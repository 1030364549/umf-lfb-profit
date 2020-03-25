 package com.umf.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import com.umf.socket.base.BaseIO;
import com.umf.utils.EncryUtil;
import com.umf.utils.LoFunction;

import net.sf.json.JSONObject;

public class  TestClient extends BaseIO{
	private static LoFunction lo = new LoFunction();
	private static EncryUtil eu = new EncryUtil();
	
	public byte[] connServer(byte[] reqPack) throws Exception {
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			String ipAddress ="127.0.0.1";
			int port = 17102;
			socket = new Socket(); // 建立与服务器端的链接
			socket.connect(new InetSocketAddress(ipAddress, port), 60000); // 连接超时设置
			socket.setSoTimeout(60000); // 读写超时设置
			os = socket.getOutputStream();
			is = socket.getInputStream();
			os.write(reqPack);
			System.out.println("请求包：" + lo.byte2HexStr(reqPack));
			os.flush();
			// 接收服务器的响应
			byte[] lenByte = new byte[2];
			is.read(lenByte, 0, 2);
			int len = (int) lo.byteArrayToShort(lenByte);
			byte[] bb = new byte[len];
			is.read(bb, 0, len);
			byte[] respPack = new byte[lenByte.length + bb.length];
			System.arraycopy(lenByte, 0, respPack, 0, 2);
			System.arraycopy(bb, 0, respPack, lenByte.length, bb.length);
			return lo.checkByte(bb) ? null : bb;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeIO(socket, is, os);
		}
	}
	
	public static void main(String[] args) throws Exception {
			TestClient test = new TestClient();
			Map<String, String> data = new TreeMap<String, String>();
			data.put("serial", "100000380430");     // 交易流水号
			data.put("merno", "100000000004216");  //  商户编号
			data.put("posno", "100000565469");     // 终端号
			data.put("msgtype", "H007");     // 交易类型 0 线下 1 线上
			/*添加签名，将所有数据拼接MD5加密，在存入map集合*/
			data.put("signature", test.getSign(data));
			String reqdata=JSONObject.fromObject(data).toString();
			byte[] resByte = reqdata.getBytes();
			byte[] reqPack = new byte[2 + resByte.length];
			int lenByte = reqPack.length - 2; // 获得报文的长度(不包括2个byte的长度)
			/*将报文长度转换成16进制，调用方法，位数不够前补0*/
			String str = lo.initSize(Integer.toString(lenByte, 16));
			/*将16进制字符串转换为byte数组，前两位合并，两位的byte数组*/
			byte[] lenByteHex = lo.hexStr2Bytes(str);
			/*将报文长度转换成的16进制字符串转换成的byte数组、参数转换json字符串转换成的byte数组，
			  合成为一个byte数组，数据的长度在前，数据在后*/
			byte[] reqBody = lo.assemble(lenByteHex, resByte);
			/*传输信息*/
			byte[] resp=test.connServer(reqBody);
			/*将接受的byte数组转换成16进制的字符串*/
			String pack = lo.byte2HexStr(resp);
			/*将16进制的字符串转换成字符串*/
			String respStr = lo.hexStr2Str(pack);
			System.out.println(respStr);
 	}
	
	public String getSign(Map<String, String> map) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("serial=").append(map.get("serial"));
		sb.append("&");
		sb.append("merno=").append(map.get("merno"));
		sb.append("&");
		sb.append("posno=").append(map.get("posno"));
		sb.append("&");
		sb.append("msgtype=").append(map.get("msgtype"));
		System.out.println(sb.toString());
		return eu.encryptMD5(sb.toString());
	}
}

	