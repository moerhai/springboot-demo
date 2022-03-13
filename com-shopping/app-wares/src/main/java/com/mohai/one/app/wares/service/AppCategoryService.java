package com.mohai.one.app.wares.service;

import com.mohai.one.app.core.constant.ComConstants;
import com.mohai.one.app.core.exception.BusinessException;
import com.mohai.one.app.core.utils.BeanUtil;
import com.mohai.one.app.core.utils.SecurityUserUtil;
import com.mohai.one.app.wares.dao.AppCategoryMapper;
import com.mohai.one.app.wares.domain.AppCategory;
import com.mohai.one.app.wares.dto.AppCategoryDTO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:42
 */
@Service
@Transactional
public class AppCategoryService {

    @Autowired
    private AppCategoryMapper appCategoryMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据主键id查询分类信息
     * @param catId
     * @return
     */
    public AppCategoryDTO selectByPrimaryKey(Long catId){
        AppCategory appCategory = appCategoryMapper.selectByPrimaryKey(catId);
        return BeanUtil.copyProperties(appCategory,AppCategoryDTO.class);
    }

    /**
     * 根据父级id查询子分类信息
     * @param parentId
     * @return
     */
    public List<AppCategoryDTO> selectListByParentId(Long parentId) {
       List<AppCategory> appCategories = appCategoryMapper.selectListByParentId(parentId);
       return BeanUtil.copyPropertiesOfList(appCategories,AppCategoryDTO.class);
    }

    /**
     * 保存商品分类信息
     * @param appCategoryDTO
     */
    public void save(AppCategoryDTO appCategoryDTO) {
        appCategoryDTO.setCreateTime(new Date());
        appCategoryDTO.setCreateUser(SecurityUserUtil.getUserDetails().getUsername());
        appCategoryMapper.insertSelective(BeanUtil.copyProperties(appCategoryDTO,AppCategory.class));
    }

    /**
     * 更新商品分类信息
     * @param appCategoryDTO
     */
    public void updateById(AppCategoryDTO appCategoryDTO) {
        appCategoryDTO.setUpdateTime(new Date());
        appCategoryDTO.setUpdateUser(SecurityUserUtil.getUserDetails().getUsername());
        appCategoryMapper.updateByPrimaryKeySelective(BeanUtil.copyProperties(appCategoryDTO,AppCategory.class));
    }

    /**
     * 根据父级id查询子分类信息的id集合
     * @param id
     * @return
     */
    public List<Long> queryCategoryIdList(Long id) {
        List<AppCategory> appCategories = appCategoryMapper.selectListByParentId(id);
        List<Long> ids = appCategories.stream().filter(Objects::nonNull).map(p -> p.getCatId()).collect(Collectors.toList());
        return ids;
    }

    /**
     * mybatis 批量操作示例
     * @param asList
     */
    public void deleteBatchIds(List<Long> asList) {
         String username = SecurityUserUtil.getUserDetails().getUsername();
        // 当前会话开启批量模式
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        // 从会话中获取到mapper
        AppCategoryMapper appCategoryMapper = sqlSession.getMapper(AppCategoryMapper.class);
        try {
            for (Long id : asList) {
                AppCategory appCategory = new AppCategory();
                appCategory.setCatId(id);
                appCategory.setCatStatus(ComConstants.STATUS_2);
                appCategory.setUpdateTime(new Date());
                appCategory.setUpdateUser(username);
                appCategoryMapper.updateCategoryStatus(appCategory);
            }
            //提交
            sqlSession.commit();
            sqlSession.flushStatements();
        }catch (Exception e) {
            sqlSession.rollback();
            throw new BusinessException("批量删除商品分类信息失败！");
        }finally {
            sqlSession.close();
        }
    }
}