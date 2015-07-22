package com.csnt.util.validate;

import java.util.Collection;
import java.util.Map;

public class ValidateUtil {
	
	public static boolean isNull(Object obj){
		if(obj == null){
			return true;
		}
		return false;
	}

	public static boolean isBlank(String string){
		if(string == null || "".equals(string) || string.trim().equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean hasOneNullOrEmpty(String ...strings){
		if(ValidateUtil.isEmpty(strings)){
			return true;
		}
		for (String string : strings) {
			if(ValidateUtil.isBlank(string)){
				return true;
			}
		}
		return false;
		
	}
	
	public static boolean isEmpty(Collection<?> collection){
		if(collection == null || collection.size()== 0){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Object[] objects){
		if(objects == null || objects.length== 0){
			return true;
		}
		return false;
	}
	public static boolean isEmpty(Map<?, ?> map){
		return map==null || map.isEmpty();
	}
}
