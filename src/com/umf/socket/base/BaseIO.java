package com.umf.socket.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.umf.utils.LogUtil;

public class BaseIO {
	
	private LogUtil log = new LogUtil();
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] closeIO <br>
	 * [����] TODO(�ر�IO��) <br>
	 * [����] TODO(socketͨѶ���������������) <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:47:03 <br>
	 *********************************************************.<br>
	 */
	protected void closeIO(Socket socket, InputStream input, OutputStream output) {
		try {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			log.errinfoS(e);
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] closeIO <br>
	 * [����] TODO(�ر�IO��) <br>
	 * [����] TODO(socketͨѶ�������) <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:47:31 <br>
	 *********************************************************.<br>
	 */
	protected void closeIO(Socket socket, OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			log.errinfoS(e);
		}
	}

}
