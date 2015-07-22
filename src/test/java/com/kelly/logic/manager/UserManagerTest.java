package com.kelly.logic.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.csnt.util.exception.BaseAppException;
import com.csnt.util.validate.Assert;
import com.kelly.base.framework.BeanService;
import com.kelly.dto.UserDTO;
import com.kelly.logic.manager.inter.IUserManager;
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations={"classpath:config/spring/applicationContext-*.xml"})

public class UserManagerTest{

	@Test
	public void testRegisterAndLogin() {
		IUserManager userManager = BeanService.getBean(IUserManager.class);
		UserDTO user = new UserDTO();
		user.setName("Kelly");
		user.setPassword("123456");
		user.setPhone("18001697342");
		user.setUsername("hliu");
		try {
			userManager.register(user);
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		
		UserDTO user2 = null;
		try {
			user2 = userManager.login("hliu", "123456");
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		Assert.isTrue(user.getName().equals(user2.getName()));
	}


}
