package com.umf.service.profitservice;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface Profit {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] posReqMap <br>
	 * [����] ���� <br>
	 * [����] Map<String,String> <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����1:04:33 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> posReqMap(String reqpack) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] holdSignature <br>
	 * [����] mac <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����1:04:38 <br>
	 *********************************************************.<br>
	 */
	String holdSignature(Map<String, String> posReqMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] chargeProfit <br>
	 * [����] �Ǹ��� <br>
	 * [����] Map<String,Object> <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����1:04:42 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> chargeProfit(Map<String, String> auMap , int i) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] additProfit <br>
	 * [����] ���� <br>
	 * [����] Map<String,Object> <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����1:04:47 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> additProfit(Map<String, String> auMap , int i) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] taxpoint <br>
	 * [����] ˰�� <br>
	 * [����] Map<String,Object> <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����1:04:52 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> taxpoint(Map<String, String> auMap, String taxproSumMoney) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [����] taxpointCharge <br>
	 * [����] ���ӷ�˰�� <br>
	 * [����] Map<String,Object> <br>
	 * [����] UMF
	 * [ʱ��] 2019��12��3�� ����1:05:33 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> taxpointCha(Map<String, String> auMap) throws Exception;


}
