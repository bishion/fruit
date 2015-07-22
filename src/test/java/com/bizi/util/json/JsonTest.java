package com.bizi.util.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.csnt.util.json.JsonMapper;

public class JsonTest {

	@Test
	public void testList(){
		User user1 = new User("bizi1","bizi1");
		User user2 = new User("bizi2","bizi1");
		User user3 = new User("bizi3","bizi1");
		User user4 = new User("bizi4","bizi1");
		User user5 = new User("bizi5","bizi1");
		User user6 = new User("bizi6","bizi1");
		
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);
		userList.add(user5);
		userList.add(user6);
		
		String jsonString = JsonMapper.toNormalJson(userList);
		JsonMapper jsonMapper = JsonMapper.buildNormalMapper();
		List<User> userList2 = jsonMapper.fromJson(jsonString, jsonMapper.constructParametricType(ArrayList.class, User.class));
		System.out.println(userList2);
	}
}
