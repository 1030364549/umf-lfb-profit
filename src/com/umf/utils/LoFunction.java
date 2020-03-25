package com.umf.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class LoFunction {
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] changeJsonToMap <br>
	 * [描述] 将Json字符串转换为Map集合 <br>
	 * [参数] Json字符串 <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:37:09 <br>
	 *********************************************************.<br>
	 */
	public Map<String, String> changeJsonToMap(String jsonStr){
		return JSONObject.fromObject(jsonStr);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] changeMapToJson <br>
	 * [描述] 将Map集合转换为Json字符串 <br>
	 * [参数] 集合 <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月13日 下午4:37:12 <br>
	 *********************************************************.<br>
	 */
	public String changeMapToJson(Map<String, String> mapData){
		return JSONObject.fromObject(mapData).toString();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] appendField <br>
	 * [描述] 拼接字符串<br>
	 * [参数] 将所有参数拼接<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:07:54 <br>
	 *********************************************************.<br>
	 */
	public String appendField(Object ... params) {
		StringBuffer sbf = new StringBuffer();
		for (Object str : params) {
			sbf.append(str);
		}
		return sbf.toString();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] convertMoney <br>
	 * [描述] 将12位的字符串转换为BigDecimal型，带有2位小数 <br>
	 * [参数] 金额 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:55:45 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal convertMoney(String money) throws Exception {
		String moneyStr1 = money.substring(0, money.length() - 2);
		String moneyStr2 = money.substring(money.length() - 2);
		String moneyStr = appendField(moneyStr1, ".", moneyStr2);
		return new BigDecimal(moneyStr).setScale(2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] convertMoney1 <br>
	 * [描述] 将带有小数点的字符串金额转换为12位金额<br>
	 * [参数] 金额<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:56:15 <br>
	 *********************************************************.<br>
	 */
	public String convertMoney1(String money) throws Exception {
		String totalMoney = money.substring(0, money.length()).replace(".", "");
		StringBuffer zero = new StringBuffer();
		for (int i = 0; i < 12 - totalMoney.length(); i++) {
			zero.append("0");
		}
		totalMoney = zero.append(totalMoney).toString();
		return totalMoney;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] changeMoney <br>
	 * [描述] 处理不带小数点的金额，补齐12位<br>
	 * [参数] 金额<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:56:32 <br>
	 *********************************************************.<br>
	 */
	public String changeMoney(String money) throws Exception {
		if (money.length() != 12) {
			StringBuffer x = new StringBuffer();
			for (int i = 0; i < 12 - money.length(); i++) {
				x.append("0");
			}
			return (x.append(money).toString());
		}
		return money;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] assemble <br>
	 * [描述] 将多个byte数组组合成一个byte数组<br>
	 * [参数] byte数组<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:56:46 <br>
	 *********************************************************.<br>
	 */
	public byte[] assemble(byte[]... b) throws Exception {
		int length = 0;
		for (byte[] bl : b) {
			if (bl != null)
				length += bl.length;
		}
		byte[] data = new byte[length];
		int count = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				System.arraycopy(b[i], 0, data, count, b[i].length);
				count += b[i].length;
			}
		}
		return data;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] convertChar <br>
	 * [描述] 将字符数转换为字节数<br>
	 * [参数] 字符数<br>
	 * [返回] int <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:57:04 <br>
	 *********************************************************.<br>
	 */
	public int convertChar(int s) {
		return (s % 2 == 0) ? s / 2 : (s + 1) / 2;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] shortToBytes <br>
	 * [描述] 将一个short类型的数据转换为字节并存入到指定的字节数组指定的位置。高位在前<br>
	 * [参数] short<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:57:14 <br>
	 *********************************************************.<br>
	 */
	public byte[] shortToBytes(short val) {
		byte[] b = new byte[2];
		for (int i = 0; i < 2; i++) {
			b[i] = (byte) (val >>> (8 - i * 8));
		}
		return b;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getBinaryFromByte <br>
	 * [描述] 将一个16字节数组转成128二进制数组<br>
	 * [参数] byte数组<br>
	 * [返回] boolean[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:57:36 <br>
	 *********************************************************.<br>
	 */
	public boolean[] getBinaryFromByte(byte[] b) throws Exception {
		boolean[] binary = new boolean[b.length * 8 + 1];
		StringBuffer strsum = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			strsum.append(getEigthBitsStringFromByte(b[i]));
		}
		for (int i = 0; i < strsum.length(); i++) {
			binary[i + 1] = ("1".equalsIgnoreCase(strsum.substring(i, i + 1))) ? true : false;
		}
		return binary;
	}

	private String getEigthBitsStringFromByte(int b) throws Exception {
		b |= 256; // mark the 9th digit as 1 to make sure the string
		String str = Integer.toBinaryString(b);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getByteFromBinary <br>
	 * [描述] 将一个128二进制数组转成16字节数组<br>
	 * [参数] 二进制数组<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:58:22 <br>
	 *********************************************************.<br>
	 */
	public byte[] getByteFromBinary(boolean[] binary) throws Exception {
		int num = (binary.length - 1) / 8;
		num = ((binary.length - 1) % 8 != 0) ? num + 1 : num;
		byte[] b = new byte[num];
		StringBuffer s = new StringBuffer();
		for (int i = 1; i < binary.length; i++) {
			s.append((binary[i]) ? "1" : "0");
		}
		String tmpstr;
		int j = 0;
		for (int i = 0; i < s.length(); i = i + 8) {
			tmpstr = s.substring(i, i + 8);
			b[j] = getByteFromEigthBitsString(tmpstr);
			j = j + 1;
		}
		return b;
	}

	private byte getByteFromEigthBitsString(String str) throws Exception {
		byte b;
		if ("1".equals(str.substring(0, 1))) {
			str = appendField("0", str.substring(1));
			b = Byte.valueOf(str, 2);
			b |= 128;
		} else {
			b = Byte.valueOf(str, 2);
		}
		return b;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] byteArrayToShort <br>
	 * [描述] byte转换为int长度<br>
	 * [参数] byte数组<br>
	 * [返回] int <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:58:41 <br>
	 *********************************************************.<br>
	 */
	public int byteArrayToShort(byte[] b) throws Exception {
		/*首位数字*256+二位数字*/
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] byte2HexStr <br>
	 * [描述] bytes转换成十六进制字符串<br>
	 * [参数] byte数组<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:58:56 <br>
	 *********************************************************.<br>
	 */
	public String byte2HexStr(byte[] b) throws Exception {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			hs = appendField(hs,
					(stmp.length() == 1) ? appendField("0", stmp)
							: stmp);
		}
		return hs.toUpperCase();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] hexStr2Bytes <br>
	 * [描述] 十六进制字符串转换成bytes<br>
	 * [参数] 16进制字符串<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:59:11 <br>
	 *********************************************************.<br>
	 */
	public byte[] hexStr2Bytes(String src) throws Exception {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	private byte uniteBytes(String src0, String src1) throws Exception {
		byte b0 = Byte.decode(appendField("0x", src0)).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode(appendField("0x", src1)).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getAsciiBytes <br>
	 * [描述] ascii码转换为byte数组<br>
	 * [参数] ascii<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:59:28 <br>
	 *********************************************************.<br>
	 */
	public byte[] getAsciiBytes(String input) throws Exception {
		char[] c = input.toCharArray();
		byte[] b = new byte[c.length];
		for (int i = 0; i < c.length; i++)
			b[i] = (byte) (c[i] & 0x007F);

		return b;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] strToBCDBytes <br>
	 * [描述] 将String转成BCD码 (左补零)<br>
	 * [参数] string<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:59:42 <br>
	 *********************************************************.<br>
	 */
	public byte[] strToBCDBytes(String s) throws Exception {
		s = (s.length() % 2 != 0) ? appendField("0", s) : s;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length; i += 2) {
			int high = cs[i] - 48;
			int low = cs[i + 1] - 48;
			baos.write(high << 4 | low);
		}
		return baos.toByteArray();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] strToBCDBytesRight <br>
	 * [描述] 将String转成BCD码 (右补零) <br>
	 * [参数] string<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 上午11:59:56 <br>
	 *********************************************************.<br>
	 */
	public byte[] strToBCDBytesRight(String s) throws Exception {
		s = (s.length() % 2 != 0) ? appendField(s, "0") : s;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length; i += 2) {
			int high = cs[i] - 48;
			int low = cs[i + 1] - 48;
			baos.write(high << 4 | low);
		}
		return baos.toByteArray();
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] bcdToint <br>
	 * [描述] 将BCD码转成int<br>
	 * [参数] byte<br>
	 * [返回] int <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:00:14 <br>
	 *********************************************************.<br>
	 */
	public int bcdToint(byte[] b) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return Integer.parseInt(sb.toString());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] bcdTostr <br>
	 * [描述] 将BCD码转成String<br>
	 * [参数] byte<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:00:29 <br>
	 *********************************************************.<br>
	 */
	public String bcdTostr(byte[] b) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] asciiToString <br>
	 * [描述] byte[]数组转换为ascii码<br>
	 * [参数] byte[]<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:00:44 <br>
	 *********************************************************.<br>
	 */
	public String asciiToString(byte[] value) throws Exception {
		return new String(value, "GBK");
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] asciiToByte <br>
	 * [描述] byte[]数组转换为ascii码<br>
	 * [参数] byte[]<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:00:57 <br>
	 *********************************************************.<br>
	 */
	public byte[] asciiToByte(byte[] value) throws Exception {
		return hexStr2Bytes(new String(value, "GBK"));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] initSize <br>
	 * [描述] 初始化银联报文长度补位<br>
	 * [参数] int<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:01:34 <br>
	 *********************************************************.<br>
	 */
	public String initSize(int i) throws Exception {
		String j = appendField("0000", Integer.toString(i));
		return j.substring(j.length() - 4, j.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] initSize <br>
	 * [描述] 初始化银联直连pos报文长度补位<br>
	 * [参数] String<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:01:46 <br>
	 *********************************************************.<br>
	 */
	public String initSize(String i) throws Exception {
		String j = appendField("0000", i);
		return j.substring(j.length() - 4, j.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] leftZero <br>
	 * [描述] 左补零<br>
	 * [参数] 需补零的数据 <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:02:01 <br>
	 *********************************************************.<br>
	 */
	public String leftZero(String s) throws Exception {
		s = appendField("00000000000", s);
		return s.substring(s.length() - 11, s.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] leftZero <br>
	 * [描述] 左补零<br>
	 * [参数] 需补零的数据，是否变长<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:02:15 <br>
	 *********************************************************.<br>
	 */
	public String leftZero(String s, int i) throws Exception {
		s = appendField("000", s);
		return s.substring(s.length() - i, s.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] rightZero <br>
	 * [描述] 右补零<br>
	 * [参数] 需补零的数据<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:02:29 <br>
	 *********************************************************.<br>
	 */
	public String rightZero(String s) throws Exception {
		return (s.length() % 2 != 0) ? appendField(s, "0") : s;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] rightSpace <br>
	 * [描述] 右补空格<br>
	 * [参数] 字符串<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:02:57 <br>
	 *********************************************************.<br>
	 */
	public String rightSpace(String s) {
		s = appendField(s, "                                        ");
		return s.substring(0, 40);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] checkByte <br>
	 * [描述] 校验byte是否为空字节数组<br>
	 * [参数] byte[]数组<br>
	 * [返回] boolean <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:03:11 <br>
	 *********************************************************.<br>
	 */
	public boolean checkByte(byte[] byteData) throws Exception {
		return ("0000".equals(byte2HexStr(byteData))) ? true : false;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] str2HexStr <br>
	 * [描述] 将汉字转为十六进制<br>
	 * [参数] 需转换的字符串<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:03:24 <br>
	 *********************************************************.<br>
	 */
	public String str2HexStr(String str) throws UnsupportedEncodingException {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes("GBK");
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] hexStr2Str <br>
	 * [描述] 16进制直接转换成为字符串(无需Unicode解码)<br>
	 * [参数] 16进制<br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午1:27:19 <br>
	 *********************************************************.<br>
	 */
	public String hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] stringNumberToBytes <br>
	 * [描述] 将字符串数字转换成bytes数组<br>
	 * [参数] 字符串<br>
	 * [返回] byte[] <br>
	 * [作者] UMF
	 * [时间] 2019年8月14日 下午12:03:40 <br>
	 *********************************************************.<br>
	 */
	public byte[] stringNumberToBytes(String num) {
		try {
			return shortToBytes((short) Integer.parseInt(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
