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
	private String isAct = "0"; // 是否活动 0 不是 1 是
	private String isVip = "0"; // 是否VIP 0 不是 1 是
	private String proMark = "0"; // 0-分润底价 1-活动底价 2-会员底价
	private String isChannel = "0"; // 是否是渠道 0-否 1-是
	private String actId = "";
	private Map<String, String> audMap;/*未发的分润清算数据信息auditdata*/
	private List<Map<String, String>> agList;/*商户信息agent_info*/
	private Map<String, Map<String, String>> selPolMap;
	private Map<String, String> channelPolMap;
	private Jpush jpush = new Jpush();
	
	public TradingService(String reqpack, String ipp) {
		this.reqpack = reqpack;
		this.ip = ipp;
		try {
			RunningData.removeAll();  // 清除共享线程变量
			RunningData.setIp(ip);
			/*将传入的json字符串转换成map集合，并且为共享线程变量赋值*/
			posReqMap = pro.posReqMap(reqpack);
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	public void mainService() throws Exception {
		try {
			
//			/** IP校验 */
//			if(!checkIp()) {
//				System.out.println("上送IP地址异常......");
//				return;
//			}
			
			/** 校验mac */
			/*将传来数据的签名删除，然后再次拼接所有数据MD5加密形成签名，判断与之前的共享线程变量的签名是否相同*/
			if(!checkMak()) {
				System.out.println("Mak校验异常......");
				return;
			}
			
			/** 校验是否渠道  */
			/*从用户信息中判断代理商性质是否为机构，若是机构返回true，是渠道类型*/
			if(dbdao.selAgNat()) {
				isChannel = "1";   // 是否是渠道 0-否 1-是
			}
			
			/** 反射到方法 */
			/*根据是否为渠道，选择方法执行*/
			getClass().getMethod("deal_0" + isChannel, new Class[] {}).invoke(this);
			
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] pro_f0 <br>
	 * [描述] 非渠道分润<br>
	 * [返回] void <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午1:13:06 <br>
	 *********************************************************.<br>
	 */
	public void deal_00() throws Exception {
		if(!deal()) {
			return;
		}
		/** 查询代理结算底价  */
		/*根据未发的分润结算数据取出数据，Map<id,map>集合，以商户编号为key，费用信息为value*/
		selPolMap = dbdao.selPoldata(audMap);
		String upPrice = "";   // 临时手续费结算底价
		String addupPrice = "";  // 临时附加结算底价
		String upTax = ""; // 临时税点底价
		String taxproSumMoney = "0";  // 临时本级及以下分润金额
		/*遍历所有商户信息agList*/
		for (int i = 0; i < agList.size(); i++) {
			Map<String,String> auMap = new HashMap<String,String>();
			auMap.putAll(audMap);
			Map<String, String> agMap = agList.get(i);
			/*以当前用户的代理编号为key取出清算费用信息map集合*/
			Map<String, String> polMap = selPolMap.get(agMap.get("agent_num"));
			/*将费用信息存入清算集合中*/
			if(polMap == null) {
				auMap.put("sett_price", "");	/*结算底价（%）*/
				auMap.put("ceiling_cost", "");	/*封顶成本（元/笔） */
				auMap.put("surcharge", "");		/*附加手续费底价（%）*/
				auMap.put("surcharge_ein", "");	/*附加手续费底价（元/笔）*/
				auMap.put("tax_point", "");		/*计税点*/
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
			/*取出用户信息的代理级别并存入到auMap中*/
			auMap.put("agent_level", agMap.get("agent_level"));
			/** 计算手续费分润  */
			Map<String, Object> charMap = pro.chargeProfit(auMap , i);
			upPrice = charMap.get("upPrice").toString();
			auMap.put("proMoney", charMap.get("proMoney").toString());
			auMap.put("proSumMoney", charMap.get("proSumMoney").toString());
			
			/** 计算附加手续费分润  */
			/*sett_type  结算类型 0：D0 1：T1*/
			if("0".equals(auMap.get("sett_type"))) {
				Map<String, Object> addMap = pro.additProfit(auMap,i);
				addupPrice = addMap.get("addupPrice").toString();
				auMap.put("addproMoney", addMap.get("addproMoney").toString());
				auMap.put("addproSumMoney", addMap.get("addproSumMoney").toString());
			}
			/*判断代理商等级*/
			if("1".equals(auMap.get("agent_level"))) {
				taxproSumMoney = charMap.get("proSumMoney").toString();
			}
			/** 计算税差  */
			Map<String, Object> taxMap = pro.taxpoint(auMap,taxproSumMoney);
			auMap.put("level_tax", taxMap.get("level_tax").toString());  // 本级扣税
			auMap.put("below_tax", taxMap.get("below_tax").toString());  // 本级及以下扣税
			auMap.put("level_tax_profit", taxMap.get("level_tax_profit").toString());  // 本级扣税后分润金额
			auMap.put("below_tax_profit", taxMap.get("below_tax_profit").toString());  // 本级及以下扣税后分润金额
			auMap.put("profit_tax_diff", taxMap.get("profit_tax_diff").toString());  // 本级税差
			upTax = taxMap.get("upTax").toString();
			System.out.println("税费用："+audMap.get("charge_buckle"));
			System.out.println("扣税后："+audMap.get("charge_after"));
//			dbdao.insProData(auMap, agMap);
//			jpush.pAll(agMap.get("agent_num"),auMap.get("level_tax_profit"));
		}
//		dbdao.upProData();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] pro_f1 <br>
	 * [描述] 渠道分润<br>
	 * [返回] void <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午1:12:53 <br>
	 *********************************************************.<br>
	 */
	public void deal_01() throws Exception {
		if(!deal()) {
			return;
		}
		/*查询机构费率*/
		BigDecimal rate = dbdao.selChaRate(audMap);
		/*机构成本，分润费率*交易金额*/
		BigDecimal cost = rate.multiply(new BigDecimal(audMap.get("amount")));
		/*机构分润，手续费-机构成本*/
		BigDecimal profit = new BigDecimal(audMap.get("charge")).subtract(cost);
        /*补位后的机构成本*/
		String repairCost = "";
		/*当机构成本为0时，补位为11位0*/
		if(cost.compareTo(BigDecimal.ZERO)==0){
			repairCost = "00000000000";
		}else{
            /*不为0时，乘以10000去除小数点，前补0到11位*/
			repairCost = cost.multiply(new BigDecimal("10000")).setScale(0,BigDecimal.ROUND_DOWN)
					.toString();
			Integer costLength = repairCost.length();
            /*前边补的0*/
			StringBuffer stringBuffer = new StringBuffer("");
			for(int i=0;i<11-costLength;i++){
				stringBuffer.append("0");
			}
			repairCost = stringBuffer.append(repairCost).toString();
		}
		/*机构成本，机构利润*/
		audMap.put("channel_cost",repairCost);
		audMap.put("channel_profit",profit.toString());
		/*将渠道分润信息添加到渠道分润信息表中*/
		Integer i = dbdao.insChaData(audMap);
		/*修改清算表状态码为1，已发*/
		dbdao.upProData();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] deal <br>
	 * [描述] <br>
	 * [参数] <br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年9月2日 下午1:31:45 <br>
	 *********************************************************.<br>
	 */
	public boolean deal() throws Exception {
		
		/** 根据流水号查询分润表是否存在 */
		/*根据是否为渠道选择sql，若不是渠道则根据交易流水serial查询利润信息profit_detailed总条数，
		* 若是渠道则根据交易流水serial查询渠道利润信息channel_profit_detailed总条数
		* 当总条数为0时继续*/
		if(!dbdao.selSerial(isChannel)) {
			return false;
		}

		/** 查询要分润的结算数据 */
		/*根据商户编号、交易类型、交易流水号和 未发分润状态is_profit（0：未发，1：已发）查询要结算的数据*/
		audMap = dbdao.selAuditdata();
		if(audMap == null) {
			return false;
		}
		
		/** 查询代理商信息 */
		/*根据上方查询的结算数据中的代理商编号agent_num信息查询本级及其父类的信息 agent_info*/
		agList = dbdao.selAgdata(audMap.get("agent_num"));
		if(agList.size() == 0) {
			return false;
		}
		
		/** 是否mypos 终端类型 0-传统Pos 1-Mpos 2-智能Pos 3-机构 */
		if ("1".equals(audMap.get("pos_type")) && "H007".equals(RunningData.getMsgtype())) {
			/** 查询当前终端是否是活动终端 */
			/*取出活动编号actId，如果不为空，就是活动，设置是否活动isAct为1（是），低价proMark为1（活动低价）*/
			actId = dbdao.isAct(audMap.get("localdate"));
			/*判断是否有活动id，有就是活动状态*/
			if (!"".equals(actId)) {
				isAct = "1"; // 是否活动 0 不是 1 是
				proMark = "1"; // 0-分润底价 1-活动底价 2-会员底价
			}
			
			/** 查看当前商户是否是会员商户 */
			/*判断是否是会员且在有效时间范围内*/
			if (!"0000-00-00".equals(audMap.get("vip_status")) && DateUtil.getbooleanDay(audMap.get("vip_status"))) {
				isVip = "1"; // 是否VIP 0 不是 1 是
				proMark = "2"; // 0-分润底价 1-活动底价 2-会员底价
			}
		}
		audMap.put("actId", actId);
		audMap.put("isAct", isAct);
		audMap.put("proMark", proMark);
		audMap.put("isVip", isVip);
		audMap.put("isChannel", isChannel);

		/*计算附加费，税前税后*/
		Map<String, Object> chaMap = pro.taxpointCha(audMap);
		audMap.put("charge_buckle",chaMap.get("charge_buckle").toString());
		audMap.put("charge_after",chaMap.get("charge_after").toString());
//		/*计算用户三个月的交易金额*/
//		BigDecimal sumAmount = dbdao.sumAmount();
//		/*判断是否未到达5万交易金额，未达到增加费率，达到了不增加*/
//		System.out.println("三个月内的交易金额："+sumAmount);
//		/*当三个月内的总交易金额小于5万时*/
//		if(sumAmount.compareTo(new BigDecimal("50000")) < 0){
//			/*取出它的顶级代理商信息*/
//			Map<String, String> map = agList.get(agList.size() - 1);
//			System.out.println("顶级代理商信息：");
//			for(Map.Entry<String,String> entry:map.entrySet()){
//				System.out.println(entry.getKey()+"："+entry.getValue());
//			}
//		}
//		System.out.println("-------------------");

		return true;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] checkIp <br>
	 * [描述] ip校验 <br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午2:08:47 <br>
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
	 * [方法] checkMak <br>
	 * [描述] mak校验 <br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月30日 下午2:09:09 <br>
	 *********************************************************.<br>
	 */
	public boolean checkMak() throws Exception {
		return RunningData.getSignature().equals(pro.holdSignature(posReqMap)) ? true : false;
	}
}
