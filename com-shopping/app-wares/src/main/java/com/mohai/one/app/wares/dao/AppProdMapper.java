package com.mohai.one.app.wares.dao;

import com.mohai.one.app.wares.domain.AppProd;

import java.util.List;

public interface AppProdMapper {
    int deleteByPrimaryKey(Long proId);

    int insert(AppProd record);

    int insertSelective(AppProd record);

    AppProd selectByPrimaryKey(Long proId);

    int updateByPrimaryKeySelective(AppProd record);

    int updateByPrimaryKeyWithBLOBs(AppProd record);

    int updateByPrimaryKey(AppProd record);

    int updateProdStatus(AppProd record);

    List<AppProd> selectPageProd(AppProd appProd);
}