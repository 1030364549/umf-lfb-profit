package com.umf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.umf.config.InitConfig;

public class LogUtil {

	/*需要打印的信息*/
	private StringBuffer msg;
	private DateUtil du = new DateUtil();
	private LoFunction lo = new LoFunction();
	
	public void saveInitlog(String reqpack) throws Exception {
		/*拼接信息及换行*/
		msg=new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("【报文详解】:").append(reqpack).append(InitConfig.newLine).append(InitConfig.newLine);
		/*根据日志类型选择存放地址，通过io流写入信息*/
		saveLog(msg.toString(), 3);
	}
	
	public void saveMaplog(String reqpack) throws Exception {
		msg=new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("【请求地址】:").append(RunningData.getIp()).append(InitConfig.newLine);
		msg.append("【交易流水】:").append(RunningData.getSerial()).append(InitConfig.newLine);
		msg.append("【请求报文】:").append(reqpack).append(InitConfig.newLine);
		saveLog(msg.toString(), 3);
	}
	
	public void errinfoE(Exception e) {
		try {
			msg = new StringBuffer();
			msg.append("************************************************************").append(InitConfig.newLine);
			msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
			msg.append("【交易流水】:").append(RunningData.getSerial()).append(InitConfig.newLine);
			msg.append("【错误信息】:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
			saveLog(msg.toString(), 0);
		} catch (Exception e2) {
			errinfoE(e2);
		}
	}

	public void errinfoI(Exception e,String str) throws Exception {
		msg = new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("【重载系统参数】:").append(InitConfig.newLine);
		msg.append("【报文详解】:").append(str).append(InitConfig.newLine).append(InitConfig.newLine);
		msg.append("【错误信息】:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
		saveLog(msg.toString(), 1);
	}
	
	public void errinfoS(Exception e) {
		try {
			msg = new StringBuffer();
			msg.append("************************************************************").append(InitConfig.newLine);
			msg.append("【系统时间】:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
			msg.append("【错误信息】:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
			saveLog(msg.toString(), 2);
		} catch (Exception e2) {
			errinfoE(e2);
		}
	}
	
	public void saveLog(String msg, int tt) throws Exception {
		FileOutputStream fs = null;
		OutputStreamWriter ow = null;
		BufferedWriter bw = null;
		try {
			/*异常日志信息地址*/
			String fileUrl = "log/error/";
			/*获取当前时间年月日格式的日期*/
			String datetime = du.getCurrentDateTime("yyyyMMdd");
			/*根据类型存放日志*/
			switch (tt) {
			case 0:/*流程中出现异常*/
				/*将不定长参数全部拼接并返回*/
				fileUrl = lo.appendField(fileUrl, "Exp/E", datetime,".txt");
				break;
			case 1:/*初始化信息错误*/
				fileUrl = lo.appendField(fileUrl, "Init/T", datetime,".txt");
				break;
			case 2:/*关闭io、服务器未停止、socket异常*/
				fileUrl = lo.appendField(fileUrl, "Socket/S", datetime,".txt");
				break;
			case 3:/*加载成功信息，交易流水信息*/
				fileUrl = lo.appendField("log/normal/", "N", datetime,".txt");
				break;
			}
			/*创建拼接完成的文件地址*/
			File file = new File(fileUrl);
			/*创建文件（append[追加]设置为true，若存在并不覆盖文件而是追加文本，不存在就创建；默认为false，就会覆盖之前的文本）*/
			fs = new FileOutputStream(file, true);
			/*设置字符集为UTF-8*/
			ow = new OutputStreamWriter(fs, "UTF-8");
			bw = new BufferedWriter(ow);
			/*写入信息*/
			bw.write(msg);
			/*刷新文件*/
			bw.flush();
			/*换行*/
			bw.newLine();
			bw.newLine();
		} catch (Exception e) {
			closeIO(fs, ow, bw);
		}
	}
	
	private String changeExtInfo(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	private void closeIO(FileOutputStream fs, OutputStreamWriter ow,BufferedWriter bw) throws Exception {
		try {
			if (bw != null) {
				bw.close();
			}
			if (ow != null) {
				ow.close();
			}
			if (fs != null) {
				fs.close();
			}
		} catch (IOException e) {
			errinfoS(e);
		}
	}
}
