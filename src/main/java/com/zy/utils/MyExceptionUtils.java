package com.zy.utils;

/**
 * 记录关于异常相关的方法  
 * @author :zy  
 * @创建日期: 2020年6月9日 下午8:48:46  
 */
public class MyExceptionUtils {
	/**
	 * 获取异常堆栈
	 * @param exception
	 * @return
	 */
	public static String getStackTrace(Throwable exception) {
		StringBuilder sb = new StringBuilder();
		sb.append(exception.getMessage()).append(":");
		StackTraceElement[] stacks = exception.getStackTrace();
		if (stacks != null) {
			sb.append(stacks.toString()).append(";");
		}
		return sb.toString();
	}
}
