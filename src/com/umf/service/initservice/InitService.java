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
	 * [方法] loadSysParam <br>
	 * [描述] 加载配置文件 <br>
	 * [作者] UMF
	 * [时间] 2019年8月9日 下午3:19:27 <br>
	 *********************************************************.<br>
	 */
	public static boolean loadSysParam() throws Exception {
		try {
			/*配置日志文件*/
			boolean logPro = initLogProperty();
			/*传入端口参数配置文件地址，将xml文件信息读出并通过反射赋值给ParamsConfig类中变量*/
			boolean params = xmlHan.analyticalXML(InitConfig.paramsXml);
			/*配置c3p0文件，加载数据库信息*/
			boolean initdb = initDataBaseConfig();
			/*配置log4j文件，加载日志信息*/
			boolean initLog = initLogConfig();
			/*判断所有配置文件是否加载成功*/
			return checkResult(logPro, params,initdb,initLog);
		} catch (Exception e) {
			log.errinfoI(e,"overload system parameters failure...");
			return false;
		}
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] initLogProperty <br>
	 * [描述] TODO(日志文件创建) <br>
	 * [作者] UMF
	 * [时间] 2019年8月9日 下午3:19:55 <br>
	 *********************************************************.<br>
	 * @throws Exception 
	 */
	private static boolean initLogProperty() throws Exception {
		try {
			/*创建日常流水日志和异常的日志文件*/
			new File(InitConfig.logExpURL).mkdirs();
			new File(InitConfig.logInitURL).mkdirs();
			new File(InitConfig.logSocketURL).mkdirs();
			new File(InitConfig.logNorURL).mkdirs();
			/*拼接日志：初始化日志属性成功*/
			log.saveInitlog("init log property success...");
			return true;
		} catch (Exception e) {
			/*拼接异常日志*/
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
	 * [方法] initDataBaseConfig <br>
	 * [描述] TODO(c3p0加载) <br>
	 * [作者] UMF
	 * [时间] 2019年8月9日 下午3:20:27 <br>
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
	 * [方法] initLogConfig <br>
	 * [描述] TODO(log4j2加载) <br>
	 * [作者] UMF
	 * [时间] 2019年8月9日 下午3:20:51 <br>
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
	 * [方法] checkResult <br>
	 * [描述] TODO(返回值校验) <br>
	 * [作者] UMF	
	 * [时间] 2019年8月9日 下午3:21:09 <br>
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
