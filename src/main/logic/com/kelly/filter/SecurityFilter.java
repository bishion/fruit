package com.kelly.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.ConfigConst;
import com.kelly.dto.UserDTO;

public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 验证是否需要登陆权限
		HttpServletRequest req = (HttpServletRequest)request;
		//获得请求的URL
		String url=req.getRequestURL().toString();
		String pageBeforeLogin = url;
		String contextPath=req.getContextPath();
		url=url.substring(url.indexOf(contextPath)+contextPath.length());
		// 如果开关关闭，则全部放行。
		if(BaseConst.NO.equals(ConfigConst.LOGIN_CHECK_SWITCH)){
			chain.doFilter(request, response);
			return;
		}
		// 静态文件或者是某些页面全部放行
		if(url.matches(ConfigConst.FILES_CAN_PASS) || url.matches(ConfigConst.PAGES_CAN_PASS)){
			chain.doFilter(request, response);
			return;
		}
		// 运行到这的url都需要登陆权限，如果需要，看是否已经登陆
		UserDTO user = (UserDTO) req.getSession().getAttribute(BaseConst.USER_INFO);
		if(ValidateUtil.isNull(user)){
			req.getSession().setAttribute(BaseConst.PAGE_BEFORE_LOGIN, pageBeforeLogin);
			System.out.println("登陆前页面："+pageBeforeLogin);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(ConfigConst.LOGIN_PAGE);
			requestDispatcher.forward(request, response);
			return;
		}
		
		// 看页面是否要求admin权限
		if(url.matches(ConfigConst.PAGES_NEED_ADMIN) && !ConfigConst.ADMIN_LOGINNAME.equals(user.getUsername())){
			request.setAttribute("message", "需要管理员权限");
			return;
		}
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

}
