package com.kelly.logic.manager.inter;

import com.csnt.util.exception.BaseAppException;
import com.kelly.dto.UserDTO;

public interface IUserManager {

	/** 系统注册 */
	public void register(UserDTO user) throws BaseAppException ;
	
	/** 系统登录 */
	public UserDTO login(String username,String password) throws BaseAppException ;
	
}
