package com.csnt.util.excel;

import java.util.List;
public class ExcelData {

	private String[] headName;		// 要显示的标题
	private String[] fieldNames;	// 要获取的属性名
	private List<?> sources;	// 数据源
	
	public String[] getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	public List<?> getSources() {
		return sources;
	}
	public void setSources(List<?> sources) {
		this.sources = sources;
	}
	public String[] getHeadName() {
		return headName;
	}
	public void setHeadName(String[] headName) {
		this.headName = headName;
	}
	
	
}
