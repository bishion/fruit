package com.kelly.logic.manager.inter;

import java.util.List;

import com.csnt.util.exception.BaseAppException;
import com.kelly.dto.FruitDTO;
import com.kelly.pojo.fruit.Fruit;

public interface IFruitManager {

	public void stock(List<FruitDTO> fruitDTOList) throws BaseAppException;
	public List<Fruit> loadAllFruits();
}
