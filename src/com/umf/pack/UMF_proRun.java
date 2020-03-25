package com.umf.pack;

import com.umf.config.ParamsConfig;
import com.umf.service.initservice.InitService;
import com.umf.socket.server.SocketServer;
import com.umf.utils.LogUtil;

public class UMF_proRun {
	
	private static LogUtil log = new LogUtil();
	
	public static void main(String[] args) throws Exception {
		try {
			/*������־�ļ���Ϣ���˿ں��߳���������c3p0���ݿ���Ϣ��ȫ�����سɹ���ȡ���õĶ˿ںſ�ʼ����*/
			if (InitService.loadSysParam()) {
				new SocketServer(ParamsConfig.getListenPort()).service();
			}
		} catch (Exception e) {
			log.errinfoS(e);
		}
	}
}
