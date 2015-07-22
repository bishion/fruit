package com.kelly.view;

import java.util.List;

import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.OrderDTO;
import com.kelly.dto.ResultDTO;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IOrderManager;

public class ShowOrderAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IOrderManager orderManager = (IOrderManager) BeanService.getBean("IOrderManager");
	
	public void showOrders(){
		ResultDTO result = new ResultDTO();
		UserDTO user = (UserDTO) this.getSession().getAttribute(BaseConst.USER_INFO);
		
		List<OrderDTO> orderList = orderManager.searchOrders(user);
		try{
			result.setResult(BaseConst.YES);
			result.setMessage(JsonMapper.toNonNullJson(orderList));
		}catch(Exception e){
			LogUtil.error(logger, "获取当前用户订单模块", e);
			
		}
		this.writeResponse(result);
	}
}
