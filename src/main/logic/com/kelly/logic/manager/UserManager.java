package com.kelly.logic.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csnt.util.bean.BeanUtil;
import com.csnt.util.exception.BaseAppException;
import com.csnt.util.log.LogUtil;
import com.csnt.util.secret.EncryptUtil;
import com.csnt.util.validate.ValidateUtil;
import com.kelly.base.ExceptionConst;
import com.kelly.base.framework.BaseDao;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IUserManager;
import com.kelly.pojo.fruit.SysUser;

public class UserManager implements IUserManager{

	private BaseDao<SysUser> userDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/* 
	 * 系统注册
	 */
	@Override
	public void register(UserDTO user) throws BaseAppException {
		try{
			SysUser sysuser = new SysUser();
			BeanUtil.copyProperties(user, sysuser);
			
			// 密码加密保存
			sysuser.setPassword(EncryptUtil.MD5Enc(user.getPassword()));
			userDao.save(sysuser);
			
		}catch(Exception e){
			LogUtil.error(logger, "用户注册模块", e);
			throw new BaseAppException(ExceptionConst.REGISTER_ERROR);
		}
		
	}

	/* 
	 * 系统登录
	 */
	@Override
	public UserDTO login(String username, String password) throws BaseAppException {
		if(ValidateUtil.hasOneNullOrEmpty(username,password)){
			LogUtil.error(logger, "注册模块-Manager", "username",username,"password",password,"用户名或密码为空！");
			throw new BaseAppException(ExceptionConst.SYSTEM_PARAM_ERROR);
		}
		SysUser sysUser = userDao.get(SysUser.class, username);
		
		if(ValidateUtil.isNull(sysUser) || !sysUser.getPassword().equals(EncryptUtil.MD5Enc(password))){
			throw new BaseAppException(ExceptionConst.LOGIN_FAILED);
		}
		UserDTO user = new UserDTO();
		user.setName(sysUser.getName());
		user.setPhone(sysUser.getPhone());
		user.setUsername(username);
		
		return user;
	}
	
	public void setUserDao(BaseDao<SysUser> userDao) {
		this.userDao = userDao;
	}
}
