package com.umf.socket.client;

import com.umf.utils.LoFunction;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author:LiuYuKun
 * @Date:2019/11/12
 */
public class Test {
    public static void main(String[] args) {
        /*byte [] b = {0,0};
        LoFunction loFunction = new LoFunction();
        try {
            int i = loFunction.byteArrayToShort(b);
            System.out.println(i);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

//        StringBuffer stringBuffer = new StringBuffer("1");
//        String stringBuffer = "1";
//        Map<Object,Object> map = new HashMap();
//        map.put("~",stringBuffer);
//        for(Map.Entry<Object,Object> list:map.entrySet()){
//            System.out.println(list.getValue());
//        }
//        stringBuffer = "111";
//        for(Map.Entry<Object,Object> list:map.entrySet()){
//            System.out.println(list.getValue());
//        }


        Map<String,String> audMap = new HashMap(){
            {
                put("charge","0.5");
                put("amount","100");
            }
        };
        /*查询机构费率*/
        BigDecimal rate = new BigDecimal("0");
        if(!"".equals(audMap)&&audMap!=null){
            BigDecimal rsRate = new BigDecimal("0.004");
            System.out.println("查询出的机构费率："+rsRate);
			/*当查询出的机构费率大于0的时候将其赋值给rate*/
            if(rsRate.compareTo(BigDecimal.ZERO)==1){
                rate = rsRate;
            }
        }
        System.out.println("机构费率："+rate);
		/*手续费*/
        BigDecimal charge = new BigDecimal("0");
        if(!"".equals(audMap.get("charge"))&&audMap.get("charge")!=null){
            /*当手续费不为空，且大于0的时候赋值*/
            if((new BigDecimal(audMap.get("charge"))).compareTo(BigDecimal.ZERO)==1){
                charge = new BigDecimal(audMap.get("charge"));
            }
        }
        System.out.println("手续费："+charge);
		/*交易金额*/
        BigDecimal amount = new BigDecimal("0");
        if(!"".equals(audMap.get("amount"))&&audMap.get("amount")!=null){
            /*当交易金额不为空，且大于0的时候赋值*/
            if((new BigDecimal(audMap.get("amount"))).compareTo(BigDecimal.ZERO)==1) {
                amount = new BigDecimal(audMap.get("amount"));
            }
        }
        System.out.println("交易金额："+amount);
		/*机构成本，交易金额*分润费率*/
        BigDecimal cost = rate.multiply(amount);
        System.out.println("机构成本："+cost);
		/*机构分润，手续费-机构成本*/
        BigDecimal profit = new BigDecimal("0");
        /*当手续费大于机构成本的时候做减法*/
		if(charge.compareTo(cost)==1){
             profit = charge.subtract(cost);
        }
        System.out.println("机构分润："+profit);

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
            System.out.println("乘以10000后的长度："+costLength);
            /*位数不够前边补的0*/
            StringBuffer stringBuffer = new StringBuffer("");
            for(int i=0;i<11-costLength;i++){
                stringBuffer.append("0");
            }
            repairCost = stringBuffer.append(repairCost).toString();
        }
        System.out.println("补位后的机构成本："+repairCost);
        /*机构成本，机构费率，机构利润*/
        audMap.put("agent_cost",repairCost);
        audMap.put("channel_rate",rate.toString());
        audMap.put("channel_profit",profit.toString());
    }


}
