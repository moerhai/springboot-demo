package com.mohai.one.springbootdataredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest
class SpringbootDataRedisApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void contextLoads() {
		stringRedisTemplate.delete("name");
		//打印连接工厂实例
		System.out.println(redisTemplate.getConnectionFactory());
		//通过stringRedisTemplate向redis中存值
		stringRedisTemplate.opsForValue().set("name","逍遥子");
		//通过redisTemplate获取，无法获取
		System.out.println("name:"+stringRedisTemplate.opsForValue().get("name"));
		//通过redisTemplate获取，无法获取
	//	System.out.println("name:"+redisTemplate.opsForValue().get("name"));
	}

	@Test
	public void testString(){
		//获取Redis字符串操作实例
		ValueOperations vo = redisTemplate.opsForValue();
		vo.set("name","风清扬");
		String name = vo.get("name").toString();
		System.out.println("name:"+name);
		//设置60秒过期
		redisTemplate.expire("name",60, TimeUnit.SECONDS);
	}

	@Test
	public void testList(){
		List<String> list1 = new ArrayList<>();
		list1.add("1");
		list1.add("2");
		list1.add("3");
		List<String> list2 = new ArrayList<>();
		list2.add("a");
		list2.add("b");
		list2.add("c");
        //获取Redis列表操作实例
		ListOperations listOperations = redisTemplate.opsForList();
		listOperations.leftPush("lList1",list1);
		listOperations.rightPush("rList2",list2);
		//不管是通过leftPush还是rightPush放的值都可以用leftPop或者rightPop来获取其中的值，
		//由于获取的遍历方向不同，所以效率也不同，所以最好是leftPush用leftPop遍历，rightPush用rightPop遍历
		List<String> redisList1 = (List<String>) listOperations.leftPop("lList1");
		List<String> redisList2 = (List<String>) listOperations.rightPop("rList2");
		System.out.println("redisList1:"+redisList1);
		System.out.println("redisList2:"+redisList2);
		//设置60秒过期
		redisTemplate.expire("lList1",60, TimeUnit.SECONDS);
		redisTemplate.expire("rList2",60, TimeUnit.SECONDS);
	}

	@Test
	public void testSet(){
        //获取Redis集合操作实例
		SetOperations setOperations = redisTemplate.opsForSet();
		//为集合key添加多个值
		setOperations.add("one","1","2","3");
		setOperations.add("one","4","5","6","3");
		//移除集合中多个value值
		setOperations.remove("one","4");
		//获取集合中所有元素
		Set set = setOperations.members("one");
		System.out.println("set:"+set);
		//设置60秒过期
		redisTemplate.expire("one",60, TimeUnit.SECONDS);
	}

	@Test
	public void testSortedSet(){
		//获取Redis集合操作实例
		ZSetOperations zSetOperations = redisTemplate.opsForZSet();
		//新增一个有序集合，存在返回false，不存在返回true
		zSetOperations.add("zone","1",1d);
		zSetOperations.add("zone","2",2d);
		zSetOperations.add("zone","3",3d);
		zSetOperations.add("zone","4",4d);
		zSetOperations.add("zone","5",5d);
		zSetOperations.add("zone","6",6d);
		//从有序集合中移除一个元素
		zSetOperations.remove("zone","4");
		//给指定元素加分
		Double score = zSetOperations.incrementScore("zone", "3", 2);
		//返回有序集合中指定成员的排名，其中按成员分数值递增(从小到大)顺序排列
		Long rank = zSetOperations.rank("zone","3");
		//通过指定索引区间，返回有序集合中指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列，-1为返回全部
		Set rangeSet = zSetOperations.range("zone",0,-1);
		//通过指定索引区间，返回有序集合中指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
		Set reverseRangeSet = zSetOperations.reverseRange("zone",0,1);
        //打印
		System.out.println("score:"+score);
		System.out.println("rank:"+rank);
		System.out.println("rangeSet:"+rangeSet);
		System.out.println("reverseRangeSet:"+reverseRangeSet);
		//设置60秒过期
		redisTemplate.expire("zone",60, TimeUnit.SECONDS);
	}

	@Test
	public void testHash(){
		Map map = new HashMap<>();
		map.put("name","风清扬");
		map.put("sex","男");
		map.put("age",99);
        //获取Redis哈希操作实例
		HashOperations hashOperations = redisTemplate.opsForHash();
		//将map放入redis
		hashOperations.putAll("aMap",map);
		//put不管什么直接覆盖之前的数据
		hashOperations.put("aMap","tel","6666666");
		//putIfAbsent只会在key对应的value不存在时存入并返回null
		hashOperations.putIfAbsent("aMap","name","逍遥子");
		//通过key获取存储的map对象
		Map entriesMap = hashOperations.entries("aMap");
		//获取redis中存入map的value集合
		List valuesList = hashOperations.values("aMap");
		//获取redis中存入map的key集合
		Set keysSet = hashOperations.keys("aMap");
		//获取redis中存入的map中name的值
		String nameVal = hashOperations.get("aMap","name").toString();
		Long mapSize = hashOperations.size("aMap");
		//打印
		System.out.println("entriesMap:"+entriesMap);
		System.out.println("valuesList:"+valuesList);
		System.out.println("keysSet:"+keysSet);
		System.out.println("nameVal:"+nameVal);
		System.out.println("mapSize:"+mapSize);
		//设置60秒过期
		redisTemplate.expire("aMap",5, TimeUnit.SECONDS);
	}

	@Test
	public void get() {
		stringRedisTemplate.delete("kk");
		stringRedisTemplate.delete("k1");
		// 测试线程安全
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		IntStream.range(0, 1000).forEach(i ->
				executorService.execute(() -> stringRedisTemplate.opsForValue().increment("kk", 1))
		);
	//System.out.println(stringRedisTemplate.opsForValue().get("kk"));
	}


}
