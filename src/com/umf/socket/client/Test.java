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
        /*��ѯ��������*/
        BigDecimal rate = new BigDecimal("0");
        if(!"".equals(audMap)&&audMap!=null){
            BigDecimal rsRate = new BigDecimal("0.004");
            System.out.println("��ѯ���Ļ������ʣ�"+rsRate);
			/*����ѯ���Ļ������ʴ���0��ʱ���丳ֵ��rate*/
            if(rsRate.compareTo(BigDecimal.ZERO)==1){
                rate = rsRate;
            }
        }
        System.out.println("�������ʣ�"+rate);
		/*������*/
        BigDecimal charge = new BigDecimal("0");
        if(!"".equals(audMap.get("charge"))&&audMap.get("charge")!=null){
            /*�������Ѳ�Ϊ�գ��Ҵ���0��ʱ��ֵ*/
            if((new BigDecimal(audMap.get("charge"))).compareTo(BigDecimal.ZERO)==1){
                charge = new BigDecimal(audMap.get("charge"));
            }
        }
        System.out.println("�����ѣ�"+charge);
		/*���׽��*/
        BigDecimal amount = new BigDecimal("0");
        if(!"".equals(audMap.get("amount"))&&audMap.get("amount")!=null){
            /*�����׽�Ϊ�գ��Ҵ���0��ʱ��ֵ*/
            if((new BigDecimal(audMap.get("amount"))).compareTo(BigDecimal.ZERO)==1) {
                amount = new BigDecimal(audMap.get("amount"));
            }
        }
        System.out.println("���׽�"+amount);
		/*�����ɱ������׽��*�������*/
        BigDecimal cost = rate.multiply(amount);
        System.out.println("�����ɱ���"+cost);
		/*��������������-�����ɱ�*/
        BigDecimal profit = new BigDecimal("0");
        /*�������Ѵ��ڻ����ɱ���ʱ��������*/
		if(charge.compareTo(cost)==1){
             profit = charge.subtract(cost);
        }
        System.out.println("��������"+profit);

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
            System.out.println("����10000��ĳ��ȣ�"+costLength);
            /*λ������ǰ�߲���0*/
            StringBuffer stringBuffer = new StringBuffer("");
            for(int i=0;i<11-costLength;i++){
                stringBuffer.append("0");
            }
            repairCost = stringBuffer.append(repairCost).toString();
        }
        System.out.println("��λ��Ļ����ɱ���"+repairCost);
        /*�����ɱ����������ʣ���������*/
        audMap.put("agent_cost",repairCost);
        audMap.put("channel_rate",rate.toString());
        audMap.put("channel_profit",profit.toString());
    }


}
