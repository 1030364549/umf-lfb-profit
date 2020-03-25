
package com.umf.utils;

import java.math.BigDecimal;

import com.umf.config.InitConfig;

public class Utils {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] multiply <br>
	 * [����] ���ʵ�һ�� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��10��31�� ����10:00:19 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal firstMultiply(String amount, String ar_content, String sett_price) throws Exception {
		/*������ͼ�% ���ڿ����㷨ʱ������0*/
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		}
		/*���׽��*(����-����ͼ�)���������룬������λС��*/
		return new BigDecimal(amount).multiply(new BigDecimal(ar_content).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] noFirstMultiply <br>
	 * [����] ���ʷǵ�һ�� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��10��31�� ����11:49:20 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstMultiply(String amount, String ar_content, String upPrice, String sett_price) throws Exception {
		/*������׼ۣ�%��С�ڿ����㷨����*/
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		/*������׼ۣ�%��С����ʱ�����ѽ���׼ۼ���*/
		}else if(new BigDecimal(sett_price).compareTo(new BigDecimal(upPrice)) == 1) {
			return new BigDecimal("0");
		/*����ʱ�����ѽ���׼ۼ�ȥ����׼ۣ�%��С�ڿ����㷨����*/
		}else if (new BigDecimal(upPrice).subtract(new BigDecimal(sett_price)).compareTo(new BigDecimal(ar_content)) == 1) {
			return new BigDecimal("0");
		}else {
			/*���ؽ��׽��amount*����ʱ�����ѽ���׼�-����׼ۣ�%������������λС�����������룬�ó�������*/
			return new BigDecimal(amount).multiply(new BigDecimal(upPrice).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] firstSubtract <br>
	 * [����] �ⶥ��һ�� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��10��31�� ����12:37:00 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal firstSubtract(String charge, String ceiling_cost) throws Exception {
		if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(charge)) == 1){
			return new BigDecimal("0");
		}
		return new BigDecimal(charge).subtract(new BigDecimal(ceiling_cost));
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] noFirstSubtract <br>
	 * [����] �ⶥ�ǵ�һ�� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��10��31�� ����12:37:03 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstSubtract(String charge, String upPrice , String ceiling_cost) throws Exception {
		/*�ж���߳ɱ��Ƿ����*/
		if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(charge)) == 1){
			return new BigDecimal("0");
		}else if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(upPrice)) == 1) {
			return new BigDecimal("0");
		}else if (new BigDecimal(upPrice).subtract(new BigDecimal(ceiling_cost)).compareTo(new BigDecimal(charge)) == 1) {
			return new BigDecimal("0");
		}else {
			return new BigDecimal(upPrice).subtract(new BigDecimal(ceiling_cost));
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] taxMoney <br>
	 * [����] ��˰ <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����12:58:14 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxMoney (String money, String taxPoint) throws Exception {
		/*�ж��Ƿ����˰��ⶥֵ0.08*/
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal(InitConfig.tax)) == 1){
			/*���*���˰��0.08����������֮���������룬������λС��*/
			return new BigDecimal(money).multiply(new BigDecimal(InitConfig.tax)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		/*��С��˰��ⶥֵ��ʱ�򣬽��*˰�ʣ��������뱣����λС��*/
		return new BigDecimal(money).multiply(new BigDecimal(taxPoint)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] taxDiff <br>
	 * [����] ˰�� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��11��4�� ����12:57:52 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxDiff(String money, String upTax , String taxPoint) throws Exception {
		/*�ж��Ƿ����˰��ⶥֵ*/
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal(InitConfig.tax)) == 1){
			return new BigDecimal("0");
			/*�ж��Ƿ����upTax*/
		}else if(new BigDecimal(taxPoint).compareTo(new BigDecimal(upTax)) == 1) {
			return new BigDecimal("0");
			/*�жϼ�ȥ��˰����Ƿ����˰��ⶥֵ*/
		}else if (new BigDecimal(upTax).subtract(new BigDecimal(taxPoint)).compareTo(new BigDecimal(InitConfig.tax)) == 1) {
			return new BigDecimal("0");
		}else {
			/*���ؽ��*��upTax-��˰�㣩��Ȼ���������뱣����λС��*/
			return new BigDecimal(money).multiply(new BigDecimal(upTax).subtract(new BigDecimal(taxPoint))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
}

	