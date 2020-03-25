package com.umf.service.initservice;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.umf.config.InitConfig;
import com.umf.mapper.dbbase.ConnectionManager;
import com.umf.utils.LogUtil;
import com.umf.utils.XMLHandler;

@SuppressWarnings("all")
public class InitService {
	
	private static XMLHandler xmlHan = new XMLHandler();
	private static LogUtil log = new LogUtil();

	/**
	 * 
	 *********************************************************.<br>
	 * [����] loadSysParam <br>
	 * [����] ���������ļ� <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:19:27 <br>
	 *********************************************************.<br>
	 */
	public static boolean loadSysParam() throws Exception {
		try {
			/*������־�ļ�*/
			boolean logPro = initLogProperty();
			/*����˿ڲ��������ļ���ַ����xml�ļ���Ϣ������ͨ�����丳ֵ��ParamsConfig���б���*/
			boolean params = xmlHan.analyticalXML(InitConfig.paramsXml);
			/*����c3p0�ļ����������ݿ���Ϣ*/
			boolean initdb = initDataBaseConfig();
			/*����log4j�ļ���������־��Ϣ*/
			boolean initLog = initLogConfig();
			/*�ж����������ļ��Ƿ���سɹ�*/
			return checkResult(logPro, params,initdb,initLog);
		} catch (Exception e) {
			log.errinfoI(e,"overload system parameters failure...");
			return false;
		}
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] initLogProperty <br>
	 * [����] TODO(��־�ļ�����) <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:19:55 <br>
	 *********************************************************.<br>
	 * @throws Exception 
	 */
	private static boolean initLogProperty() throws Exception {
		try {
			/*�����ճ���ˮ��־���쳣����־�ļ�*/
			new File(InitConfig.logExpURL).mkdirs();
			new File(InitConfig.logInitURL).mkdirs();
			new File(InitConfig.logSocketURL).mkdirs();
			new File(InitConfig.logNorURL).mkdirs();
			/*ƴ����־����ʼ����־���Գɹ�*/
			log.saveInitlog("init log property success...");
			return true;
		} catch (Exception e) {
			/*ƴ���쳣��־*/
			log.errinfoE(e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			log.errinfoE(e);
		}
		System.exit(0);
		return false;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] initDataBaseConfig <br>
	 * [����] TODO(c3p0����) <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:20:27 <br>
	 *********************************************************.<br>
	 * @throws Exception 
	 */
	private static boolean initDataBaseConfig() throws Exception {
		try {
			System.setProperty("com.mchange.v2.c3p0.cfg.xml", InitConfig.dbXml);
			Connection conn = new ConnectionManager().getConnection();
			log.saveInitlog("Database Connection----"+conn);
			if(conn!=null){
				conn.close();
			}
			log.saveInitlog("init dataBase connection config success...");
			return true;
		} catch (Exception e) {
			log.errinfoI(e,"init dataBase connection config failure...");
		}
		return false;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] initLogConfig <br>
	 * [����] TODO(log4j2����) <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��9�� ����3:20:51 <br>
	 *********************************************************.<br>
	 * @throws Exception 
	 */
	private static boolean initLogConfig() throws Exception {
		try {
			System.setProperty("log4j.configurationFile", InitConfig.logXml);
			LogManager.getLogger();
			ConfigurationSource source=new ConfigurationSource(new FileInputStream(InitConfig.logXml),new File(InitConfig.logXml).toURL());
			Configurator.initialize(null,source);
			log.saveInitlog("init log4j2 config success...");
			return true;
		} catch (Exception e) {
			log.errinfoI(e,"init log4j2 config failure...");
		}
		return false;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] checkResult <br>
	 * [����] TODO(����ֵУ��) <br>
	 * [����] UMF	
	 * [ʱ��] 2019��8��9�� ����3:21:09 <br>
	 *********************************************************.<br>
	 */
	private static boolean checkResult(boolean... results) {
		for (boolean rst : results) {
			if (!rst) {
				return false;
			}
		}
		return true;
	}
}
