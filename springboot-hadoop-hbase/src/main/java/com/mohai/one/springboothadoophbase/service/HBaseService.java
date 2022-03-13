package com.mohai.one.springboothadoophbase.service;

import com.mohai.one.springboothadoophbase.mapper.EntityRowMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/14 01:34
 */
@Service
public class HBaseService {

    private static final String TABLE_NAME= "student_table";

    @Autowired
    private HbaseTemplate hbaseTemplate;


    /**
     *
     * @param id
     * @return
     */
    public List<Map<String, Map<String, String>>> query(String id){
        List<Map<String,Map<String, String>>> md = hbaseTemplate.find(TABLE_NAME, id, (result, rowNum)->{
            Cell[] cells = result.rawCells();
            Map<String,Map<String, String>> data = new HashMap<>(16);
            for(Cell c : cells){
                //列族
                String columnFamily = new String(CellUtil.cloneFamily(c));
                //列
                String rowName = new String(CellUtil.cloneQualifier(c));
                //数据
                String value = new String(CellUtil.cloneValue(c));

                Map<String, String> obj = data.get(columnFamily);
                if(null == obj){
                    obj = new HashMap<>(16);
                }
                obj.put(rowName, value);
            }
            return data;
        });

        return md;
    }

    /**
     * 保存
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param rowMap
     */
    public void save(String tableName, String rowKey, String columnFamily,Map<String,String> rowMap) {
        for (Map.Entry<String, String> en : rowMap.entrySet()) {
            hbaseTemplate.put(tableName, rowKey, columnFamily, en.getKey(), Bytes.toBytes(en.getValue()));
        }
    }

    /**
     * 获取一条记录
     * @param tableName
     * @param rowName
     * @return
     */
    public Map<String, Object> get(String tableName, String rowName) {
        return hbaseTemplate.get(tableName, rowName,new RowMapper<Map<String,Object>>(){
            @Override
            public Map<String, Object> mapRow(Result result, int i) throws Exception {
                List<Cell> ceList =   result.listCells();
                Map<String,Object> map = new HashMap<String, Object>(16);
                if(ceList!=null&&ceList.size()>0){
                    for(Cell cell:ceList){
                        map.put(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength())+
                                        "_"+Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()),
                                Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                    }
                }
                return map;
            }
        });
    }

    /**
     * 获取一个数据
     * @param tableName
     * @param rowName
     * @param familyName
     * @param qualifier
     * @return
     */
    public String get(String tableName ,String rowName, String familyName, String qualifier) {
        return hbaseTemplate.get(tableName, rowName,familyName,qualifier ,new RowMapper<String>(){
            @Override
            public String mapRow(Result result, int i) throws Exception {
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
    }

    /**
     * 删除一条数据
     * @param tableName
     * @param rowKey
     */
    public void delete(String tableName, final String rowKey) {
        hbaseTemplate.execute(tableName, new TableCallback<Boolean>() {
            public Boolean doInTable(HTableInterface table) throws Throwable {
                boolean flag = false;
                try{
                    List<Delete> list = new ArrayList<Delete>();
                    Delete d1 = new Delete(rowKey.getBytes());
                    list.add(d1);
                    table.delete(list);
                    flag = true;
                }catch(Exception e){
                    e.printStackTrace();
                }
                return flag;
            }
        });
    }


    public List<String> find(String tableName,String family,String column){
        List<String> rows = hbaseTemplate.find(tableName, family,column, new RowMapper<String>() {
            public String mapRow(Result result, int rowNum) throws Exception {
                return Bytes.toString(result.getRow());
            }
        });
        return rows;
    }



    public List<Result> getRowKeyAndColumn(String tableName, String startRowKey, String stopRowKey, String column, String qualifier) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        if (StringUtils.isNotBlank(column)) {
            filterList.addFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
        }
        if (StringUtils.isNotBlank(qualifier)) {
            filterList.addFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(qualifier))));
        }
        Scan scan = new Scan();
        if (filterList.getFilters().size() > 0) {
            scan.setFilter(filterList);
        }
        scan.setStartRow(Bytes.toBytes(startRowKey));
        scan.setStopRow(Bytes.toBytes(stopRowKey));
        return hbaseTemplate.find(tableName, scan, (rowMapper, rowNum) -> rowMapper);
    }

    public List<Result> getListRowKeyData(String tableName, List<String> rowKeys, String familyColumn, String column) {
        return rowKeys.stream().map(rk -> {
            if (StringUtils.isNotBlank(familyColumn)) {
                if (StringUtils.isNotBlank(column)) {
                    return hbaseTemplate.get(tableName, rk, familyColumn, column, (rowMapper, rowNum) -> rowMapper);
                } else {
                    return hbaseTemplate.get(tableName, rk, familyColumn, (rowMapper, rowNum) -> rowMapper);
                }
            }
            return hbaseTemplate.get(tableName, rk, (rowMapper, rowNum) -> rowMapper);
        }).collect(Collectors.toList());
    }


}
