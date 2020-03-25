package com.umf.config;

public class InitConfig {
	
	public final static String newLine = "\r\n";
	public final static String paramsXml = "config/paramConfig.xml";   //  系统参数配置文件路径
	public final static String dbXml = "config/c3p0-config.xml";    //  数据库配置文件路径
	public final static String logXml = "config/log4j2.xml";    // log4j日志配置文件路径
	public final static String logNorURL = "log/normal";    // 日常日志记录
	public final static String logExpURL = "log/error/exp";   // 异常日志路径
	public final static String logInitURL = "log/error/init";    // 日常信息日志路径
	public final static String logSocketURL = "log/error/socket";   // 通讯日志路径
	public final static String[] ip = {"127.0.0.1"};   // ip地址
	public final static String tax = "0.08";   // 税点封顶值
	//生产key,android,iOS 合一 
	public final static String app_key = "1586132504ee0e4110b974f4";
	public final static String master_secret = "bef803d3b9d7ef657435d646";
	 //ios蒲公英key
	public final static String app_keyios = "ea43a2f3577d83f6118f0efa";
	public final static String master_secretios = "65d65440ada677da35851a73";
	
	
	
}
