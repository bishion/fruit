package com.kelly.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.csnt.util.date.DateUtil;
import com.csnt.util.excel.ExcelUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.ExceptionConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.FruitDTO;
import com.kelly.dto.ResultDTO;
import com.kelly.logic.manager.inter.IFruitManager;

/**
 * 进货类，实际上是由用户上传水果信息
 *
 */
public class StockAction extends BaseAction {

	private File fruitExcel;
	private static final long serialVersionUID = 1L;
	private IFruitManager fruitManager = (IFruitManager) BeanService.getBean("IFruitManager");
	public void stockFruit(){
		ResultDTO result = new ResultDTO();
		// 复制文件到目标文件夹
		String realpath = ServletActionContext.getServletContext().getRealPath("/files/fruits");
		
		File saveFile = new File(new File(realpath), DateUtil.toString(new Date(), DateUtil.yyyy_MM_dd)+".xls");
		try {
			FileUtils.copyFile(fruitExcel, saveFile);
		} catch (IOException e) {
			LogUtil.error(logger, "上传水果", e);
			result.setMessage(JsonMapper.toNonNullJson(e));
			this.writeResponse(JsonMapper.toNonNullJson(result));
		}
		
		// 读取excel，保存到数据库
		try {
			Map<String, String[][]> excelData = ExcelUtil.readExcel(fruitExcel);
			List<FruitDTO> fruits = null;
			for (String[][] fruitData : excelData.values()) {
				fruits = transData(fruitData);
				break;
			}
			
			fruitManager.stock(fruits);
			result.setResult(BaseConst.YES);
			
		} catch (BaseAppException e) {
			LogUtil.error(logger, "入库--action", e.getExceptionMsg());
			result.setMessage(e.getExceptionMsg());
		} catch (Exception e){
			LogUtil.error(logger, "入库--Action", e);
			result.setMessage(e.getMessage());
		}
		
		this.writeResponse(JsonMapper.toNonNullJson(result));
	}
	
	private List<FruitDTO> transData(String[][] fruitDataList) throws BaseAppException {
		List<FruitDTO> fruitList = new ArrayList<FruitDTO>();
		for (String[] fruitData : fruitDataList) {
			if(fruitData.length<BaseConst.FRUIT_EXCEL_ITEM){
				LogUtil.error(logger, "入库-action", "水果excel缺少数据");
				throw new BaseAppException(ExceptionConst.FRUIT_DATA_ERROR);
			}
			if(ValidateUtil.hasOneNullOrEmpty(fruitData[0],fruitData[1])){
				continue;
			}
			FruitDTO fruit = new FruitDTO();
			fruit.setName(fruitData[0]);
			fruit.setPrice(Float.valueOf(fruitData[1]));
			fruit.setRemark(fruitData[2]);
			
			fruitList.add(fruit);
		} 
		System.out.println(JsonMapper.toNormalJson(fruitList));
		return fruitList;
	}

	public void setFruitExcel(File fruitExcel) {
		this.fruitExcel = fruitExcel;
	}

}
