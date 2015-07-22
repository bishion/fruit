package com.kelly.logic.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csnt.util.bean.BeanUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.ExceptionConst;
import com.kelly.base.cache.FruitCache;
import com.kelly.base.framework.BaseDao;
import com.kelly.dto.FruitDTO;
import com.kelly.logic.manager.inter.IFruitManager;
import com.kelly.pojo.fruit.Fruit;

public class FruitManager implements IFruitManager {

	private BaseDao<Fruit> fruitDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void stock(List<FruitDTO> orderDetailList)
			throws BaseAppException {
		
		
		// 参数转换
		List<Fruit> fruits = transData(orderDetailList);
		
		// 保存
		try{
			// 清空水果表
			fruitDao.updateByHql("delete Fruit");
			// 保存新水果
			fruitDao.saveOrUpdate(fruits);
			FruitCache.refresh();
		}catch(Exception e){
			LogUtil.error(logger, "水果入库", e);
			throw new BaseAppException(ExceptionConst.SAVE_FRUITS_ERROR);
		}
		
	}

	/**
	 * 获取到所有的水果条目
	 * @return
	 */
	@Override
	public List<Fruit> loadAllFruits(){
		return fruitDao.loadAll(Fruit.class);
	}
	private List<Fruit> transData(List<FruitDTO> orderDetailList)
			throws BaseAppException {
		if (ValidateUtil.isEmpty(orderDetailList)) {
			throw new BaseAppException();
		}
		List<Fruit> fruits = new ArrayList<Fruit>();
		for (FruitDTO fruitDTO : orderDetailList) {
			Fruit fruit = new Fruit();
			BeanUtil.copyProperties(fruitDTO, fruit);
			fruits.add(fruit);
		}
		return fruits;
	}

	public void setFruitDao(BaseDao<Fruit> fruitDao) {
		this.fruitDao = fruitDao;
	}
	
	

}
