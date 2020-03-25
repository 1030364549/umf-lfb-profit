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
	 * [����] getCurrentDateTime <br>
	 * [����] ��ȡϵͳʱ��<br>
	 * [����] ��ʽ<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:44:49 <br>
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
	 * [����] toStrDate <br>
	 * [����] ��ʱ�䡢����ƴ�ӳ�yyyy-MM-dd hh:mm:ss��ʽ <br>
	 * [����] ʱ�䡢����<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:46:08 <br>
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
	 * [����] MMDD <br>
	 * [����] ��yyyy-MM-ddת����mmdd<br>
	 * [����] ʱ��<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:48:22 <br>
	 *********************************************************.<br>
	 */
	public String MMDD(String date) throws Exception {
		String[] dates = date.split("-");
		return (lo.appendField(dates[1], dates[2]));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] HHMMSS <br>
	 * [����] ��hh��mm��ssת����hhmmss<br>
	 * [����] ʱ��<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:48:45 <br>
	 *********************************************************.<br>
	 */
	public String HHMMSS(String time) throws Exception {
		String[] times = time.split(":");
		return (lo.appendField(times[0], times[1], times[2]));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] getGroupDateTime <br>
	 * [����] ��ȡ��ǰyyy-MM-dd,HH:mm:ss<br>
	 * [����] <br>
	 * [����] String[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:49:06 <br>
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
	 * [����] getLocaldate <br>
	 * [����] ���ݵ�ǰʱ���ȥ�����죨����,��ȥ��������<br>
	 * [����] ʱ��,����<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:49:27 <br>
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
	 * [����] getbooleanDay <br>
	 * [����] ��ǰʱ���Ƿ�����Чʱ����<br>
	 * [����] ʱ��<br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:49:50 <br>
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
