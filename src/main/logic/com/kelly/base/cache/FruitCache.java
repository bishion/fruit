package com.kelly.base.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.framework.BeanService;
import com.kelly.logic.manager.inter.IFruitManager;
import com.kelly.pojo.fruit.Fruit;

public class FruitCache {

	private static Map<String, Fruit> fruitsMap;
	public static Fruit getFuit(String key){
		if(ValidateUtil.isEmpty(fruitsMap)){
			FruitCache.refresh();
		}
		return fruitsMap.get(key);
	}
	
	public static void refresh(){
		// 获取到所有的水果
		IFruitManager fruitManager = (IFruitManager) BeanService.getBean("IFruitManager");
		
		List<Fruit> fruitList = fruitManager.loadAllFruits();
		
		// 放入缓存
		if(ValidateUtil.isEmpty(fruitList)){
			Logger logger = LoggerFactory.getLogger(FruitCache.class);
			LogUtil.error(logger, "水果缓存", "从数据库获取水果列表为空！");
			return;
		}
		
		// 将map中内容清空，重新加载数据库中的内容
		fruitsMap = new HashMap<String, Fruit>();
		
		for (Fruit fruit : fruitList) {
			fruitsMap.put(fruit.getId().toString(), fruit);
		}
	}
}
