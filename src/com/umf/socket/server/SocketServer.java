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
		/*ͨ����ȡ�����ļ����߳������������̶��̣߳�100�����̳߳�*/
		ExecutorService executorService = Executors.newFixedThreadPool(ParamsConfig.getThreadPool()); // ���������̵߳��̳߳�
		while (true) {
			Socket socket;
			try {
				/*��������*/
				socket = serverSocket.accept();
				executorService.execute(createTask(socket)); // ����һ���߳�
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
					/*��ȡ��ǰsocket��ip��ַ*/
					String ip = socket.getInetAddress().toString();
					System.out.println("ip:"+ip);
					/*��ȡ�����ļ���Ϣ���ó�ʱʱ��*/
					socket.setSoTimeout(ParamsConfig.getSocketSoTimeOut());
					byte[] lenByte = new byte[2];
					input.read(lenByte, 0, 2);// ��ȡ����
					int len = (int) loFunction.byteArrayToShort(lenByte);
					System.out.println("len:"+len);
					/*��ȡǰ��λ��������ݵĳ��ȣ�ת����int���ͣ�������С�ڵ���0��ʱ��ֱ���˳�*/
					if(len <= 0){
						return ;
					}
					/*��û����������������ݣ��������ݳ��ȵ�byte����*/
					byte[] bb = new byte[len];
					/*��ȡ�൱�����ݳ��ȵ��ӽڣ�����ʵ�ʶ�ȡ���ֽ���*/
					int rdLen = input.read(bb, 0, len);
					/*��Ϊ���ܻ������粨��ԭ���ڶ�ȡ��ʱ����ܲ�û�ж������ݣ�
					  �����ж�ʵ�ʶ�ȡ�ĳ����Ƿ�С��ʵ�����ݳ���*/
					while(rdLen < len) {
						System.out.println("rdLen<len,while!");
						/*��С��ʵ�����ݳ��ȣ������ȡ���ݵ�ʱ������û�ж�ȡ�꣬input.available()�жϵ�ǰ����
						 *�ж����ֽڿ��Զ�ȡ��������ʵ�ʶ�ȡ�ĳ��ȼ�����䣬ֱ��ʵ�ʶ�ȡ�ĳ���==�����ܳ���ʱ*/
						rdLen += input.read(bb, rdLen, input.available());
					}
					/*��ȡ����byte����ת����16�����ַ�������ת�����ַ���*/
					String reqpack = loFunction.hexStr2Str(loFunction.byte2HexStr(bb));
					System.out.println("reqpack:"+reqpack);
					/** Ӧ�� */
					/*�����յ����ݸ�ֵ�������������ȡ�������������*/
					TradingService ts = new TradingService(reqpack , ip);
					
					/** Ӧ�� */
					/*����200�ɹ���ĳ��ȡ��ɹ����byte����*/
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
			 * [����] send_message <br>
			 * [����] TODO(������һ�仰�����������������) <br>
			 * [����] UMF
			 * [ʱ��] 2019��8��9�� ����3:48:12 <br>
			 *********************************************************.<br>
			 */
			private void send_message() throws Exception {
				String respStr = "200";
				byte[] resByte = respStr.getBytes();
				byte[] reqPack = new byte[2 + resByte.length];
				int bodyLen = reqPack.length - 2; // ��ñ��ĵĳ���(������2��byte�ĳ���)
				String resp = loFunction.initSize(Integer.toString(bodyLen, 16));
				byte[] lenByteHex = loFunction.hexStr2Bytes(resp);
				byte[] endRsB = loFunction.assemble(lenByteHex, resByte);
				output.write(endRsB);
				output.flush();
			}
		};
	}
}
