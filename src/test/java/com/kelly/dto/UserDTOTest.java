package com.kelly.dto;

import org.junit.Test;

import com.csnt.util.json.JsonMapper;

public class UserDTOTest {

	@Test
	public void test() {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("bizie");
		userDTO.setPassword("bizi");
		System.out.println(JsonMapper.toNonNullJson(userDTO));
	}

}
