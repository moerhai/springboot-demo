package com.mohai.one.app.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:56
 */
@ApiModel(value= "分页参数")
public class PageParam{

    /**
     * 当前页
     */
    @ApiParam(value = "当前页，默认1",required = false,defaultValue = "1")
    private int current = 1;

    /**
     * 每页显示条数，默认 10
     */
    @ApiParam(value = "每页大小，默认10",required = false, defaultValue = "10")
    private int size = 10;

    /**
     * 是否进行 count 查询
     */
    @ApiParam(hidden = true)
    private boolean isSearchCount = true;

    @ApiParam(required = true)
    private Map<String,Object> data;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSearchCount() {
        return isSearchCount;
    }

    public void setSearchCount(boolean searchCount) {
        isSearchCount = searchCount;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}