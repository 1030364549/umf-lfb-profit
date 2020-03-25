package com.umf.mapper.dbbase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.umf.config.InitConfig;
import com.umf.utils.LogUtil;
import com.umf.utils.RunningData;

@SuppressWarnings("all")
public class BaseDao {
	
	private LogUtil log = new LogUtil();
	protected ConnectionManager conMan = ConnectionManager.getInstance();
	private String newLine = "\r\n";     //  换行符

	/**
	 ********************************************************* .<br>
	 * [方法] errorInfo <br>
	 * [描述] 记录错误日志 <br>
	 * [参数] 异常信息，sql语句 <br>
	 * [返回] void <br>
	 ********************************************************* .<br>
	 */
	public void errorInfo(Exception e, String sql) throws Exception {
		try {
			StringBuffer errormsg = new StringBuffer();
			errormsg.append("[SQL]: [").append(sql).append("]");
			errormsg.append(newLine);
			errormsg.append("[Exp]: ").append(changeExtInfo(e)).append("]");
			errormsg.append(newLine);
			log.saveLog(errormsg.toString(), 0);
		} catch (Exception e1) {
			log.errinfoE(e1);
		}
	}
	
	/**
	 *********************************************************.<br>
	 * [方法] getValue <br>
	 * [描述] 判断map中key是否存在，存在取值否则为空 <br>
	 * [参数] map集合、key <br>
	 * [返回] String <br>
	 *********************************************************.<br>
	 */
	public String getValue(Map<String, String> map, String key) throws Exception{
		return map.containsKey(key) ? map.get(key) : "";
	}

	/**
	 ********************************************************* .<br>
	 * [方法] changeExtInfo <br>
	 * [描述] 获取异常打印信息 <br>
	 * [参数] 异常对象 <br>
	 * [返回] String <br>
	 ********************************************************* .<br>
	 */
	private String changeExtInfo(Exception e) throws Exception {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e1) {
			throw e1;
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getSelSerialSql <br>
	 * [描述] 根据类型替换sql <br>
	 * [参数] 渠道标识 <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午2:28:05 <br>
	 *********************************************************.<br>
	 */
	public String getIsChannelSql() {
		String sql = "";
		if ("H007".equals(RunningData.getMsgtype())) {
			sql = "select agent_nature from agent_info where agent_num in (select agent_num from formal_pos where posno = ?)";
		} else {
			sql = "select agent_nature from agent_info where agent_num in (select agent_no from merchant_info where merno = ?)";
		}
		return sql;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getSelSerialSql <br>
	 * [描述] 根据渠道标识替换sql <br>
	 * [参数] 渠道标识 <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午2:28:05 <br>
	 *********************************************************.<br>
	 */
	public String getSelSerialSql(String isChannel) {
		String sql = "";
		switch (isChannel) {
		case "0":
			sql = "select count(*) as serial from profit_detailed where serial = ?";
			break;
		case "1":
			sql = "select count(*) as serial from channel_profit_detailed where serial = ?";
			break;
		}
		return sql;
	}
}
