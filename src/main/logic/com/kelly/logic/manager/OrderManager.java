package com.kelly.logic.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.csnt.util.exception.BaseAppException;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.ExceptionConst;
import com.kelly.base.cache.FruitCache;
import com.kelly.base.framework.BaseDao;
import com.kelly.dto.OrderDTO;
import com.kelly.dto.OrderDetail;
import com.kelly.dto.RecordDTO;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IOrderManager;
import com.kelly.pojo.fruit.Fruit;
import com.kelly.pojo.fruit.OrderInfo;
import com.kelly.pojo.fruit.Record;

/**
 * @author GuoFB
 * 订单表操作
 */
public class OrderManager implements IOrderManager {

	private BaseDao<OrderInfo> orderDao;
	
	/**
	 * 订单入库
	 */
	@Override
	public void buy(List<OrderDetail> detailList,UserDTO user) throws BaseAppException {
		// 数据转换
		List<Record> recordList = transData(detailList,user.getName());
		
		// 计算价格
		float amount = getAmount(recordList);
		
		// 组装OrderInfo入库
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setAmount(amount);
		orderInfo.setBuyDate(new Date());
		orderInfo.setBuyer(user.getName());
		orderInfo.setBuyerId(user.getUsername());
		orderInfo.setPhone(user.getPhone());
		orderInfo.setRecords(new HashSet<Record>(recordList));
		
		// 规避record中的订单号为空的情况。级联保存时，从表中无法自动关联主表的主键。
		for (Record record : recordList) {
			record.setOrderInfo(orderInfo);
		}
		
	orderDao.save(orderInfo);
	}

	
	/**
	 * 计算一个订单的总金额
	 */
	private float getAmount(List<Record> recordList) throws BaseAppException {
		// 数据校验
		if(ValidateUtil.isEmpty(recordList)){
			throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
		}

		float amount = 0;
		for (Record record : recordList) {
			amount = amount + record.getSubtotal();
		}
		return amount;
	}

	private List<Record> transData(List<OrderDetail> detailList,String name) throws BaseAppException {
		// 数据校验
		if(ValidateUtil.isEmpty(detailList)){
			throw new BaseAppException(ExceptionConst.FRUIT_DATA_ERROR);
		}
		List<Record> recordList = new ArrayList<Record>();
		// 数据转换
		for (OrderDetail orderDetail : detailList) {
			Record record = new Record();
			
			Fruit fruit = FruitCache.getFuit(orderDetail.getFruitId());
			record.setFruitName(fruit.getName());
			record.setPrice(fruit.getPrice());
			record.setQuantity(Integer.valueOf(orderDetail.getQuantity()));
			record.setSubtotal(fruit.getPrice()*record.getQuantity());
			record.setBuyer(name);
			record.setBuyDate(new Date());
			recordList.add(record);
		}
		
		return recordList;
	}

	public void setOrderDao(BaseDao<OrderInfo> orderDao) {
		this.orderDao = orderDao;
	}


	/**
	 * 按照起始时间搜索大订单信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDTO> searchOrders(Date startDate, Date endDate){
		String hql = "from OrderInfo where buyDate>? and buyDate <=? order by buyDate desc";
		List<OrderInfo> orderList = (List<OrderInfo>) orderDao.find(hql, startDate,endDate);
		
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		
		for (OrderInfo orderInfo : orderList) {
			OrderDTO orderDTO = new OrderDTO(orderInfo);
			orderDTOList.add(orderDTO);

		/*	
			注释原因，现在搜索Order不需要将record一起查出来，将来可能会需要。
			List<RecordDTO> recordDTOList = new ArrayList<RecordDTO>();
			
			List<Record> recordlList = new ArrayList<Record>(orderInfo.getRecords());
			for (Record record : recordlList) {
				RecordDTO recordDTO = new RecordDTO(record);
				
				recordDTOList.add(recordDTO);
			}
			orderDTO.setRecordList(recordDTOList);
			*/
			
		}
		return orderDTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDTO> searchOrders(UserDTO user) {
		String hql = "from OrderInfo where buyerId = ?  order by buyDate desc";
		List<OrderInfo> orderList = (List<OrderInfo>) orderDao.find(hql, user.getUsername());
		
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		
		for (OrderInfo orderInfo : orderList) {
			OrderDTO orderDTO = new OrderDTO(orderInfo);
			orderDTOList.add(orderDTO);

		/* 将查询到的当前的订单信息放到OrderDTO中 */
			List<RecordDTO> recordDTOList = new ArrayList<RecordDTO>();
			
			List<Record> recordlList = new ArrayList<Record>(orderInfo.getRecords());
			for (Record record : recordlList) {
				RecordDTO recordDTO = new RecordDTO(record);
				
				recordDTOList.add(recordDTO);
			}
			orderDTO.setRecordList(recordDTOList);
			
			
		}
		return orderDTOList;
	}

	
}
