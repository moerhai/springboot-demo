package com.mohai.one.springboothadoophbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootHadoopHbaseApplicationTests {

	FileSystem fileSystem=null;
	Configuration configuration=null;
	public static final String HDFS_PATH="hdfs://hadoopMaster:8020";

	@Before
	public void setup() throws Exception{
		System.out.println("HDFS.setup");
		configuration= HBaseConfiguration.create();
		fileSystem=FileSystem.get(new URI(HDFS_PATH),configuration,"root");
	}


	@Test
	void contextLoads() {
		System.out.println(configuration);
	}

	@Autowired
	private HbaseTemplate hbaseTemplate;


	/**
	 * 创建表
	 * create <table>, {NAME => <column family>, VERSIONS => <VERSIONS>}
	 * shell command: create ‘test’, 'info',‘cf1’
	 */


	@Test
	public void testGet(){
		Map<String,Object> map = hbaseTemplate.get("test", "001", new RowMapper<Map<String,Object>>() {
			@Override
			public Map<String,Object> mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				Map<String,Object> map = new HashMap<String, Object>(16);
				for(Cell cell:ceList){
					map.put(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength())+
									"_"+Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),
							Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
				}
				return map;
			}
		});
		System.out.println(map);
	}

	@Test
	public void testGet2(){
		Map<String,Object> map = hbaseTemplate.get("test", "001", "info",new RowMapper<Map<String,Object>>() {
			@Override
			public Map<String,Object> mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				Map<String,Object> map = new HashMap<String, Object>(16);
				for(Cell cell:ceList){
					map.put(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength())+
									"_"+Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),
							Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
				}
				return map;
			}
		});
		System.out.println(map);
	}

	@Test
	public void testGet3(){
		String str = hbaseTemplate.get("test", "001", "info","name",new RowMapper<String>() {
			@Override
			public String mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				String res = "";
				if(ceList!=null&&ceList.size()>0){
					for(Cell cell:ceList){
						res = Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
					}
				}
				return res;
			}
		});
		System.out.println(str);
	}

	/**
	 * 命令put 'test','001','info:name','用户名'
	 * 表示插入表名为test，Rowkey为001的行（没有则创建），列簇info下面的列name的值为用户名
	 */
	@Test
	public void testPut(){
		String value = "用户名";
		hbaseTemplate.put("test","001","info","name",value.getBytes());
		System.out.println(value);
	}

	@Test
	public void testScan(){
		List<Map<String,Map<String, String>>> md = hbaseTemplate.find("test", "info", (result, rowNum)->{
			Cell[] cells = result.rawCells();
			Map<String,Map<String, String>> data = new HashMap<>(16);
			// 行键，列族和列限定符一起确定一个单元Cell
			for(Cell c : cells){
				//列族
				String columnFamily = new String(CellUtil.cloneFamily(c));
				//列限定符
				String rowName = new String(CellUtil.cloneQualifier(c));
				//数据
				String value = new String(CellUtil.cloneValue(c));

				Map<String, String> obj = data.get(columnFamily);
				if(null == obj){
					obj = new HashMap<>(16);
				}
				obj.put(rowName, value);
				data.put(columnFamily,obj);
			}
			return data;
		});
		System.out.println(md);
	}

	@Test
	public void testScan2(){
		List<String> md = hbaseTemplate.find("test", "info","name", (result, rowNum)->{
			return Bytes.toString(result.getRow())+ ":("+ result.rawCells()[0] +")" + Bytes.toString(result.value());
		});
		System.out.println(md);
	}

	@Test
	public void testDelete(){
		hbaseTemplate.delete("test","001","info");
	}

	@Test
	public void testDelete2(){
		hbaseTemplate.delete("test","001","info","name");
	}

}
