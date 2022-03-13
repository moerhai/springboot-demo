package com.mohai.one.springbootdruid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootDruidApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testSqlParser(){
		String sql = "SELECT * FROM USER WHERE ID = '1'";
		//使用Parser解析生成AST，这里SQLStatement就是AST
		SQLStatement sqlStatement = SQLUtils.parseSingleMysqlStatement(sql);
		// 使用visitor来访问AST
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		sqlStatement.accept(visitor);
        // 打印
		System.out.println(visitor.getTables());
	}

}
