package com.kelly.dto;

public class ResultDTO {

	private String result = "0";// 结果信息，为1时为正常，为0时，报message
	private String message;		// 报错信息
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
