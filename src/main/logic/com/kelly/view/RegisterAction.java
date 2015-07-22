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
 * 系统注册功能
 * 保存注册系统，密码要加密
 */
public class RegisterAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private IUserManager userManager = (IUserManager) BeanService.getBean("IUserManager");
	public void register(){
		ResultDTO result = new ResultDTO();
		try{
			// 获取注册信息，并校验
			if(ValidateUtil.isBlank(postData)){
				throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
			}
			UserDTO user = JsonMapper.fromJson(postData, UserDTO.class);
			
			userManager.register(user);
			
			result.setResult(BaseConst.YES);
		}catch (BaseAppException e) {
			result.setMessage(e.getExceptionMsg());
		}catch (Exception e){
			LogUtil.error(logger, "注册--Action", e);
			result.setMessage(e.getMessage());
		}
		
		this.writeResponse(JsonMapper.toNonNullJson(result));
	}
	
}
