package com.mohai.one.springbootcacheredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootCacheRedisApplication implements CommandLineRunner {

	@Autowired
	private CacheManager cacheManager;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCacheRedisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(cacheManager);
	}
}
