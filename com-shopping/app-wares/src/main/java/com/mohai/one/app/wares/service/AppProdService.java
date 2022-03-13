package com.mohai.one.app.wares.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mohai.one.app.core.constant.ComConstants;
import com.mohai.one.app.core.dto.PageParam;
import com.mohai.one.app.core.utils.BeanUtil;
import com.mohai.one.app.core.utils.SecurityUserUtil;
import com.mohai.one.app.wares.dao.AppProdMapper;
import com.mohai.one.app.wares.domain.AppProd;
import com.mohai.one.app.wares.dto.AppProdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:42
 */
@Service
@Transactional
public class AppProdService {

    @Autowired
    private AppProdMapper appProdMapper;

    /**
     * 根据主键id查询商品信息
     * @param prodId
     * @return
     */
    public AppProdDTO selectByPrimaryKey(Long prodId){
        AppProd appProd = appProdMapper.selectByPrimaryKey(prodId);
        return BeanUtil.copyProperties(appProd,AppProdDTO.class);
    }

    /**
     * 保存商品信息
     * @param appProdDTO
     */
    public void save(AppProdDTO appProdDTO) {
        appProdDTO.setCreateTime(new Date());
        appProdDTO.setCreateUser(SecurityUserUtil.getUserDetails().getUsername());
        appProdMapper.insertSelective(BeanUtil.copyProperties(appProdDTO,AppProd.class));
    }

    /**
     * 修改商品信息
     * @param appProdDTO
     */
    public void updateByProdId(AppProdDTO appProdDTO) {
        appProdDTO.setUpdateTime(new Date());
        appProdDTO.setUpdateUser(SecurityUserUtil.getUserDetails().getUsername());
        appProdMapper.updateByPrimaryKeySelective(BeanUtil.copyProperties(appProdDTO,AppProd.class));
    }

    /**
     * 更新商品信息状态为2
     * @param prodId
     */
    public void updateProdStatus(Long prodId) {
        AppProd appProd = new AppProd();
        appProd.setProId(prodId);
        appProd.setProStatus(ComConstants.STATUS_2);
        appProd.setUpdateTime(new Date());
        appProd.setUpdateUser(SecurityUserUtil.getUserDetails().getUsername());
        appProdMapper.updateProdStatus(appProd);
    }

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    public List<AppProdDTO> findProdPage(PageParam pageParam) {
        //  开始分页 pageNum-当前页码、pageSize-每页显示条数
        PageHelper.startPage(pageParam.getCurrent(),pageParam.getSize());
        // 利用 fastjson 转换
        AppProd appProd = JSON.parseObject(JSON.toJSONString(pageParam.getData()), AppProd.class);
        List<AppProd> appProds = appProdMapper.selectPageProd(appProd);
        List<AppProdDTO> appProdDTOS = BeanUtil.copyPropertiesOfPage((Page<?>) appProds,AppProdDTO.class);
        PageHelper.clearPage();
        return appProdDTOS;
    }
}