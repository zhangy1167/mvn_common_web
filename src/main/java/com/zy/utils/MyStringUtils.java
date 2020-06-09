package com.zy.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 编写常用的String类方法  * @author :zy  * @创建日期: 2020年6月8日 下午8:00:51  
 */
public class MyStringUtils {
	private static Logger log = LoggerFactory.getLogger(MyStringUtils.class);
	/**
	 * 按指定的字符集进行字符串剪切
	 * @param message 源字符串
	 * @param charset 字符集
	 * @param begin开始位置
	 * @param count 字符数
	 * @return 返回指定位数的字符串
	 */
	public static String subString(String message, Charset charset, int begin, int count) {
		byte[] aimBytes = message.getBytes(charset);
		byte[] resultBytes = new byte[count];
		System.arraycopy(aimBytes, begin-1, resultBytes, 0, count);
		return new String(resultBytes, charset);
	}
	public static String convert(String input,String fromEncoding,String toEncoding) {
		String result = "";
		if(StringUtils.isBlank(input)||StringUtils.isBlank(fromEncoding)||StringUtils.isBlank(toEncoding)) {
			return input;
		}
		try {
			byte[] byteArray = input.getBytes(fromEncoding);
			result = new String(byteArray,toEncoding);
			
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			result = input;
		}
		return result;
	}
	 
	public static void main(String[] args) {
		String str = "123456";
		System.out.println(subString(str,Charset.forName("gbk"),1,3));
	}
}
