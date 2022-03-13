package com.mohai.one.springbootcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@EnableCaching(mode = AdviceMode.PROXY)
public class SpringbootCacheApplication implements CommandLineRunner {

	@Autowired
	private CacheManager cacheManager;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCacheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(cacheManager);
	}
}
