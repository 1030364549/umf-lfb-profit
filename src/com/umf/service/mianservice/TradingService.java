package com.umf.service.mianservice;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umf.config.InitConfig;
import com.umf.mapper.dbdao.DBDao;
import com.umf.mapper.dbdao.impl.DBDaoImpl;
import com.umf.service.profitservice.Profit;
import com.umf.service.profitservice.impl.ProfitImpl;
import com.umf.socket.client.Jpush;
import com.umf.utils.DateUtil;
import com.umf.utils.LoFunction;
import com.umf.utils.LogUtil;
import com.umf.utils.RunningData;

import javax.xml.bind.SchemaOutputResolver;

@SuppressWarnings("all")
public class TradingService {
	
	private String ip;
	private String reqpack;
	private Map<String, String> posReqMap;
	private LogUtil log = new LogUtil();
	private LoFunction lo = new LoFunction();
	private DBDao dbdao = new DBDaoImpl();
	private Profit pro = new ProfitImpl();
	private String isAct = "0"; // �Ƿ� 0 ���� 1 ��
	private String isVip = "0"; // �Ƿ�VIP 0 ���� 1 ��
	private String proMark = "0"; // 0-����׼� 1-��׼� 2-��Ա�׼�
	private String isChannel = "0"; // �Ƿ������� 0-�� 1-��
	private String actId = "";
	private Map<String, String> audMap;/*δ���ķ�������������Ϣauditdata*/
	private List<Map<String, String>> agList;/*�̻���Ϣagent_info*/
	private Map<String, Map<String, String>> selPolMap;
	private Map<String, String> channelPolMap;
	private Jpush jpush = new Jpush();
	
	public TradingService(String reqpack, String ipp) {
		this.reqpack = reqpack;
		this.ip = ipp;
		try {
			RunningData.removeAll();  // ��������̱߳���
			RunningData.setIp(ip);
			/*�������json�ַ���ת����map���ϣ�����Ϊ�����̱߳�����ֵ*/
			posReqMap = pro.posReqMap(reqpack);
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	public void mainService() throws Exception {
		try {
			
//			/** IPУ�� */
//			if(!checkIp()) {
//				System.out.println("����IP��ַ�쳣......");
//				return;
//			}
			
			/** У��mac */
			/*���������ݵ�ǩ��ɾ����Ȼ���ٴ�ƴ����������MD5�����γ�ǩ�����ж���֮ǰ�Ĺ����̱߳�����ǩ���Ƿ���ͬ*/
			if(!checkMak()) {
				System.out.println("MakУ���쳣......");
				return;
			}
			
			/** У���Ƿ�����  */
			/*���û���Ϣ���жϴ����������Ƿ�Ϊ���������ǻ�������true������������*/
			if(dbdao.selAgNat()) {
				isChannel = "1";   // �Ƿ������� 0-�� 1-��
			}
			
			/** ���䵽���� */
			/*�����Ƿ�Ϊ������ѡ�񷽷�ִ��*/
			getClass().getMethod("deal_0" + isChannel, new Class[] {}).invoke(this);
			
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] pro_f0 <br>
	 * [����] ����������<br>
	 * [����] void <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����1:13:06 <br>
	 *********************************************************.<br>
	 */
	public void deal_00() throws Exception {
		if(!deal()) {
			return;
		}
		/** ��ѯ�������׼�  */
		/*����δ���ķ����������ȡ�����ݣ�Map<id,map>���ϣ����̻����Ϊkey��������ϢΪvalue*/
		selPolMap = dbdao.selPoldata(audMap);
		String upPrice = "";   // ��ʱ�����ѽ���׼�
		String addupPrice = "";  // ��ʱ���ӽ���׼�
		String upTax = ""; // ��ʱ˰��׼�
		String taxproSumMoney = "0";  // ��ʱ���������·�����
		/*���������̻���ϢagList*/
		for (int i = 0; i < agList.size(); i++) {
			Map<String,String> auMap = new HashMap<String,String>();
			auMap.putAll(audMap);
			Map<String, String> agMap = agList.get(i);
			/*�Ե�ǰ�û��Ĵ�����Ϊkeyȡ�����������Ϣmap����*/
			Map<String, String> polMap = selPolMap.get(agMap.get("agent_num"));
			/*��������Ϣ�������㼯����*/
			if(polMap == null) {
				auMap.put("sett_price", "");	/*����׼ۣ�%��*/
				auMap.put("ceiling_cost", "");	/*�ⶥ�ɱ���Ԫ/�ʣ� */
				auMap.put("surcharge", "");		/*���������ѵ׼ۣ�%��*/
				auMap.put("surcharge_ein", "");	/*���������ѵ׼ۣ�Ԫ/�ʣ�*/
				auMap.put("tax_point", "");		/*��˰��*/
			}else {
				auMap.put("sett_price", polMap.get("sett_price") == null ? "" : polMap.get("sett_price"));
				auMap.put("ceiling_cost", polMap.get("ceiling_cost") == null ? "" : polMap.get("ceiling_cost"));
				auMap.put("surcharge", polMap.get("surcharge") == null ? "" : polMap.get("surcharge"));
				auMap.put("surcharge_ein", polMap.get("surcharge_ein") == null ? "" : polMap.get("surcharge_ein"));
				auMap.put("tax_point", polMap.get("tax_point") == null ? "" : polMap.get("tax_point"));
			}
			auMap.put("upPrice", upPrice);
			auMap.put("addupPrice", addupPrice);
			auMap.put("upTax", upTax);
			/*ȡ���û���Ϣ�Ĵ����𲢴��뵽auMap��*/
			auMap.put("agent_level", agMap.get("agent_level"));
			/** ���������ѷ���  */
			Map<String, Object> charMap = pro.chargeProfit(auMap , i);
			upPrice = charMap.get("upPrice").toString();
			auMap.put("proMoney", charMap.get("proMoney").toString());
			auMap.put("proSumMoney", charMap.get("proSumMoney").toString());
			
			/** ���㸽�������ѷ���  */
			/*sett_type  �������� 0��D0 1��T1*/
			if("0".equals(auMap.get("sett_type"))) {
				Map<String, Object> addMap = pro.additProfit(auMap,i);
				addupPrice = addMap.get("addupPrice").toString();
				auMap.put("addproMoney", addMap.get("addproMoney").toString());
				auMap.put("addproSumMoney", addMap.get("addproSumMoney").toString());
			}
			/*�жϴ����̵ȼ�*/
			if("1".equals(auMap.get("agent_level"))) {
				taxproSumMoney = charMap.get("proSumMoney").toString();
			}
			/** ����˰��  */
			Map<String, Object> taxMap = pro.taxpoint(auMap,taxproSumMoney);
			auMap.put("level_tax", taxMap.get("level_tax").toString());  // ������˰
			auMap.put("below_tax", taxMap.get("below_tax").toString());  // ���������¿�˰
			auMap.put("level_tax_profit", taxMap.get("level_tax_profit").toString());  // ������˰�������
			auMap.put("below_tax_profit", taxMap.get("below_tax_profit").toString());  // ���������¿�˰�������
			auMap.put("profit_tax_diff", taxMap.get("profit_tax_diff").toString());  // ����˰��
			upTax = taxMap.get("upTax").toString();
			System.out.println("˰���ã�"+audMap.get("charge_buckle"));
			System.out.println("��˰��"+audMap.get("charge_after"));
//			dbdao.insProData(auMap, agMap);
//			jpush.pAll(agMap.get("agent_num"),auMap.get("level_tax_profit"));
		}
//		dbdao.upProData();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] pro_f1 <br>
	 * [����] ��������<br>
	 * [����] void <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����1:12:53 <br>
	 *********************************************************.<br>
	 */
	public void deal_01() throws Exception {
		if(!deal()) {
			return;
		}
		/*��ѯ��������*/
		BigDecimal rate = dbdao.selChaRate(audMap);
		/*�����ɱ����������*���׽��*/
		BigDecimal cost = rate.multiply(new BigDecimal(audMap.get("amount")));
		/*��������������-�����ɱ�*/
		BigDecimal profit = new BigDecimal(audMap.get("charge")).subtract(cost);
        /*��λ��Ļ����ɱ�*/
		String repairCost = "";
		/*�������ɱ�Ϊ0ʱ����λΪ11λ0*/
		if(cost.compareTo(BigDecimal.ZERO)==0){
			repairCost = "00000000000";
		}else{
            /*��Ϊ0ʱ������10000ȥ��С���㣬ǰ��0��11λ*/
			repairCost = cost.multiply(new BigDecimal("10000")).setScale(0,BigDecimal.ROUND_DOWN)
					.toString();
			Integer costLength = repairCost.length();
            /*ǰ�߲���0*/
			StringBuffer stringBuffer = new StringBuffer("");
			for(int i=0;i<11-costLength;i++){
				stringBuffer.append("0");
			}
			repairCost = stringBuffer.append(repairCost).toString();
		}
		/*�����ɱ�����������*/
		audMap.put("channel_cost",repairCost);
		audMap.put("channel_profit",profit.toString());
		/*������������Ϣ��ӵ�����������Ϣ����*/
		Integer i = dbdao.insChaData(audMap);
		/*�޸������״̬��Ϊ1���ѷ�*/
		dbdao.upProData();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] deal <br>
	 * [����] <br>
	 * [����] <br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��9��2�� ����1:31:45 <br>
	 *********************************************************.<br>
	 */
	public boolean deal() throws Exception {
		
		/** ������ˮ�Ų�ѯ������Ƿ���� */
		/*�����Ƿ�Ϊ����ѡ��sql����������������ݽ�����ˮserial��ѯ������Ϣprofit_detailed��������
		* ������������ݽ�����ˮserial��ѯ����������Ϣchannel_profit_detailed������
		* ��������Ϊ0ʱ����*/
		if(!dbdao.selSerial(isChannel)) {
			return false;
		}

		/** ��ѯҪ����Ľ������� */
		/*�����̻���š��������͡�������ˮ�ź� δ������״̬is_profit��0��δ����1���ѷ�����ѯҪ���������*/
		audMap = dbdao.selAuditdata();
		if(audMap == null) {
			return false;
		}
		
		/** ��ѯ��������Ϣ */
		/*�����Ϸ���ѯ�Ľ��������еĴ����̱��agent_num��Ϣ��ѯ�������丸�����Ϣ agent_info*/
		agList = dbdao.selAgdata(audMap.get("agent_num"));
		if(agList.size() == 0) {
			return false;
		}
		
		/** �Ƿ�mypos �ն����� 0-��ͳPos 1-Mpos 2-����Pos 3-���� */
		if ("1".equals(audMap.get("pos_type")) && "H007".equals(RunningData.getMsgtype())) {
			/** ��ѯ��ǰ�ն��Ƿ��ǻ�ն� */
			/*ȡ������actId�������Ϊ�գ����ǻ�������Ƿ�isActΪ1���ǣ����ͼ�proMarkΪ1����ͼۣ�*/
			actId = dbdao.isAct(audMap.get("localdate"));
			/*�ж��Ƿ��лid���о��ǻ״̬*/
			if (!"".equals(actId)) {
				isAct = "1"; // �Ƿ� 0 ���� 1 ��
				proMark = "1"; // 0-����׼� 1-��׼� 2-��Ա�׼�
			}
			
			/** �鿴��ǰ�̻��Ƿ��ǻ�Ա�̻� */
			/*�ж��Ƿ��ǻ�Ա������Чʱ�䷶Χ��*/
			if (!"0000-00-00".equals(audMap.get("vip_status")) && DateUtil.getbooleanDay(audMap.get("vip_status"))) {
				isVip = "1"; // �Ƿ�VIP 0 ���� 1 ��
				proMark = "2"; // 0-����׼� 1-��׼� 2-��Ա�׼�
			}
		}
		audMap.put("actId", actId);
		audMap.put("isAct", isAct);
		audMap.put("proMark", proMark);
		audMap.put("isVip", isVip);
		audMap.put("isChannel", isChannel);

		/*���㸽�ӷѣ�˰ǰ˰��*/
		Map<String, Object> chaMap = pro.taxpointCha(audMap);
		audMap.put("charge_buckle",chaMap.get("charge_buckle").toString());
		audMap.put("charge_after",chaMap.get("charge_after").toString());
//		/*�����û������µĽ��׽��*/
//		BigDecimal sumAmount = dbdao.sumAmount();
//		/*�ж��Ƿ�δ����5���׽�δ�ﵽ���ӷ��ʣ��ﵽ�˲�����*/
//		System.out.println("�������ڵĽ��׽�"+sumAmount);
//		/*���������ڵ��ܽ��׽��С��5��ʱ*/
//		if(sumAmount.compareTo(new BigDecimal("50000")) < 0){
//			/*ȡ�����Ķ�����������Ϣ*/
//			Map<String, String> map = agList.get(agList.size() - 1);
//			System.out.println("������������Ϣ��");
//			for(Map.Entry<String,String> entry:map.entrySet()){
//				System.out.println(entry.getKey()+"��"+entry.getValue());
//			}
//		}
//		System.out.println("-------------------");

		return true;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] checkIp <br>
	 * [����] ipУ�� <br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����2:08:47 <br>
	 *********************************************************.<br>
	 */
	public boolean checkIp() throws Exception {
		String[] strIp = InitConfig.ip;
		for (int i = 0; i < strIp.length; i++) {
			if(RunningData.getIp().equals(strIp[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] checkMak <br>
	 * [����] makУ�� <br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��30�� ����2:09:09 <br>
	 *********************************************************.<br>
	 */
	public boolean checkMak() throws Exception {
		return RunningData.getSignature().equals(pro.holdSignature(posReqMap)) ? true : false;
	}
}
