package com.kelly.base.framework;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanService implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		BeanService.applicationContext =applicationContext;
	}
	 public static ApplicationContext getApplicationContext() {  
	        return applicationContext;  
	    }  
	    /** 
	     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 
	     */  
		public static <T> T getBean(Class<T> clazz) {  
	        return (T) applicationContext.getBean(clazz);  
	    }
	    
	    public static Object getBean(String name){
	    	return applicationContext.getBean(name);
	    }
}
