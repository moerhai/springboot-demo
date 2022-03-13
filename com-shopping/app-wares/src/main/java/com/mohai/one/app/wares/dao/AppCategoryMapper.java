package com.mohai.one.app.wares.dao;

import com.mohai.one.app.wares.domain.AppCategory;

import java.util.List;

public interface AppCategoryMapper {
    int deleteByPrimaryKey(Long catId);

    int insert(AppCategory record);

    int insertSelective(AppCategory record);

    AppCategory selectByPrimaryKey(Long catId);

    int updateByPrimaryKeySelective(AppCategory record);

    int updateByPrimaryKey(AppCategory record);

    List<AppCategory> selectListByParentId(Long parentId);

    int updateCategoryStatus(AppCategory record);
}