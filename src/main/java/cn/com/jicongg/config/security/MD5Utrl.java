package cn.com.jicongg.config.security;

import java.util.Random;

import org.springframework.util.DigestUtils;

/**
 * md5工具类. 2017年9月28日
 * 
 * @author jicong.
 */
public class MD5Utrl {

	public MD5Utrl() {
	}

	/**
	 * md5Encode.
	 * 
	 * @param str
	 * @return String
	 */
	private static String toMD5(String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes());
	}

	/**
	 * MD5加密,二次加密.
	 * 
	 * @param str
	 *            被加密字符串.
	 * @return 加密值
	 */
	public synchronized static String md5Encode(String str) {
		String pre = MD5Utrl.toMD5(str);
		String end = null;
		for (int i = pre.length() - 1; i >= 0; i--) {
			char ch = pre.charAt(i);
			if (Character.isDigit(ch)) {
				end = MD5Utrl.toMD5(pre.substring(0, Character.getNumericValue(ch) + 5));
				break;
			}
		}
		if (end == null) {// 这句java这辈子估计也不会跑.
			end = MD5Utrl.toMD5(pre.substring(0, new Random().nextInt(10) + 5));
		}
		return pre + end;

	}

}
