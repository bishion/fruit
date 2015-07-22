package com.kelly.base;

public class ConfigConst {

	/** 静态文件可以通行 */
	public static String FILES_CAN_PASS = ".*\\.(css|js|gif|jpg|png)$";
	
	/** 某些界面可以通行 */
	public static String PAGES_CAN_PASS = ".*login.*$";
	
	/** 某些路径需要管理员权限 */
	public static String PAGES_NEED_ADMIN = ".*/admin\\.*";
	
	/** 管理员登陆名 */
	public static String ADMIN_LOGINNAME = "admin";
	
	/** 没有通过过滤器，直接跳转到登陆页面*/
	public static String LOGIN_PAGE = "/login.html";
	
	/** 拦截器开关，如果为1则启用登陆拦截器，如果为0则不进行拦截 */
	public static String LOGIN_CHECK_SWITCH = "0";
}
