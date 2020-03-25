package com.umf.service.profitservice;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface Profit {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] posReqMap <br>
	 * [描述] 请求 <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:33 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> posReqMap(String reqpack) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] holdSignature <br>
	 * [描述] mac <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:38 <br>
	 *********************************************************.<br>
	 */
	String holdSignature(Map<String, String> posReqMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] chargeProfit <br>
	 * [描述] 非附加 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:42 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> chargeProfit(Map<String, String> auMap , int i) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] additProfit <br>
	 * [描述] 附加 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:47 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> additProfit(Map<String, String> auMap , int i) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] taxpoint <br>
	 * [描述] 税差 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:52 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> taxpoint(Map<String, String> auMap, String taxproSumMoney) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] taxpointCharge <br>
	 * [描述] 附加费税差 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年12月3日 下午1:05:33 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> taxpointCha(Map<String, String> auMap) throws Exception;


}
