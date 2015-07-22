package com.kelly.base;

public class BaseConst {

	/** true */
	public static final String YES = "1";
	/** false */
	public static final String NO = "0";
	
	/** 登录session */
	public static final String USER_INFO = "USER_INFO";
	/** 登陆前page */
	public static final String PAGE_BEFORE_LOGIN = "REQUEST_PAGE";
	
	/** 水果excel数据的长度 */
	public static final int FRUIT_EXCEL_ITEM = 3;
	
	/** 下载报表类别 */
	public static final String TYPE_RECORDS = "RECORDS";
	public static final String TYPE_ORDERS = "ORDERS";
	
	/** 下载报表文件后缀 */
	public static final String EXCEL_FILE_TYPE = ".xls";
	/** 下载record 报表名 */
	public static final String RECOED_EXCEL = "_Orders.xls";
	/** 下载订单报表名 */
	public static final String ORDER_EXCEL = "_Records.xls";
	/** 下载报表时将包含最后一天的数据 */
	public static final String INCLUDE_TODAY = " 23:59:59.999";
}
