package com.csnt.util.excel;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.csnt.util.bean.BeanUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.exception.BaseException;
import com.csnt.util.validate.ValidateUtil;

/**
 * excel的工具类，使用jxl.jar,据说在内存方面比poi小一些，不过没有poi强大。
 * 二者对比介绍：http://blog.csdn.net/shutingwang/article/details/7278871
 * @author GuoFB
 * 
 */
public class ExcelUtil {

	/**
	 * 创建文档主类，负责基础校验，分发功能。
	 * @return
	 * @throws BaseAppException 
	 */
	public static Workbook createExcel(Map<String, ExcelData> excelData, OutputStream outputStream) throws BaseAppException{
		if(ValidateUtil.isEmpty(excelData) || ValidateUtil.isNull(outputStream))
			throw new BaseException("data is illegal !");
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(outputStream);
			
			// 依次创建sheet，并填充数据。
			int i = 0;
			for (String title : excelData.keySet()) {
				createSheet(i++,title,excelData.get(title),workbook);
			}
			
		workbook.write();
		workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseAppException(e);
		}
		return null;
	}
	/**
	 * 创建sheet
	 * @param index 当前sheet的序号
	 * @param title 当前sheet的标题
	 * @param excelData 要填充的数据列表
	 * @param workbook excel工作表对象
	 * @throws Exception
	 */
	private static void createSheet(int index,String title,ExcelData excelData,WritableWorkbook workbook) throws Exception{
		// 校验数据
		if(ValidateUtil.isNull(excelData) || ValidateUtil.isEmpty(excelData.getFieldNames())|| 
		ValidateUtil.isEmpty(excelData.getSources())){
			
			throw new BaseException("data is illegal !");
		}
		// 创建sheet
		WritableSheet sheet = workbook.createSheet("Sheet"+index, index);
		
		// 填充数据
		// 填充第一行
		sheet.addCell(new Label(0, 0, title));
		
		// 填充标题
		for (int i = 0; i < excelData.getHeadName().length; i++) {
			sheet.addCell(new Label(i, 1, excelData.getHeadName()[i]));
		}
		// 填充数据
		int i = 2;
		for(Object object : excelData.getSources()){
			int columns = excelData.getFieldNames().length;
			for(int j = 0; j<columns;j++){
				sheet.addCell(new Label(j, i, BeanUtil.getProperty(excelData.getFieldNames()[j], object)));
			}
			i++;
		}
		
	}
	
	/**
	 * 读取excel文件，将数据返回为一个map<key,result>的数据结构；
	 * 其中key为sheet的名字，result为根据单元格排列的二维数组
	 * @param file
	 * @return
	 * @throws BaseAppException
	 */
	public static Map<String,String[][]> readExcel(File file) throws BaseAppException{
		if(ValidateUtil.isNull(file)){
			throw new BaseException("data is illegal !");
		}
		Map<String, String[][]> data = new HashMap<String, String[][]>();
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			
			
			for (Sheet sheet:workbook.getSheets()) {
				String sheetName = sheet.getName();
				
				int rows = sheet.getRows();
				int columns = sheet.getColumns();
				
				String[][] cells = new String[rows][columns];
				for (int i = 0; i < sheet.getRows(); i++) {
					for (int j = 0; j < sheet.getColumns(); j++) {
						cells[i][j] = sheet.getCell(j, i).getContents();
					}
				}
				
				data.put(sheetName, cells);
			}
			return data;
		} catch (Exception e) {
			throw new BaseAppException(e);
		}
	}
	
}
