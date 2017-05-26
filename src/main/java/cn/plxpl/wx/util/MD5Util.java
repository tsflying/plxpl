package cn.plxpl.wx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String MD5(String sourceStr)
			throws UnsupportedEncodingException {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// log.info(e);
		}
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(MD5Util.MD5("20121221"));
		System.out.println(MD5Util.MD5("¼ÓÃÜ"));
	}

}
