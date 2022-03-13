package com.mohai.one.springbootconfiguration;

import com.mohai.one.springbootconfiguration.bean.House;
import com.mohai.one.springbootconfiguration.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootConfigurationApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	User user;
	@Autowired
	House house;

	@Test
	public void testProxyBeanMethods(){
		System.out.println(user.getHouse() == house);
	}

}
