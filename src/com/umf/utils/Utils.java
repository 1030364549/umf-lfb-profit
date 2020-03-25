
package com.umf.utils;

import java.math.BigDecimal;

import com.umf.config.InitConfig;

public class Utils {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] multiply <br>
	 * [描述] 费率第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 上午10:00:19 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal firstMultiply(String amount, String ar_content, String sett_price) throws Exception {
		/*当结算低价% 大于扣率算法时，返回0*/
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		}
		/*交易金额*(扣率-结算低价)，四舍五入，保留两位小数*/
		return new BigDecimal(amount).multiply(new BigDecimal(ar_content).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] noFirstMultiply <br>
	 * [描述] 费率非第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 上午11:49:20 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstMultiply(String amount, String ar_content, String upPrice, String sett_price) throws Exception {
		/*当结算底价（%）小于扣率算法继续*/
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		/*当结算底价（%）小于临时手续费结算底价继续*/
		}else if(new BigDecimal(sett_price).compareTo(new BigDecimal(upPrice)) == 1) {
			return new BigDecimal("0");
		/*当临时手续费结算底价减去结算底价（%）小于扣率算法继续*/
		}else if (new BigDecimal(upPrice).subtract(new BigDecimal(sett_price)).compareTo(new BigDecimal(ar_content)) == 1) {
			return new BigDecimal("0");
		}else {
			/*返回交易金额amount*（临时手续费结算底价-结算底价（%）），保留两位小数，四舍五入，得出手续费*/
			return new BigDecimal(amount).multiply(new BigDecimal(upPrice).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] firstSubtract <br>
	 * [描述] 封顶第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 下午12:37:00 <br>
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
	 * [方法] noFirstSubtract <br>
	 * [描述] 封顶非第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 下午12:37:03 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstSubtract(String charge, String upPrice , String ceiling_cost) throws Exception {
		/*判断最高成本是否大于*/
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
	 * [方法] taxMoney <br>
	 * [描述] 扣税 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午12:58:14 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxMoney (String money, String taxPoint) throws Exception {
		/*判断是否大于税点封顶值0.08*/
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal(InitConfig.tax)) == 1){
			/*金额*最大税率0.08，计算出金额之后四舍五入，保留两位小数*/
			return new BigDecimal(money).multiply(new BigDecimal(InitConfig.tax)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		/*当小于税点封顶值的时候，金额*税率，四舍五入保留两位小数*/
		return new BigDecimal(money).multiply(new BigDecimal(taxPoint)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] taxDiff <br>
	 * [描述] 税差 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午12:57:52 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxDiff(String money, String upTax , String taxPoint) throws Exception {
		/*判断是否大于税点封顶值*/
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal(InitConfig.tax)) == 1){
			return new BigDecimal("0");
			/*判断是否大于upTax*/
		}else if(new BigDecimal(taxPoint).compareTo(new BigDecimal(upTax)) == 1) {
			return new BigDecimal("0");
			/*判断减去纳税点后是否大于税点封顶值*/
		}else if (new BigDecimal(upTax).subtract(new BigDecimal(taxPoint)).compareTo(new BigDecimal(InitConfig.tax)) == 1) {
			return new BigDecimal("0");
		}else {
			/*返回金额*（upTax-纳税点），然后四舍五入保留两位小数*/
			return new BigDecimal(money).multiply(new BigDecimal(upTax).subtract(new BigDecimal(taxPoint))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
}

	