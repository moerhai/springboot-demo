package com.mohai.one.springbootdataredis;

import com.mohai.one.springbootdataredis.config.HashMapping;
import com.mohai.one.springbootdataredis.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootDataRedisApplicationTests1 {

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private RedisTemplate redisTemplate;

	//@Resource(name="redisTemplate")
	//HashOperations<String, byte[], byte[]> hashOperations;

	@Autowired
	HashOperations<String, String, Object> hashOperations;

	@Test
	void contextLoads() {
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis);
		//查看服务是否运行
		System.out.println("服务正在运行: "+jedis.ping());
		// 获取数据并输出
		Set<String> keys = jedis.keys("*");
		Iterator<String> it=keys.iterator() ;
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key);
		}
		jedis.close();
	}

	@Test
	public void test(){
		//连接本地的 Redis 服务
		Jedis jedis = jedisPool.getResource();
		//查看服务是否运行
		System.out.println("服务正在运行: "+jedis.ping());
		for (int i = 0; i < 2; i++) {
			int finalI = i;
			new Thread(() -> {
				for (int j = 0; j < 10; j++) {
					jedis.set("a" + finalI, String.valueOf(finalI));
					System.out.println("a" + finalI + " = " + jedis.get("a" + finalI));
				}
			}).start();
		}
	}


	@Test
	public void testString(){
		//获取Redis字符串操作实例
		Jedis jedis = jedisPool.getResource();
		jedis.set("name","风清扬");
		jedis.expire("name",60);
		String name = jedis.get("name");
		System.out.println("name:"+name);
		//将Jedis实例归还给JedisPool
		jedis.close();
	}

	@Test
	public void testList(){
		//获取Redis字符串操作实例
		Jedis jedis = jedisPool.getResource();
		jedis.lpush("list", "IE");
		jedis.lpush("list", "Chrome");
		jedis.lpush("list", "Firefox");
		// 从列表中获取start到stop之间的元素
		List<String> list = jedis.lrange("list", 0 ,2);
		//遍历输出
		for(int i=0; i<list.size(); i++) {
			System.out.println("列表项为: "+list.get(i));
		}
		//将Jedis实例归还给JedisPool
		jedis.close();
	}

	@Test
	public void testSet(){
		//获取Redis字符串操作实例
		Jedis jedis = jedisPool.getResource();
		jedis.sadd("set","R","G","B");
		jedis.sadd("set","H","A","I");
		jedis.sadd("set","H","A","I");
		Set<String> set = jedis.smembers("set");
		//遍历输出
		for(String str : set){
			System.out.println("集合项为: "+str);
		}
		//将Jedis实例归还给JedisPool
		jedis.close();
	}

	@Test
	public void testSortedSet(){
		//获取Redis字符串操作实例
		Jedis jedis = jedisPool.getResource();
		jedis.zadd("zset",1d,"R");
		jedis.zadd("zset",2d,"G");
		jedis.zadd("zset",3d,"B");
		jedis.zadd("zset",3d,"B");
		jedis.zadd("zset",4d,"H");
		jedis.zadd("zset",5d,"A");
		jedis.zadd("zset",6d,"I");
        // 获取有序集合的成员数
		Long num = jedis.zcard("zset");
		System.out.println("成员数："+num);
		// 给指定成员的分数加上增量
		jedis.zincrby("zset",7d,"G");
		// 返回有序集合中指定成员的索引
		Long index = jedis.zrank("zset","G");
		System.out.println("索引："+index);
		// 通过索引区间返回有序集合指定区间内的成员，元素和分数
		Set<Tuple> zset = jedis.zrangeWithScores("zset",0,10);
		//遍历输出
		for(Tuple tuple : zset){
			System.out.println("有序集合项为: "+tuple.getScore() + ":"+tuple.getElement());
		}
		//将Jedis实例归还给JedisPool
		jedis.close();
	}

	@Test
	public void testHash(){
		Map map = new HashMap<>();
		map.put("name","风清扬");
		map.put("sex","男");
		map.put("age","99");
        //获取Redis字符串操作实例
		Jedis jedis = jedisPool.getResource();
        //将map设置到哈希表key中
		jedis.hmset("hmap",map);
		//将哈希表key中的字段field的值设为value
		jedis.hset("hmap","tel","6666666");
        //有在字段 field 不存在时，设置哈希表字段的值
		jedis.hsetnx("hmap","name","逍遥子");
		//删除一个或多个哈希表字段
		jedis.hdel("hmap","age");
		//获取redis中存入map的key集合
		Set<String> keysSet = jedis.hkeys("hmap");
		//获取redis中存入map的value集合
		List<String> valuesList = jedis.hvals("hmap");
		//获取redis中存入的map中name的值
		String nameVal = jedis.hget("hmap","name");
		//获取哈希表中字段的数量
		Long mapSize = jedis.hlen("hmap");
		//打印
		System.out.println("valuesList:"+valuesList);
		System.out.println("keysSet:"+keysSet);
		System.out.println("nameVal:"+nameVal);
		System.out.println("mapSize:"+mapSize);
        //将Jedis实例归还给JedisPool
		jedis.close();
	}

}
