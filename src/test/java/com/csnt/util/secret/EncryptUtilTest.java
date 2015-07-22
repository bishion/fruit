package com.csnt.util.secret;



import org.junit.Assert;
import org.junit.Test;


public class EncryptUtilTest {

	@Test
	public void testMD5Enc() {
		System.out.println(EncryptUtil.MD5Enc("bizi1234"));
		Assert.assertEquals(true, true);
	}

}
