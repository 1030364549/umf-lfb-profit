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

	/*��Ҫ��ӡ����Ϣ*/
	private StringBuffer msg;
	private DateUtil du = new DateUtil();
	private LoFunction lo = new LoFunction();
	
	public void saveInitlog(String reqpack) throws Exception {
		/*ƴ����Ϣ������*/
		msg=new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("��ϵͳʱ�䡿:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("��������⡿:").append(reqpack).append(InitConfig.newLine).append(InitConfig.newLine);
		/*������־����ѡ���ŵ�ַ��ͨ��io��д����Ϣ*/
		saveLog(msg.toString(), 3);
	}
	
	public void saveMaplog(String reqpack) throws Exception {
		msg=new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("��ϵͳʱ�䡿:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("�������ַ��:").append(RunningData.getIp()).append(InitConfig.newLine);
		msg.append("��������ˮ��:").append(RunningData.getSerial()).append(InitConfig.newLine);
		msg.append("�������ġ�:").append(reqpack).append(InitConfig.newLine);
		saveLog(msg.toString(), 3);
	}
	
	public void errinfoE(Exception e) {
		try {
			msg = new StringBuffer();
			msg.append("************************************************************").append(InitConfig.newLine);
			msg.append("��ϵͳʱ�䡿:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
			msg.append("��������ˮ��:").append(RunningData.getSerial()).append(InitConfig.newLine);
			msg.append("��������Ϣ��:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
			saveLog(msg.toString(), 0);
		} catch (Exception e2) {
			errinfoE(e2);
		}
	}

	public void errinfoI(Exception e,String str) throws Exception {
		msg = new StringBuffer();
		msg.append("************************************************************").append(InitConfig.newLine);
		msg.append("��ϵͳʱ�䡿:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
		msg.append("������ϵͳ������:").append(InitConfig.newLine);
		msg.append("��������⡿:").append(str).append(InitConfig.newLine).append(InitConfig.newLine);
		msg.append("��������Ϣ��:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
		saveLog(msg.toString(), 1);
	}
	
	public void errinfoS(Exception e) {
		try {
			msg = new StringBuffer();
			msg.append("************************************************************").append(InitConfig.newLine);
			msg.append("��ϵͳʱ�䡿:").append(du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss")).append(InitConfig.newLine);
			msg.append("��������Ϣ��:").append(changeExtInfo(e)).append(InitConfig.newLine).append(InitConfig.newLine);
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
			/*�쳣��־��Ϣ��ַ*/
			String fileUrl = "log/error/";
			/*��ȡ��ǰʱ�������ո�ʽ������*/
			String datetime = du.getCurrentDateTime("yyyyMMdd");
			/*�������ʹ����־*/
			switch (tt) {
			case 0:/*�����г����쳣*/
				/*������������ȫ��ƴ�Ӳ�����*/
				fileUrl = lo.appendField(fileUrl, "Exp/E", datetime,".txt");
				break;
			case 1:/*��ʼ����Ϣ����*/
				fileUrl = lo.appendField(fileUrl, "Init/T", datetime,".txt");
				break;
			case 2:/*�ر�io��������δֹͣ��socket�쳣*/
				fileUrl = lo.appendField(fileUrl, "Socket/S", datetime,".txt");
				break;
			case 3:/*���سɹ���Ϣ��������ˮ��Ϣ*/
				fileUrl = lo.appendField("log/normal/", "N", datetime,".txt");
				break;
			}
			/*����ƴ����ɵ��ļ���ַ*/
			File file = new File(fileUrl);
			/*�����ļ���append[׷��]����Ϊtrue�������ڲ��������ļ�����׷���ı��������ھʹ�����Ĭ��Ϊfalse���ͻḲ��֮ǰ���ı���*/
			fs = new FileOutputStream(file, true);
			/*�����ַ���ΪUTF-8*/
			ow = new OutputStreamWriter(fs, "UTF-8");
			bw = new BufferedWriter(ow);
			/*д����Ϣ*/
			bw.write(msg);
			/*ˢ���ļ�*/
			bw.flush();
			/*����*/
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
