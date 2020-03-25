package com.umf.utils;

import io.netty.util.concurrent.FastThreadLocal;

public class RunningData {
	
	private static FastThreadLocal<String> ip = new FastThreadLocal<String>();     // 上送者IP
	private static FastThreadLocal<String> serial = new FastThreadLocal<String>();  // 交易流水
	private static FastThreadLocal<String> merno = new FastThreadLocal<String>();   // 商户编号 
	private static FastThreadLocal<String> posno = new FastThreadLocal<String>();   // 终端号
	private static FastThreadLocal<String> msgtype = new FastThreadLocal<String>();   // 交易类型 
	private static FastThreadLocal<String> signature = new FastThreadLocal<String>();   // 签名
	
	public static String getIp() {
		return ip.get();
	}
	public static void setIp(String ip) {
		RunningData.ip.set(ip);
	}
	
	public static String getSerial() {
		return serial.get();
	}
	public static void setSerial(String serial) {
		RunningData.serial.set(serial);
	}
	
	public static String getMerno() {
		return merno.get();
	}
	public static void setMerno(String merno) {
		RunningData.merno.set(merno);
	}
	
	public static String getPosno() {
		return posno.get();
	}
	public static void setPosno(String posno) {
		RunningData.posno.set(posno);
	}
	
	public static String getMsgtype() {
		return msgtype.get();
	}
	public static void setMsgtype(String msgtype) {
		RunningData.msgtype.set(msgtype);
	}
	
	public static String getSignature() {
		return signature.get();
	}
	public static void setSignature(String signature) {
		RunningData.signature.set(signature);
	}
	
	public static void removeAll(){
		FastThreadLocal.removeAll();
	}
}
