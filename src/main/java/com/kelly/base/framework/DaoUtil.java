package com.kelly.base.framework;

import java.beans.PropertyDescriptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;





import com.csnt.util.validate.ValidateUtil;

public class DaoUtil { 

    protected static final Logger log = LoggerFactory.getLogger(DaoUtil.class.getName());
    /**
     * 为计算纪录数量拼装HQL，判定是否含有"distinct"
     * @param hql
     * @return
     */
    public static String getHQL4Count(String hql)
    {
        int formPos = hql.toLowerCase().indexOf("from ");
		Assert.isTrue(formPos != -1, " hql : " + hql + " must has a keyword 'from'");
		
        int distPos = hql.toLowerCase().indexOf(" distinct");
        if (distPos < 0)
            return hql = " select count (*) " + removeSelect(removeOrders(hql));

        //含有"distinct"  
        hql = " select count(" + hql.substring(distPos, formPos).trim() + ") " + removeSelect(removeOrders(hql));
        return hql;

    }
    
    /**
     * 去除hql的select 子句，未考虑union的情况.
     * @param hql
     * @return
     */
	public static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	} 

    /**
     * 去除hql的orderby 子句
     * @param hql
     * @return
     */
	public static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	 /**
    * 拼装查询条件字符串
    * @param hql
    * @return
    */
    public static String append(StringBuffer hql,String criteria){
	    if(hql == null) hql = new StringBuffer();
	    if(ValidateUtil.isBlank(hql.toString())){
		    return hql.append(" WHERE ").append(criteria).toString();
	    }	   
	    return hql.append(" AND ").append(criteria).toString();
    } 
	
	 /**
     * 拼装查询条件字符串
     * @param hql
     * @return
     */
    public static String getWhereOrAnd(String hql){
        if (hql == null || "".equals(hql))
            return " WHERE ";
        return " AND ";
    }
	
	/**
	 * 根据对象的属性值拼装ＨＱＬ语句
	 * 
	 * @param findObject
	 * @return
	 */
	public static String getHqlFromVOProperty(Object findObject) {
		StringBuffer sql = new StringBuffer();
		sql.append(" From " + findObject.getClass().getName());

		PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(findObject.getClass());

		StringBuffer whereString = new StringBuffer();
		for (int i = 0; i < pd.length; i++) {
			PropertyDescriptor p = pd[i];

			if (p.getWriteMethod() == null || p.getReadMethod() == null) 
				continue;
			
			try {
				if (BeanUtils.getProperty(findObject, p.getName()) == null) 
					continue;
				
				if (BeanUtils.getProperty(findObject, p.getName()).equals("0") || BeanUtils.getProperty(findObject, p.getName()).equals("0.0")) 
					continue;
				
			} catch (Exception e) {
				continue;
			}

			if (whereString.length() == 0) {
				whereString.append(" WHERE ");
			} else {
				whereString.append(" AND ");
			}

			whereString.append(p.getName() + " = " + ":" + p.getName());
		}

		sql.append(whereString.toString());

		return sql.toString();
	}
}