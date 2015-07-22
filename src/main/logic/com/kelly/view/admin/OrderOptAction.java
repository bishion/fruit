package com.kelly.view.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.csnt.util.date.DateUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.ExceptionConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.OrderDTO;
import com.kelly.dto.RecordDTO;
import com.kelly.dto.ResultDTO;
import com.kelly.logic.manager.inter.IOrderManager;
import com.kelly.logic.manager.inter.IRecordManager;

public class OrderOptAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private IOrderManager orderManager = (IOrderManager) BeanService.getBean("IOrderManager");
	private IRecordManager recordManager = (IRecordManager) BeanService.getBean("IRecordManager");
	
	public void search(){
		ResultDTO result = new ResultDTO();
		
		try{
			if(ValidateUtil.isBlank(postData)){
				throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
			}
			// 转换数据
			@SuppressWarnings("unchecked")
			List<String> conditionList = JsonMapper.fromJson(postData, ArrayList.class);
			if(ValidateUtil.isEmpty(conditionList) || conditionList.size()<3){
				throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
			}
			
			// 转换数据
			Date startDate = DateUtil.toDate(conditionList.get(0), DateUtil.yyyy_MM_dd);
			Date endDate = DateUtil.toDate(conditionList.get(1)+BaseConst.INCLUDE_TODAY, DateUtil.yyyy_MM_dd_HH_mm_ss_S);
			String flag = conditionList.get(2);
			
			// 如果要查看订单表
			if(BaseConst.TYPE_ORDERS.equals(flag)){
				
				List<OrderDTO> orderList = orderManager.searchOrders(startDate, endDate);
				result.setMessage(JsonMapper.toNormalJson(orderList));
			} else{
				List<RecordDTO> recordList = recordManager.searchRecords(startDate, endDate);
				result.setMessage(JsonMapper.toNormalJson(recordList));
			}
			
			result.setResult(BaseConst.YES);
			
		}catch(BaseAppException e){
			result.setMessage(e.getExceptionMsg());
		}catch(Exception e){
			result.setMessage(e.getMessage());
			LogUtil.error(logger, "订单查看-action", e);
		}
		
		this.writeResponse(result);
		
	}
	

}
