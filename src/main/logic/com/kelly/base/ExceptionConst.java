package com.kelly.base;

public enum ExceptionConst {
	SYSTEM_ERROR("SYS0001","系统异常"),
	SYSTEM_PARAM_ERROR("SYS0002","参数异常"),
	UPDATE_CONFIG_FIELD_ERROR("SYS0003","更新配置字段异常"),
	REGISTER_ERROR("BIZ0001","注册失败"),
	SAVE_FRUITS_ERROR("BIZ0002","入库失败"),
	FRUIT_DATA_ERROR("PAM0001","模板数据异常"),
	ORDER_DATA_ERROR("PAM0002","购买数据异常"),
	LOGIN_FAILED("LGN0001","用户名或密码错误");
	
	
	private String code;
	
	private String message;
	
	private ExceptionConst(String code,String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
