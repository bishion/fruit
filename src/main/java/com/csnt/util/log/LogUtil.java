package com.csnt.util.log;

import org.slf4j.Logger;

import com.csnt.util.validate.ValidateUtil;

public class LogUtil {

	/**
	 * 模块功能：单个索引的日志
	 * @param logger
	 * @param moduleName 模块名
	 * @param index 索引名
	 * @param value 索引值
	 * @param message
	 */
	public static void info(Logger logger,String moduleName,String index,String value,String message){

		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[模块名称：").append(moduleName).append("],");
		stringBuilder.append("[").append(index).append(":").append(value).append("],");
		stringBuilder.append("[日志信息：").append(message).append("]");
		
		logger.info(stringBuilder.toString());
		
	}
	/**
	 * 模块功能：单个索引的日志，记录消耗时间
	 * @param logger
	 * @param moduleName 模块名
	 * @param index 索引名
	 * @param value 索引值
	 * @param spendTime
	 * @param message
	 */
	public static void info(Logger logger,String moduleName,String index,String value,long spendTime,String message){

		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[模块名称：").append(moduleName).append("],");
		stringBuilder.append("[").append(index).append(":").append(value).append("],");
		stringBuilder.append("[消耗时间：").append(spendTime).append("],");
		stringBuilder.append("[日志信息：").append(message).append("]");
		
		logger.info(stringBuilder.toString());
		
	}
	public static void info(Logger logger,String moduleName,String index1,String value1,String index2,String value2,String message){
		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[模块名称：").append(moduleName).append("],");
		stringBuilder.append("[").append(index1).append(":").append(value1).append("],");
		stringBuilder.append("[").append(index2).append(":").append(value2).append("],");
		stringBuilder.append("[日志信息：").append(message).append("]");
		
		logger.info(stringBuilder.toString());
	}
	
	/**
	 * 模块功能：无索引的异常信息，可以直接打印异常。
	 * @param logger
	 * @param moduleName
	 * @param exception
	 */
	public static void error(Logger logger,String moduleName, Exception exception){
		
		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[模块名称：").append(moduleName).append("],");
		stringBuilder.append("[堆栈打印信息：]");
		
		logger.error(stringBuilder.toString(), exception);
	}
	
	/**
	 * 模块功能：无索引的异常信息，可以直接打印异常。
	 * @param logger
	 * @param moduleName
	 * @param errorMessage
	 */
	public static void error(Logger logger,String errorCode, String errorMessage){
		
		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[异常代码：").append(errorCode).append("],");
		stringBuilder.append("[报错信息：").append(errorMessage).append("]");
		
		logger.error(stringBuilder.toString());
	}
	
	public static void error(Logger logger,String moduleName,String index1,String value1,String index2,String value2,String message){
		if(ValidateUtil.isNull(logger)){
			throw new RuntimeException("logger is null");
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[模块名称：").append(moduleName).append("],");
		stringBuilder.append("[").append(index1).append(":").append(value1).append("],");
		stringBuilder.append("[").append(index2).append(":").append(value2).append("],");
		stringBuilder.append("[日志信息：").append(message).append("]");
		
		logger.info(stringBuilder.toString());
	}
}
