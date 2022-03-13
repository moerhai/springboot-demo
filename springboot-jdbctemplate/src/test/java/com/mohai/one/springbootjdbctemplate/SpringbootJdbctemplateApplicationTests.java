package com.mohai.one.springbootjdbctemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootJdbctemplateApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void testJdbcTemplate(){
		String sql = "select * from user;";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		System.out.println(maps);
	}

}
