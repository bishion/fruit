package com.csnt.util.string;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

import com.csnt.util.date.DateUtil;
import com.csnt.util.validate.ValidateUtil;

public class StringUtil {

	/**
	 * Convert the Object to string if the object is null,then return null 
	 * else return his string value
	 */
	public static String toNullOrString(Object o) {
		if (o == null) return null;
		return o.toString();
	}
	/**
	 * Convert the Object to string if the object is null,then return empty string,
	 * else return his string value
	 */	
	public static String toEmptyOrString(Object o) {
		if (o == null) return "";
		return o.toString();
	}

	/**
	 * 全角字符串转化成为半角字符串
	 * @param QJstr
	 */
	public static final String convertDBC2SBC(String dbcStr){
		if(ValidateUtil.isBlank(dbcStr)) return null;
		StringBuffer outStr = new StringBuffer("");
	    String tempStr = "";
	    byte[] b = null;

	    try {
			for(int i=0;i <dbcStr.length();i++){      
				tempStr = dbcStr.substring(i,i+1);
				b = tempStr.getBytes("unicode"); 
   
				if (b[3] == -1)  {
					b[2] = (byte)(b[2]+32);
					b[3] = 0;	         
					outStr.append(new String(b,"unicode")); 
				}else 
					outStr.append(tempStr);
			} 
			return outStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}   

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Split String by "//"
	 * 
	 * @param splitStr  orginally String
	 * @param delim     "//"
	 * @return String[] string array withou "//"
	 */
	public static String[] splitString(String splitStr, String delim) {
		StringTokenizer toker = new StringTokenizer(splitStr, delim);
		int count = toker.countTokens();
		String result[] = new String[count];
		for (int i = 0; i < count; i++) {
			try {
				result[i] = toker.nextToken();
				continue;
			} catch (NoSuchElementException ex) {
				result = (String[]) null;
			}
			break;
		} 
		return result;
	}

	/**
	 * Convert the "//" to "/" for a path string 
	 * @param path  orginally string with "\\"
	 * @return string with "/"
	 */
	public static String changePathSplit(String path) {
		String[] splitPath = StringUtil.splitString(path, "\\");
		StringBuffer newPath = new StringBuffer();
		for (int i = 0; i < splitPath.length; i++) {
			newPath.append(splitPath[i]);
			newPath.append("/");
		}
		return newPath.toString();
	}
	 

	//////////////////////////////////////////////////////
	//           Method for SQL  String                 //
	//////////////////////////////////////////////////////
	public static void getWhereOrAnd(StringBuffer buffer) {
		if (buffer.length() == 0) {
			buffer.append(" WHERE ");
		} else {
			buffer.append(" AND ");
		}
	}

	/**
	 * conver the ' to '' for query string 
	 * @param str 
	 */
	public static String dealSingleQuotes(String str) {

		if (str == null || "".equals(str))
			return null;
		StringBuffer buffer = new StringBuffer();
		char c;
		for (int i = 0; i < str.length(); i = i + 1) {
			c = str.charAt(i); //is letter
			if (c == 34 || c == 39) {
				buffer.append(c);
				buffer.append(c);
			} else
				buffer.append(c);
		}

		return buffer.toString();
	}
	

    /**
     * 按照分隔符分割字符串，然后转化成LIST
     * @param theString
     * @param sepChar
     * @return
     */
    public static List<String> getListBySepStr(String theString,String delim){
		try {
			if(ValidateUtil.isBlank(theString)) return null;    	
			return Arrays.asList(theString.split(delim));    	
		} catch (Exception e) {
			return null;
		}	
    }

	
	public static String getCode18(){ 
		return DateUtil.toString(new Date(), DateUtil.yyyyMMddHHmmssS) + (new Random()).nextInt(9);
	}
}

