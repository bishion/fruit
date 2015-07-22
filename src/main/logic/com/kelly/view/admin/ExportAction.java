package com.kelly.view.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csnt.util.date.DateUtil;
import com.csnt.util.excel.ExcelData;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.OrderDTO;
import com.kelly.dto.RecordDTO;
import com.kelly.dto.ResultDTO;
import com.kelly.logic.manager.inter.IOrderManager;
import com.kelly.logic.manager.inter.IRecordManager;

/**
 * 导出功能，导出每个人应付款项，还有购买水果信息。
 * 生成excel表
 * 注意要验证权限。
 *
 */
public class ExportAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IOrderManager orderManager = (IOrderManager) BeanService.getBean("IOrderManager");
	private IRecordManager recordManager = (IRecordManager) BeanService.getBean("IRecordManager");
	
	public void download(){
		ResultDTO result = new ResultDTO();
		// 参数转换
		@SuppressWarnings("unchecked")
		List<String> timeList = JsonMapper.fromJson(postData, ArrayList.class);
		if(ValidateUtil.isEmpty(timeList) || timeList.size()<3){
			
		}
		Date startDate = DateUtil.toDate(timeList.get(0), DateUtil.yyyy_MM_dd);
		
		Date endDate = DateUtil.toDate(timeList.get(1)+BaseConst.INCLUDE_TODAY, DateUtil.yyyy_MM_dd_HH_mm_ss_S);
		
		String flag = timeList.get(2);
		
		
		try{
			if(BaseConst.TYPE_ORDERS.equals(flag)){
				List<OrderDTO> orderList = orderManager.searchOrders(startDate, endDate);
				doExcelExport(this.getResponse(),timeList.get(0)+"_"+timeList.get(1)+BaseConst.ORDER_EXCEL , prepareOrder(orderList));
			}else/*(BaseConst.TYPE_ORDERS.equals(flag))*/{
				List<RecordDTO> recordList = recordManager.searchRecords(startDate, endDate);
				doExcelExport(this.getResponse(),timeList.get(0)+"_"+timeList.get(1)+BaseConst.RECOED_EXCEL , prepareRecord(recordList));
			}
		}catch(Exception e){
			result.setMessage(JsonMapper.toNonNullJson(e));
		}
		
	}
	
	private Map<String, ExcelData> prepareRecord(List<RecordDTO> recordList) {
		if(ValidateUtil.isEmpty(recordList)){
			return null;
		}
		ExcelData excelData = new ExcelData();
		excelData.setHeadName(new String[]{"水果名称","单价","购买数量","购买人","小计","下单时间"});
		excelData.setFieldNames(new String[]{"fruitName","price","quantity","buyer","subtotal","buyDate"});
		excelData.setSources(recordList);
		
		Map<String, ExcelData> orderData = new HashMap<String, ExcelData>();
		orderData.put("水果详单表", excelData);
		return orderData;
	}

	private Map<String,ExcelData> prepareOrder(List<OrderDTO> orderList){
		if(ValidateUtil.isEmpty(orderList)){
			return null;
		}
		ExcelData excelData = new ExcelData();
		excelData.setHeadName(new String[]{"订单号","购买人","总金额","联系方式","购买时间"});
		excelData.setFieldNames(new String[]{"orderNo","buyer","amount","phone","buyDate"});
		excelData.setSources(orderList);
		
		Map<String, ExcelData> orderData = new HashMap<String, ExcelData>();
		orderData.put("水果订单表", excelData);
		return orderData;
	}
	
}
