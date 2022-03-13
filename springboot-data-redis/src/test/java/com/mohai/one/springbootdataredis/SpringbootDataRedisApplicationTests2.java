package com.mohai.one.springbootdataredis;

import com.mohai.one.springbootdataredis.config.HashMapping;
import com.mohai.one.springbootdataredis.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootDataRedisApplicationTests2 {

	@Autowired
	private RedisTemplate redisTemplate;

	@Resource(name="redisTemplate")
	HashOperations<String, byte[], byte[]> hashOperations;

	@Test
	void contextLoads() {
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis);
		System.out.println(redisTemplate.getConnectionFactory());
		// 获取数据并输出
		Set<String> keys = jedis.keys("*");
		Iterator<String> it=keys.iterator() ;
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key);
		}
	}

	@Test
	public void testBean(){
		Person person = new Person();
		person.setFirstName("Ming");
		person.setLastName("Li");
		HashMapping<Person> hashMapping = new HashMapping(hashOperations);
		hashMapping.writeHash("person",person);
		System.out.println(hashMapping.loadHash("person"));
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void testTransaction() throws Exception{
		//需要主动设置开始事务，可在配置类中统一设置
		stringRedisTemplate.setEnableTransactionSupport(true);
		try{
			// 开启事务
			stringRedisTemplate.multi();
			stringRedisTemplate.opsForValue().set("key", "value");
			stringRedisTemplate.opsForValue().set("key","value_new");
			int n = 1/0 ;//模拟异常
			// 提交事务
			stringRedisTemplate.exec();
		}catch (Exception e){
			// 回滚事务
			stringRedisTemplate.discard();
		}finally {
			//设置失效时间
			stringRedisTemplate.expire("key",5, TimeUnit.SECONDS);
		}
		System.out.println(stringRedisTemplate.opsForValue().get("key"));
	}

	@Test
	@Transactional
	public void testTransaction2() throws Exception{
		try{
			stringRedisTemplate.opsForValue().set("key2", "value2");
			stringRedisTemplate.opsForValue().set("key2","value_new2");
			int n = 1/0 ;//模拟异常
		}finally {
			//设置失效时间
			stringRedisTemplate.expire("key",5, TimeUnit.SECONDS);
		}
		System.out.println(stringRedisTemplate.opsForValue().get("key2"));
	}

	@Test
	public void testExecuteTransaction(){
		//测试事务
		List<Object> txResults = (List<Object>) stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				try {
					// 开启事务
					operations.multi();
					operations.opsForSet().add("key", "value1");
					operations.opsForSet().add("key", "value2");
					// 返回事务中所有操作的结果
					return operations.exec();
				} catch (Exception e) {
					operations.discard();
					e.printStackTrace();
				}
				return null;
			}
		});
		System.out.println("返回的结果数量: " + txResults.get(0));
		stringRedisTemplate.expire("key",5,TimeUnit.SECONDS);
	}

	@Test
	public void testExecute(){
		//测试execute方法
		stringRedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set("name".getBytes(),"逍遥子".getBytes());
				connection.set("age".getBytes(),"23".getBytes());
				return null;
			}
		});
		System.out.println(stringRedisTemplate.opsForValue().get("name"));
		System.out.println(stringRedisTemplate.opsForValue().get("age"));
		stringRedisTemplate.expire("name",5,TimeUnit.SECONDS);
		stringRedisTemplate.expire("age",5,TimeUnit.SECONDS);
	}

	@Test
	public void testExecutePipelined(){
		//从队列中弹出指定数量的元素
		List<Object> results = stringRedisTemplate.executePipelined(
			new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
					stringRedisConn.rPush("queue_","00001");
					stringRedisConn.rPush("queue_","00002");
					stringRedisConn.rPush("queue_","00003");
					stringRedisConn.rPush("queue_","00004");
					for(int i=0; i< 2; i++) {
						stringRedisConn.rPop("queue_");
					}
					return null;
				}
			});
		System.out.println(stringRedisTemplate.opsForList().range("queue_",0,-1));
		stringRedisTemplate.expire("queue_",5,TimeUnit.SECONDS);
	}


}