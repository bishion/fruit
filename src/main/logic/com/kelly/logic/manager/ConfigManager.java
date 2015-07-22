package com.kelly.logic.manager;

import java.util.List;

import com.kelly.base.framework.BaseDao;
import com.kelly.logic.manager.inter.IConfigManager;
import com.kelly.pojo.fruit.FndConfig;

public class ConfigManager implements IConfigManager {

	private BaseDao<FndConfig> configDao;
	@Override
	public List<FndConfig> loadAllConfig() {
		return configDao.loadAll(FndConfig.class);
	}
	public void setConfigDao(BaseDao<FndConfig> configDao) {
		this.configDao = configDao;
	}

	
}
