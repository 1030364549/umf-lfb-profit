package com.umf.mapper.dbdao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DBDao {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selAgNat <br>
	 * [����] �Ƿ�����<br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����1:00:31 <br>
	 *********************************************************.<br>
	 */
	boolean selAgNat() throws Exception; 
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selSerial <br>
	 * [����] ��ѯ��ˮ�Ƿ���� <br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����2:11:23 <br>
	 *********************************************************.<br>
	 */
	boolean selSerial(String isChannel) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selAuditdata <br>
	 * [����] �������� <br>
	 * [����] Map<String,String> <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����3:38:14 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> selAuditdata() throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selAgdata <br>
	 * [����] �������� <br>
	 * [����] List<Map<String,String>> <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����4:23:15 <br>
	 *********************************************************.<br>
	 */
	List<Map<String, String>> selAgdata(String agent_num) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] isAct <br>
	 * [����] �Ƿ� <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����4:28:56 <br>
	 *********************************************************.<br>
	 */
	String isAct(String localdate) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selPoldata <br>
	 * [����] ��ѯ����������׼� <br>
	 * [����] <br>
	 * [����] Map<String,Map<String,String>> <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����4:40:51 <br>
	 *********************************************************.<br>
	 */
	Map<String, Map<String, String>> selPoldata(Map<String, String> audMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] insProData <br>
	 * [����] ��ӷ��� <br>
	 * [����] int <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��1�� ����4:46:16 <br>
	 *********************************************************.<br>
	 */
	int insProData(Map<String,String> auMap,Map<String,String> agMap) throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] upProData <br>
	 * [����] �޸����������ʶ <br>
	 * [����] int <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��6�� ����9:50:43 <br>
	 *********************************************************.<br>
	 */
	int upProData() throws Exception;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] selchanPoldata <br>
	 * [����] ��������׼� <br>
	 * [����] <br>
	 * [����] Map<String,String> <br>
	 * [����] UMF
	 * [ʱ��] 2019��9��2�� ����1:27:22 <br>
	 *********************************************************.<br>
	 */
	Map<String, String> selchanPoldata() throws Exception;



	/**
	 * @Author:LiuYuKun
	 * @Date:2019/11/15 15:03
	 * @Description:��ѯ��������
	 * @param:
	 */
	BigDecimal selChaRate(Map<String,String> audMap) throws Exception;


	/**
	 * @Author:LiuYuKun
	 * @Date:2019/11/15 16:36
	 * @Description:�����Ϣ�����������
	 * @param:
	 */
	Integer insChaData(Map<String,String> audMap) throws Exception;

	/**
	 * @Author:LiuYuKun
	 * @Date:2019/12/6 16:14
	 * @Description:�����������ڵĽ��׽��
	 * @param:
	 */
	BigDecimal sumAmount() throws Exception;
}
