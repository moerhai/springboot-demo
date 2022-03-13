package com.mohai.one.springbootservlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//在spring boot启动时会扫描@WebServlet @WebFilter @WebListener注解，并创建该类的实例
@ServletComponentScan
public class SpringbootServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServletApplication.class, args);
	}

}
