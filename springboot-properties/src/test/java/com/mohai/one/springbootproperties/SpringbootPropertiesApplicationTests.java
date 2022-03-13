package com.mohai.one.springbootproperties;

import com.mohai.one.springbootproperties.bean.UserBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootPropertiesApplicationTests {

	@Resource
	private UserBean userBean;

	@Test
	public void testUseBean(){
		System.out.println(userBean);
	}


	@Test
	void contextLoads() {
	}



}
