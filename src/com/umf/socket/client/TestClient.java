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
			socket = new Socket(); // ������������˵�����
			socket.connect(new InetSocketAddress(ipAddress, port), 60000); // ���ӳ�ʱ����
			socket.setSoTimeout(60000); // ��д��ʱ����
			os = socket.getOutputStream();
			is = socket.getInputStream();
			os.write(reqPack);
			System.out.println("�������" + lo.byte2HexStr(reqPack));
			os.flush();
			// ���շ���������Ӧ
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
			data.put("serial", "100000380430");     // ������ˮ��
			data.put("merno", "100000000004216");  //  �̻����
			data.put("posno", "100000565469");     // �ն˺�
			data.put("msgtype", "H007");     // �������� 0 ���� 1 ����
			/*���ǩ��������������ƴ��MD5���ܣ��ڴ���map����*/
			data.put("signature", test.getSign(data));
			String reqdata=JSONObject.fromObject(data).toString();
			byte[] resByte = reqdata.getBytes();
			byte[] reqPack = new byte[2 + resByte.length];
			int lenByte = reqPack.length - 2; // ��ñ��ĵĳ���(������2��byte�ĳ���)
			/*�����ĳ���ת����16���ƣ����÷�����λ������ǰ��0*/
			String str = lo.initSize(Integer.toString(lenByte, 16));
			/*��16�����ַ���ת��Ϊbyte���飬ǰ��λ�ϲ�����λ��byte����*/
			byte[] lenByteHex = lo.hexStr2Bytes(str);
			/*�����ĳ���ת���ɵ�16�����ַ���ת���ɵ�byte���顢����ת��json�ַ���ת���ɵ�byte���飬
			  �ϳ�Ϊһ��byte���飬���ݵĳ�����ǰ�������ں�*/
			byte[] reqBody = lo.assemble(lenByteHex, resByte);
			/*������Ϣ*/
			byte[] resp=test.connServer(reqBody);
			/*�����ܵ�byte����ת����16���Ƶ��ַ���*/
			String pack = lo.byte2HexStr(resp);
			/*��16���Ƶ��ַ���ת�����ַ���*/
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

	