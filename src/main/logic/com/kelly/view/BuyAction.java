package com.kelly.view;

import java.util.ArrayList;
import java.util.List;

import com.csnt.util.exception.BaseAppException;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.OrderDetail;
import com.kelly.dto.ResultDTO;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IFruitManager;
import com.kelly.logic.manager.inter.IOrderManager;
import com.kelly.pojo.fruit.Fruit;

/**
 * 购买Action
 * 
 */

public class BuyAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IOrderManager orderManager = (IOrderManager) BeanService
			.getBean("IOrderManager");
	private IFruitManager fruitManager = (IFruitManager) BeanService.getBean("IFruitManager");

	public void buy() {
		ResultDTO result = new ResultDTO();

		// 转换数据
		JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
		List<OrderDetail> detailList = jsonMapper.fromJson(postData, jsonMapper
				.constructParametricType(ArrayList.class, OrderDetail.class));

		// 获取登陆信息
		UserDTO userDTO = (UserDTO) this.getSession().getAttribute(BaseConst.USER_INFO);
		// 订单入库
		try {
			orderManager.buy(detailList, userDTO);

			result.setResult(BaseConst.YES);
		} catch (BaseAppException e) {
			LogUtil.error(logger, "购买水果--action", e.getExceptionMsg());
			result.setMessage(e.getExceptionMsg());
		} catch (Exception e) {
			LogUtil.error(logger, "购买--action", e);
			result.setMessage(e.getMessage());
		}

		this.writeResponse(JsonMapper.toNonNullJson(result));
	}

	/**
	 * 初始化水果列表
	 */
	public void init() {
		// 订单入库
		try {
			List<Fruit> fruits = fruitManager.loadAllFruits();

			this.writeResponse(JsonMapper.toNonNullJson(fruits));
		} catch (Exception e) {
			LogUtil.error(logger, "加载水果列表--action", e);
		}

	}

}
