package com.umf.pack;

import com.umf.config.ParamsConfig;
import com.umf.service.initservice.InitService;
import com.umf.socket.server.SocketServer;
import com.umf.utils.LogUtil;

public class UMF_proRun {
	
	private static LogUtil log = new LogUtil();
	
	public static void main(String[] args) throws Exception {
		try {
			/*加载日志文件信息、端口号线程数参数、c3p0数据库信息，全部加载成功获取配置的端口号开始监听*/
			if (InitService.loadSysParam()) {
				new SocketServer(ParamsConfig.getListenPort()).service();
			}
		} catch (Exception e) {
			log.errinfoS(e);
		}
	}
}
