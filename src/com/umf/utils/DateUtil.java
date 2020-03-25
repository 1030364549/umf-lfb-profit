package com.umf.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

private LoFunction lo = new LoFunction();
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getCurrentDateTime <br>
	 * [描述] 获取系统时间<br>
	 * [参数] 格式<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:44:49 <br>
	 *********************************************************.<br>
	 */
	public String getCurrentDateTime(String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String strDate = sdf.format(new Date());
		return strDate;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] toStrDate <br>
	 * [描述] 将时间、日期拼接成yyyy-MM-dd hh:mm:ss格式 <br>
	 * [参数] 时间、日期<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:46:08 <br>
	 *********************************************************.<br>
	 */
	public String toStrDate(String time, String date) throws Exception {
		StringBuffer dateStr = new StringBuffer();
		dateStr.append(getCurrentDateTime("yyyy"));
		dateStr.append("-");
		dateStr.append(date.substring(0, 2));
		dateStr.append("-");
		dateStr.append(date.substring(2));
		dateStr.append(" ");
		dateStr.append(time.substring(0, 2));
		dateStr.append(":");
		dateStr.append(time.substring(2, 4));
		dateStr.append(":");
		dateStr.append(time.substring(4));
		return dateStr.toString();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] MMDD <br>
	 * [描述] 将yyyy-MM-dd转换成mmdd<br>
	 * [参数] 时间<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:48:22 <br>
	 *********************************************************.<br>
	 */
	public String MMDD(String date) throws Exception {
		String[] dates = date.split("-");
		return (lo.appendField(dates[1], dates[2]));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] HHMMSS <br>
	 * [描述] 将hh：mm：ss转换成hhmmss<br>
	 * [参数] 时间<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:48:45 <br>
	 *********************************************************.<br>
	 */
	public String HHMMSS(String time) throws Exception {
		String[] times = time.split(":");
		return (lo.appendField(times[0], times[1], times[2]));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getGroupDateTime <br>
	 * [描述] 获取当前yyy-MM-dd,HH:mm:ss<br>
	 * [参数] <br>
	 * [返回] String[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:49:06 <br>
	 *********************************************************.<br>
	 */
	public static String[] getGroupDateTime() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd|HH:mm:ss");
		String strDate = sdf.format(new Date());
		return strDate.split("\\|");
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getLocaldate <br>
	 * [描述] 根据当前时间减去多少天（日期,减去的天数）<br>
	 * [参数] 时间,天数<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:49:27 <br>
	 *********************************************************.<br>
	 */
	public static String getLocaldate(String localdate,int dates) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=sdf.parse(localdate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dates);
		date = calendar.getTime();
		return sdf.format(date);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getbooleanDay <br>
	 * [描述] 当前时间是否在有效时间内<br>
	 * [参数] 时间<br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:49:50 <br>
	 *********************************************************.<br>
	 */
	public static boolean getbooleanDay(String date) throws Exception {
		DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		Date vip_format = date_format.parse(date);
		String xitong_date = date_format.format(new Date());
	    Date xitong_format = date_format.parse(xitong_date);
	    if(vip_format.getTime() >=  xitong_format.getTime()) {
	    	return true;
	    }
		return false;
	}
}
