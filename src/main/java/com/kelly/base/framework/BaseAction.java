package com.kelly.base.framework;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csnt.util.excel.ExcelData;
import com.csnt.util.excel.ExcelUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected String postData;
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// 获取session
	protected HttpSession getSession() {
		return this.getRequest().getSession();
	}

	// 获取request
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	/**
     * ajax调用时，将结果信息写入response
     * 
     * @param message
     */
    public void writeResponse(String message) {
    	try {
    		this.getResponse().setCharacterEncoding("UTF-8");
    		this.getResponse().getWriter().write(message);
    		this.getResponse().getWriter().flush();
    	} catch (Exception e) {
    		logger.error("写入结果信息时抛出异常，异常信息为：" + e.getMessage());
    	}
    }
    
    public void writeResponse(Object object){
    	try {
    		this.getResponse().setCharacterEncoding("UTF-8");
    		this.getResponse().getWriter().write(JsonMapper.toNonNullJson(object));
    		this.getResponse().getWriter().flush();
    	} catch (Exception e) {
    		logger.error("写入结果信息时抛出异常，异常信息为：" + e.getMessage());
    	}
    }
    /**
     * 使用struts2推荐的做法设置返回参数,
     * 注意页面s标签判断时须使用#paramName,取值时不一定需要添加#
     * @param paramName
     * @param object
     */
    public void setParam(String paramName,Object object){
    	ActionContext.getContext().put(paramName, object);
    }
    
	/**
	 *  获取客户端IP地址
	 * @return
	 */
	protected String getRequestIP() {
		String requestIP = "";

		// 1.首先考虑有反向代理的情况，如果有代理，通过“x-forwarded-for”获取真实ip地址
		requestIP = getRequest().getHeader("x-forwarded-for");

		// 2.如果squid.conf的配制文件forwarded_for项默认是off，则：X-Forwarded-For：unknown。考虑用Proxy-Client-IP或WL-Proxy-Client-IP获取
		if (ValidateUtil.isBlank(requestIP)
				|| "unknown".equalsIgnoreCase(requestIP)) {
			requestIP = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ValidateUtil.isBlank(requestIP)
				|| "unknown".equalsIgnoreCase(requestIP)) {
			requestIP = getRequest().getHeader("WL-Proxy-Client-IP");
		}

		// 3.最后考虑没有代理的情况，直接用request.getRemoteAddr()获取ip
		if (ValidateUtil.isBlank(requestIP)
				|| "unknown".equalsIgnoreCase(requestIP)) {
			requestIP = getRequest().getRemoteAddr();
		}

		// 4.如果通过多级反向代理，则可能产生多个ip，其中第一个非unknown的IP为客户端真实IP（IP按照','分割）
		if (!ValidateUtil.isBlank(requestIP) && requestIP.split(",").length > 1) {
			requestIP = (requestIP.split(","))[0];
		}

		// 5.如果是服务器本地访问，需要根据网卡获取本机真实ip
		if ("127.0.0.1".equals(requestIP)
				|| ("0:0:0:0:0:0:0:1".equals(requestIP))) {
			try {
				requestIP = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				return "";
			}
		}
		return requestIP;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}
	
	/**
	 * 利用自己编写的方法，生成并下载Excel文件
	 * 
	 * @param res
	 * @param title			Excel文件的名称
	 * @param headNames		Excel列头名称
	 * @param fieldNames	VO对象属性字段名称数组
	 * @param voList		VO对象列表
	 * @throws BaseAppException
	 */
	public void doExcelExport(HttpServletResponse res, String fileName,
			Map<String, ExcelData> excelData) throws BaseAppException {
		BufferedOutputStream bos = null;

		if (ValidateUtil.isBlank(fileName) || ValidateUtil.isNull(res) || ValidateUtil.isEmpty(excelData)) {
			throw new BaseAppException("结果数据为空,不能下载");
		}
		
		try {
			bos = new BufferedOutputStream(res.getOutputStream());
			res.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
			ExcelUtil.createExcel(excelData,bos);
			
			try {
				bos.flush();
			} catch (Exception e) {
			}
		} catch (IOException e) {
			LogUtil.error(logger, "生成Excel失败", e);
			throw new BaseAppException("生成Excel失败。",e.getMessage());
			
		}finally{
			
			try {
				bos.close();
			} catch (Exception e) {
			}
		}
	}
	
}
