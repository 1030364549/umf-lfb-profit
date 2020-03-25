package com.umf.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.umf.config.ParamsConfig;
import com.umf.service.mianservice.TradingService;
import com.umf.socket.base.BaseIO;
import com.umf.utils.LoFunction;
import com.umf.utils.LogUtil;

public class SocketServer extends BaseIO {
	
	private ServerSocket serverSocket;
	private LoFunction loFunction = new LoFunction();
	private LogUtil log = new LogUtil();
	
	public SocketServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	public void service() throws Exception {
		/*通过读取配置文件的线程数量，开启固定线程（100）的线程池*/
		ExecutorService executorService = Executors.newFixedThreadPool(ParamsConfig.getThreadPool()); // 创建工作线程的线程池
		while (true) {
			Socket socket;
			try {
				/*开启监听*/
				socket = serverSocket.accept();
				executorService.execute(createTask(socket)); // 分配一个线程
			} catch (Exception e) {
				log.errinfoS(e);
			}
		}
	}
	
	private Runnable createTask(final Socket socket) {
		return new Runnable() {
			InputStream input = null;
			OutputStream output = null;
			public void run() {
				try {
					input = socket.getInputStream();
					output = socket.getOutputStream();
					/*获取当前socket的ip地址*/
					String ip = socket.getInetAddress().toString();
					System.out.println("ip:"+ip);
					/*读取配置文件信息设置超时时间*/
					socket.setSoTimeout(ParamsConfig.getSocketSoTimeOut());
					byte[] lenByte = new byte[2];
					input.read(lenByte, 0, 2);// 读取长度
					int len = (int) loFunction.byteArrayToShort(lenByte);
					System.out.println("len:"+len);
					/*读取前两位，获得数据的长度，转换成int类型，当长度小于等于0的时候直接退出*/
					if(len <= 0){
						return ;
					}
					/*若没有跳出代表存在数据，创建数据长度的byte数组*/
					byte[] bb = new byte[len];
					/*读取相当于数据长度的子节，返回实际读取的字节数*/
					int rdLen = input.read(bb, 0, len);
					/*因为可能会有网络波动原因，在读取的时候可能并没有读完数据，
					  所以判断实际读取的长度是否小于实际数据长度*/
					while(rdLen < len) {
						System.out.println("rdLen<len,while!");
						/*若小于实际数据长度，代表读取数据的时候数据没有读取完，input.available()判断当前流中
						 *有多少字节可以读取，从数组实际读取的长度继续填充，直至实际读取的长度==数据总长度时*/
						rdLen += input.read(bb, rdLen, input.available());
					}
					/*将取出的byte数组转换成16进制字符串，在转换成字符串*/
					String reqpack = loFunction.hexStr2Str(loFunction.byte2HexStr(bb));
					System.out.println("reqpack:"+reqpack);
					/** 应答 */
					/*将接收的数据赋值给共享变量，和取出所传输的数据*/
					TradingService ts = new TradingService(reqpack , ip);
					
					/** 应答 */
					/*发送200成功码的长度、成功码的byte数组*/
					send_message();
					
					ts.mainService();
				} catch (Exception e) {
					log.errinfoS(e);
				}finally {
					closeIO(socket, input, output);
				}
			}
			
			/**
			 * 
			 *********************************************************.<br>
			 * [方法] send_message <br>
			 * [描述] TODO(这里用一句话描述这个方法的作用) <br>
			 * [作者] UMF
			 * [时间] 2019年8月9日 下午3:48:12 <br>
			 *********************************************************.<br>
			 */
			private void send_message() throws Exception {
				String respStr = "200";
				byte[] resByte = respStr.getBytes();
				byte[] reqPack = new byte[2 + resByte.length];
				int bodyLen = reqPack.length - 2; // 获得报文的长度(不包括2个byte的长度)
				String resp = loFunction.initSize(Integer.toString(bodyLen, 16));
				byte[] lenByteHex = loFunction.hexStr2Bytes(resp);
				byte[] endRsB = loFunction.assemble(lenByteHex, resByte);
				output.write(endRsB);
				output.flush();
			}
		};
	}
}
