package com.kelly.view;

import com.csnt.util.exception.BaseAppException;
import com.csnt.util.json.JsonMapper;
import com.csnt.util.log.LogUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.BaseConst;
import com.kelly.base.ExceptionConst;
import com.kelly.base.framework.BaseAction;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.ResultDTO;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IUserManager;

/**
 * 系统登陆功能
 *
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IUserManager userManager = (IUserManager) BeanService.getBean("IUserManager");
	
	public void login(){
		ResultDTO result = new ResultDTO();
		try{
			// 获取注册信息，并校验
			if(ValidateUtil.isBlank(postData)){
				throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
			}
			UserDTO user = JsonMapper.fromJson(postData, UserDTO.class);
			
			UserDTO userDTO = userManager.login(user.getUsername(), user.getPassword());
			
			this.getSession().setAttribute(BaseConst.USER_INFO, userDTO);
			result.setResult(BaseConst.YES);
			result.setMessage((String)this.getSession().getAttribute(BaseConst.PAGE_BEFORE_LOGIN));
			
		}catch (BaseAppException e) {
			LogUtil.error(logger, e.getExceptionCode(), e.getExceptionMsg());
			result.setMessage(e.getExceptionMsg());
		}catch (Exception e){
			LogUtil.error(logger, "注册--Action", e);
			result.setMessage(e.getMessage());
		}
		
		this.writeResponse(JsonMapper.toNonNullJson(result));
	}
}
