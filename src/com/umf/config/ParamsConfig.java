package com.umf.config;

public class ParamsConfig {
	
	private static int listenPort;   //  端口
	private static int threadPool;   //  线程数
	private static int inetTimeOut;   //  读写超时时间设置
	private static int socketSoTimeOut;   //  连接超时时间设置
	
	public static int getListenPort() {
		return listenPort;
	}
	public static void setListenPort(String listenPort) {
		ParamsConfig.listenPort = Integer.parseInt(listenPort);
	}
	
	public static int getThreadPool() {
		return threadPool;
	}
	public static void setThreadPool(String threadPool) {
		ParamsConfig.threadPool = Integer.parseInt(threadPool);
	}

	public static int getInetTimeOut() {
		return inetTimeOut;
	}
	public static void setInetTimeOut(String inetTimeOut) {
		ParamsConfig.inetTimeOut = Integer.parseInt(inetTimeOut);
	}
	
	public static int getSocketSoTimeOut() {
		return socketSoTimeOut;
	}
	public static void setSocketSoTimeOut(String socketSoTimeOut) {
		ParamsConfig.socketSoTimeOut = Integer.parseInt(socketSoTimeOut);
	}
}
