package com.kelly.logic.manager.inter;

import java.util.Date;
import java.util.List;

import com.csnt.util.exception.BaseAppException;
import com.kelly.dto.OrderDTO;
import com.kelly.dto.OrderDetail;
import com.kelly.dto.UserDTO;

public interface IOrderManager {

	/**
	 * 水果订单入库
	 * @param detailList
	 * @param username
	 * @throws BaseAppException
	 */
	public void buy(List<OrderDetail> detailList, UserDTO user) throws BaseAppException;
	
	/**
	 * 将符合条件的订单记录返回
	 * 
	 */
	public List<OrderDTO> searchOrders(Date startDate, Date endDate);
	
	/*
	 * 查询当前登录用户的订单
	 */
	public List<OrderDTO> searchOrders(UserDTO user);
	
}
