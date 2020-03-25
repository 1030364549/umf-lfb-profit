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
	private String newLine = "\r\n";     //  ���з�

	/**
	 ********************************************************* .<br>
	 * [����] errorInfo <br>
	 * [����] ��¼������־ <br>
	 * [����] �쳣��Ϣ��sql��� <br>
	 * [����] void <br>
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
	 * [����] getValue <br>
	 * [����] �ж�map��key�Ƿ���ڣ�����ȡֵ����Ϊ�� <br>
	 * [����] map���ϡ�key <br>
	 * [����] String <br>
	 *********************************************************.<br>
	 */
	public String getValue(Map<String, String> map, String key) throws Exception{
		return map.containsKey(key) ? map.get(key) : "";
	}

	/**
	 ********************************************************* .<br>
	 * [����] changeExtInfo <br>
	 * [����] ��ȡ�쳣��ӡ��Ϣ <br>
	 * [����] �쳣���� <br>
	 * [����] String <br>
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
	 * [����] getSelSerialSql <br>
	 * [����] ���������滻sql <br>
	 * [����] ������ʶ <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����2:28:05 <br>
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
	 * [����] getSelSerialSql <br>
	 * [����] ����������ʶ�滻sql <br>
	 * [����] ������ʶ <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����2:28:05 <br>
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
