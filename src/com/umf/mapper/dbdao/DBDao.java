package com.umf.mapper.dbdao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DBDao {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selAgNat <br>
	 * [描述] 是否渠道<br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午1:00:31 <br>
	 *********************************************************.<br>
	 */
	boolean selAgNat() throws Exception; 
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selSerial <br>
	 * [描述] 查询流水是否存在 <br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午2:11:23 <br>
	 *********************************************************.<br>
	 */
	boolean selSerial(String isChannel) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selAuditdata <br>
	 * [描述] 分润数据 <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午3:38:14 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> selAuditdata() throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selAgdata <br>
	 * [描述] 代理数据 <br>
	 * [返回] List<Map<String,String>> <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午4:23:15 <br>
	 *********************************************************.<br>
	 */
	List<Map<String, String>> selAgdata(String agent_num) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] isAct <br>
	 * [描述] 是否活动 <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午4:28:56 <br>
	 *********************************************************.<br>
	 */
	String isAct(String localdate) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selPoldata <br>
	 * [描述] 查询非渠道结算底价 <br>
	 * [参数] <br>
	 * [返回] Map<String,Map<String,String>> <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午4:40:51 <br>
	 *********************************************************.<br>
	 */
	Map<String, Map<String, String>> selPoldata(Map<String, String> audMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] insProData <br>
	 * [描述] 添加分润 <br>
	 * [返回] int <br>
	 * [作者] UMF
	 * [时间] 2019年11月1日 下午4:46:16 <br>
	 *********************************************************.<br>
	 */
	int insProData(Map<String,String> auMap,Map<String,String> agMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] upProData <br>
	 * [描述] 修改清算表分润标识 <br>
	 * [返回] int <br>
	 * [作者] UMF
	 * [时间] 2019年11月6日 下午9:50:43 <br>
	 *********************************************************.<br>
	 */
	int upProData() throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] selchanPoldata <br>
	 * [描述] 渠道结算底价 <br>
	 * [参数] <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年9月2日 下午1:27:22 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> selchanPoldata() throws Exception;



	/**
	 * @Author:LiuYuKun
	 * @Date:2019/11/15 15:03
	 * @Description:查询机构费率
	 * @param:
	 */
	BigDecimal selChaRate(Map<String,String> audMap) throws Exception;


	/**
	 * @Author:LiuYuKun
	 * @Date:2019/11/15 16:36
	 * @Description:添加信息到机构分润表
	 * @param:
	 */
	Integer insChaData(Map<String,String> audMap) throws Exception;

	/**
	 * @Author:LiuYuKun
	 * @Date:2019/12/6 16:14
	 * @Description:计算三个月内的交易金额
	 * @param:
	 */
	BigDecimal sumAmount() throws Exception;
}
