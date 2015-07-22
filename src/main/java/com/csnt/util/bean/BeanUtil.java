package com.csnt.util.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.csnt.util.exception.BaseAppException;

import net.sf.cglib.beans.BeanCopier;

/**
 * Bean的工具类。包括Bean转换，获取bean的一些信息等。
 * 未来可以填充Bean其他相关功能。
 * @author GuoFB
 *
 */
public class BeanUtil {

	public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

	/**
	 * Bean值转换。
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = null;
		if (!beanCopierMap.containsKey(beanKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(),
					false);
			beanCopierMap.put(beanKey, copier);
		} else {
			copier = beanCopierMap.get(beanKey);
		}
		copier.copy(source, target, null);
	}

	private static String generateKey(Class<?> class1, Class<?> class2) {
		return class1.toString() + class2.toString();
	}
	
	/**
	 * 根据属性名获取对象的属性值。
	 * @param fieldName
	 * @param object
	 * @return
	 * @throws BaseAppException 
	 */
	public static String getProperty(String fieldName,Object object) throws BaseAppException {
		try{
			return BeanUtils.getSimpleProperty(object, fieldName);
		}catch(Exception e){
			throw new BaseAppException(e);
		}
	}
}
