package com.kelly.base.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csnt.util.exception.BaseAppException;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.ConfigConst;
import com.kelly.base.ExceptionConst;
import com.kelly.base.framework.BeanService;
import com.kelly.logic.manager.inter.IConfigManager;
import com.kelly.pojo.fruit.FndConfig;

public class ConfigCache {

	private static Map<String, String> configMap;

	public String getCfgValue(String key) throws BaseAppException {
		if (ValidateUtil.isEmpty(configMap)) {
			ConfigCache.refresh();
		}
		return configMap.get(key);
	}

	public static void refresh() throws BaseAppException {
		// 获取到所有config
		IConfigManager configManager = (IConfigManager) BeanService
				.getBean("IConfigManager");

		List<FndConfig> configList = configManager.loadAllConfig();
		// 放入缓存
		if (ValidateUtil.isNull(configList)) {
			configList = new ArrayList<FndConfig>();
			return;
		}

		configMap = new HashMap<String, String>();
		for (FndConfig fndConfig : configList) {
			configMap.put(fndConfig.getCfgCode(), fndConfig.getCfgValue());
		}

		// 刷新configConst;
		ConfigCache.updateConfigField();
	}

	private static void updateConfigField() throws BaseAppException {
		Field[] fields = ConfigConst.class.getFields();

		for (Field field : fields) {
			String code = field.getName();
			String value = configMap.get(code);

			// 如果数据库中没有配置这个字段，则不更新
			if (ValidateUtil.isBlank(value)) {
				continue;
			}
			Class<?> clazz = field.getType();
			try {
				if (clazz.equals(String.class)) {
					field.set(code, value);
				}
				if (clazz.equals(Long.class)) {
					field.set(code, Long.valueOf(value));
				}
				if (clazz.equals(Integer.class)) {
					field.set(code, Integer.valueOf(value));
				}

			} catch (Exception e) {
				throw new BaseAppException(
						ExceptionConst.UPDATE_CONFIG_FIELD_ERROR);
			}

		}
	}
}
