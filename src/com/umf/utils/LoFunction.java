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
	 * [����] changeJsonToMap <br>
	 * [����] ��Json�ַ���ת��ΪMap���� <br>
	 * [����] Json�ַ��� <br>
	 * [����] Map<String,String> <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:37:09 <br>
	 *********************************************************.<br>
	 */
	public Map<String, String> changeJsonToMap(String jsonStr){
		return JSONObject.fromObject(jsonStr);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] changeMapToJson <br>
	 * [����] ��Map����ת��ΪJson�ַ��� <br>
	 * [����] ���� <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:37:12 <br>
	 *********************************************************.<br>
	 */
	public String changeMapToJson(Map<String, String> mapData){
		return JSONObject.fromObject(mapData).toString();
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] appendField <br>
	 * [����] ƴ���ַ���<br>
	 * [����] �����в���ƴ��<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:07:54 <br>
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
	 * [����] convertMoney <br>
	 * [����] ��12λ���ַ���ת��ΪBigDecimal�ͣ�����2λС�� <br>
	 * [����] ��� <br>
	 * [����] BigDecimal <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:55:45 <br>
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
	 * [����] convertMoney1 <br>
	 * [����] ������С������ַ������ת��Ϊ12λ���<br>
	 * [����] ���<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:56:15 <br>
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
	 * [����] changeMoney <br>
	 * [����] ������С����Ľ�����12λ<br>
	 * [����] ���<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:56:32 <br>
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
	 * [����] assemble <br>
	 * [����] �����byte������ϳ�һ��byte����<br>
	 * [����] byte����<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:56:46 <br>
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
	 * [����] convertChar <br>
	 * [����] ���ַ���ת��Ϊ�ֽ���<br>
	 * [����] �ַ���<br>
	 * [����] int <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:57:04 <br>
	 *********************************************************.<br>
	 */
	public int convertChar(int s) {
		return (s % 2 == 0) ? s / 2 : (s + 1) / 2;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] shortToBytes <br>
	 * [����] ��һ��short���͵�����ת��Ϊ�ֽڲ����뵽ָ�����ֽ�����ָ����λ�á���λ��ǰ<br>
	 * [����] short<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:57:14 <br>
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
	 * [����] getBinaryFromByte <br>
	 * [����] ��һ��16�ֽ�����ת��128����������<br>
	 * [����] byte����<br>
	 * [����] boolean[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:57:36 <br>
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
	 * [����] getByteFromBinary <br>
	 * [����] ��һ��128����������ת��16�ֽ�����<br>
	 * [����] ����������<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:58:22 <br>
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
	 * [����] byteArrayToShort <br>
	 * [����] byteת��Ϊint����<br>
	 * [����] byte����<br>
	 * [����] int <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:58:41 <br>
	 *********************************************************.<br>
	 */
	public int byteArrayToShort(byte[] b) throws Exception {
		/*��λ����*256+��λ����*/
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] byte2HexStr <br>
	 * [����] bytesת����ʮ�������ַ���<br>
	 * [����] byte����<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:58:56 <br>
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
	 * [����] hexStr2Bytes <br>
	 * [����] ʮ�������ַ���ת����bytes<br>
	 * [����] 16�����ַ���<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:59:11 <br>
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
	 * [����] getAsciiBytes <br>
	 * [����] ascii��ת��Ϊbyte����<br>
	 * [����] ascii<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:59:28 <br>
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
	 * [����] strToBCDBytes <br>
	 * [����] ��Stringת��BCD�� (����)<br>
	 * [����] string<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:59:42 <br>
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
	 * [����] strToBCDBytesRight <br>
	 * [����] ��Stringת��BCD�� (�Ҳ���) <br>
	 * [����] string<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����11:59:56 <br>
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
	 * [����] bcdToint <br>
	 * [����] ��BCD��ת��int<br>
	 * [����] byte<br>
	 * [����] int <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:00:14 <br>
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
	 * [����] bcdTostr <br>
	 * [����] ��BCD��ת��String<br>
	 * [����] byte<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:00:29 <br>
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
	 * [����] asciiToString <br>
	 * [����] byte[]����ת��Ϊascii��<br>
	 * [����] byte[]<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:00:44 <br>
	 *********************************************************.<br>
	 */
	public String asciiToString(byte[] value) throws Exception {
		return new String(value, "GBK");
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] asciiToByte <br>
	 * [����] byte[]����ת��Ϊascii��<br>
	 * [����] byte[]<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:00:57 <br>
	 *********************************************************.<br>
	 */
	public byte[] asciiToByte(byte[] value) throws Exception {
		return hexStr2Bytes(new String(value, "GBK"));
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] initSize <br>
	 * [����] ��ʼ���������ĳ��Ȳ�λ<br>
	 * [����] int<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:01:34 <br>
	 *********************************************************.<br>
	 */
	public String initSize(int i) throws Exception {
		String j = appendField("0000", Integer.toString(i));
		return j.substring(j.length() - 4, j.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] initSize <br>
	 * [����] ��ʼ������ֱ��pos���ĳ��Ȳ�λ<br>
	 * [����] String<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:01:46 <br>
	 *********************************************************.<br>
	 */
	public String initSize(String i) throws Exception {
		String j = appendField("0000", i);
		return j.substring(j.length() - 4, j.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] leftZero <br>
	 * [����] ����<br>
	 * [����] �貹������� <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:02:01 <br>
	 *********************************************************.<br>
	 */
	public String leftZero(String s) throws Exception {
		s = appendField("00000000000", s);
		return s.substring(s.length() - 11, s.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] leftZero <br>
	 * [����] ����<br>
	 * [����] �貹������ݣ��Ƿ�䳤<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:02:15 <br>
	 *********************************************************.<br>
	 */
	public String leftZero(String s, int i) throws Exception {
		s = appendField("000", s);
		return s.substring(s.length() - i, s.length());
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] rightZero <br>
	 * [����] �Ҳ���<br>
	 * [����] �貹�������<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:02:29 <br>
	 *********************************************************.<br>
	 */
	public String rightZero(String s) throws Exception {
		return (s.length() % 2 != 0) ? appendField(s, "0") : s;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] rightSpace <br>
	 * [����] �Ҳ��ո�<br>
	 * [����] �ַ���<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:02:57 <br>
	 *********************************************************.<br>
	 */
	public String rightSpace(String s) {
		s = appendField(s, "                                        ");
		return s.substring(0, 40);
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] checkByte <br>
	 * [����] У��byte�Ƿ�Ϊ���ֽ�����<br>
	 * [����] byte[]����<br>
	 * [����] boolean <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:03:11 <br>
	 *********************************************************.<br>
	 */
	public boolean checkByte(byte[] byteData) throws Exception {
		return ("0000".equals(byte2HexStr(byteData))) ? true : false;
	}

	/**
	 * 
	 *********************************************************.<br>
	 * [����] str2HexStr <br>
	 * [����] ������תΪʮ������<br>
	 * [����] ��ת�����ַ���<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:03:24 <br>
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
	 * [����] hexStr2Str <br>
	 * [����] 16����ֱ��ת����Ϊ�ַ���(����Unicode����)<br>
	 * [����] 16����<br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����1:27:19 <br>
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
	 * [����] stringNumberToBytes <br>
	 * [����] ���ַ�������ת����bytes����<br>
	 * [����] �ַ���<br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��14�� ����12:03:40 <br>
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
