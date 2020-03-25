package com.umf.service.profitservice.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.umf.config.InitConfig;
import com.umf.service.profitservice.Profit;
import com.umf.utils.EncryUtil;
import com.umf.utils.LoFunction;
import com.umf.utils.LogUtil;
import com.umf.utils.RunningData;
import com.umf.utils.Utils;

@SuppressWarnings("all")
public class ProfitImpl implements Profit {
	
	private LoFunction lo = new LoFunction();
	private EncryUtil eu = new EncryUtil();
	private Utils ut = new Utils();
	private LogUtil log = new LogUtil();

	@Override
	public Map<String, String> posReqMap(String reqpack) throws Exception {
		Map<String, String> posReqMap = lo.changeJsonToMap(reqpack);
		RunningData.setSerial(posReqMap.get("serial"));
		RunningData.setMerno(posReqMap.get("merno"));
		RunningData.setPosno(posReqMap.get("posno"));
		RunningData.setMsgtype(posReqMap.get("msgtype"));
		RunningData.setSignature(posReqMap.get("signature"));
		log.saveMaplog(reqpack);
		return posReqMap;
	}

	@Override
	public String holdSignature(Map<String, String> posReqMap) throws Exception {
		Map<String, String> body = new TreeMap<String, String>();
		posReqMap.remove("signature");
		StringBuffer sb=new StringBuffer();
		sb.append("serial=").append(posReqMap.get("serial"));
		sb.append("&");
		sb.append("merno=").append(posReqMap.get("merno"));
		sb.append("&");
		sb.append("posno=").append(posReqMap.get("posno"));
		sb.append("&");
		sb.append("msgtype=").append(posReqMap.get("msgtype"));
		return eu.encryptMD5(sb.toString());
	}

	@Override
	public Map<String, Object> chargeProfit(Map<String,String> auMap ,int i) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		/*根据费率类型计算金额*/
		switch (auMap.get("ar_type")) {
		case "0":/*费率*/
			map = rate(auMap , i);
			break;
		case "1":/*封顶*/
			map = ceiling(auMap , i);
			break;
		}
		return map;
	}
	
	public Map<String, Object> rate(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal proMoney = new BigDecimal("0");   // 本级分润金额
		BigDecimal proSumMoney = new BigDecimal("0");   // 本级及以下分润金额
		/*倒序输出，首位为本级，本级没有下级，当不是本级，为父级别时再进行判断计算本级以下分润*/
		if (i > 0) {
			/*结算底价（%）是否为空*/
			if(!"".equals(auMap.get("sett_price"))) {
				/*判断临时手续费结算底价是否为空*/
				if(!"".equals(auMap.get("upPrice"))) {		/*交易金额，扣率算法(P*0.01)，临时手续费结算底价，结算底价（%）*/
					proMoney = ut.noFirstMultiply(auMap.get("amount"),auMap.get("ar_content"),auMap.get("upPrice"),auMap.get("sett_price"));
					proSumMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("ar_content"),auMap.get("sett_price"));
				}else {
					proMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("ar_content"),auMap.get("sett_price"));
					proSumMoney = proMoney;
				}
			}
		}else {
			if(!"".equals(auMap.get("sett_price"))) {
				proMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("ar_content"),auMap.get("sett_price"));
				proSumMoney = proMoney;
			}
		}
		map.put("proMoney", proMoney);
		map.put("proSumMoney", proSumMoney);
		map.put("upPrice", auMap.get("sett_price"));
		return map;
	}
	
	public Map<String, Object> ceiling(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal proMoney = new BigDecimal("0");
		BigDecimal proSumMoney = new BigDecimal("0");
		String upPrice = "";
		if (i > 0) {
			/*最高价格*/
			if (!"".equals(auMap.get("ceiling_cost"))) {
				if (!"".equals(auMap.get("upPrice"))) {
					proMoney = ut.noFirstSubtract(auMap.get("charge"),auMap.get("upPrice"),auMap.get("ceiling_cost"));
					proSumMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
				}else {
					proMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
					proSumMoney = proMoney;
				}
			}
		}else {
			if (!"".equals(auMap.get("ceiling_cost"))) {
				proMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
				proSumMoney = proMoney;
			}
		}
		upPrice = auMap.get("ceiling_cost");
		map.put("proMoney", proMoney);
		map.put("proSumMoney", proSumMoney);
		map.put("upPrice", upPrice);
		return map;
	}
	
	@Override
	public Map<String, Object> additProfit(Map<String, String> auMap , int i) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (auMap.get("aff_type")) {
		case "0":	/*费率*/
			map = addRate(auMap, i);
			break;
		case "1":	/*封顶*/
			map = additCeiling(auMap, i);
			break;
		}
		return map;
	}
	
	public Map<String, Object> addRate(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal addproMoney = new BigDecimal("0");
		BigDecimal addproSumMoney = new BigDecimal("0");
		String addupPrice = "";
		if (i > 0) {
			if(!"".equals(auMap.get("surcharge"))) {
				if(!"".equals(auMap.get("addupPrice"))) {
					addproMoney = ut.noFirstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("addupPrice"),auMap.get("surcharge"));
					addproSumMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
				}else {
					addproMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
					addproSumMoney = addproMoney;
				}
			}
		}else {
			if(!"".equals(auMap.get("surcharge"))) {
				addproMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
				addproSumMoney = addproMoney;
			}
		}
		addupPrice = auMap.get("surcharge");
		map.put("addproMoney", addproMoney);
		map.put("addproSumMoney", addproSumMoney);
		map.put("addupPrice", addupPrice);
		return map;
	}
	
	public Map<String, Object> additCeiling(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal addproMoney = new BigDecimal("0");
		BigDecimal addproSumMoney = new BigDecimal("0");
		if (i > 0) {
			if (!"".equals(auMap.get("surcharge_ein"))) {
				if (!"".equals(auMap.get("addupPrice"))) {
					addproMoney = ut.noFirstSubtract(auMap.get("affix_charge"),auMap.get("addupPrice"),auMap.get("surcharge_ein"));
					addproSumMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
				}else {
					addproMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
					addproSumMoney = addproMoney;
				}
			}
		}else {
			if (!"".equals(auMap.get("surcharge_ein"))) {
				addproMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
				addproSumMoney = addproMoney;
			}
		}
		map.put("addproMoney", addproMoney);
		map.put("addproSumMoney", addproSumMoney);
		map.put("addupPrice", auMap.get("surcharge_ein"));
		return map;
	}

	@Override
	public Map<String, Object> taxpoint(Map<String, String> auMap,String taxproSumMoney) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal level_tax = new BigDecimal("0");    
		BigDecimal below_tax = new BigDecimal("0");   
		BigDecimal level_tax_profit = new BigDecimal("0");  
		BigDecimal below_tax_profit = new BigDecimal("0");   
		BigDecimal profit_tax_diff = new BigDecimal("0");  
		int level = Integer.parseInt(auMap.get("agent_level"));
		if (!"".equals(auMap.get("tax_point"))) {
			level_tax = ut.taxMoney(auMap.get("proMoney"),auMap.get("tax_point"));
			below_tax = ut.taxMoney(auMap.get("proSumMoney"),auMap.get("tax_point"));
			level_tax_profit = new BigDecimal(auMap.get("proMoney")).subtract(level_tax);
			below_tax_profit = new BigDecimal(auMap.get("proSumMoney")).subtract(below_tax);
		}
		
		if(level <= 1) {
			if(!"".equals(auMap.get("tax_point"))) {
				if(!"".equals(auMap.get("upTax"))) {
					profit_tax_diff = profit_tax_diff.add(ut.taxDiff(taxproSumMoney,auMap.get("upTax"),auMap.get("tax_point")));
				}
			}
		}
		
		map.put("level_tax", level_tax);
		map.put("below_tax", below_tax);
		map.put("level_tax_profit", level_tax_profit);
		map.put("below_tax_profit", below_tax_profit);
		map.put("profit_tax_diff", profit_tax_diff);
		map.put("upTax", auMap.get("tax_point"));
		return map;
	}

	public Map<String,Object> taxpointCha(Map<String, String> auMap) throws Exception{
		Map<String, Object> chaMap = new HashMap<String, Object>();
		/*扣除的附加费税*/
		BigDecimal charge_buckle = new BigDecimal("0");
		/*扣税后的附加费*/
		BigDecimal charge_after = new BigDecimal("0");
		/*附加费*/
		String affix_charge = auMap.get("affix_charge");
		if(!"".equals(affix_charge)&&!"0".equals(affix_charge)&&affix_charge!=null) {
			/*扣除的附加费税：附加费*税点封顶值0.08*/
			charge_buckle = new BigDecimal(affix_charge).multiply(new BigDecimal(InitConfig.tax));
			/*扣税后的附加费：附加费-扣的税*/
			charge_after = new BigDecimal(affix_charge).subtract(charge_buckle);
		}
		chaMap.put("charge_buckle",charge_buckle);
		chaMap.put("charge_after",charge_after);
		return chaMap;
	}
}
