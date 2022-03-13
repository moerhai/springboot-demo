package com.mohai.one.app.wares.controller;

import com.mohai.one.app.core.dto.ResultDto;
import com.mohai.one.app.core.exception.BusinessException;
import com.mohai.one.app.wares.dto.AppCategoryDTO;
import com.mohai.one.app.wares.service.AppCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:44
 */
@Api(tags = "商品分类管理")
@RestController
@RequestMapping("/wares/appCategory")
public class AppCategoryController {

    @Autowired
    private AppCategoryService appCategoryService;

    /**
     * 查询商品分类列表
     * @param parentId
     * @return
     */
    @ApiOperation(value = "查询商品分类列表", notes = "获取所有的商品的分类信息，顶级分类的parentId为0，默认为返回顶级分类")
    @ApiImplicitParam(name = "parentId", value = "父级分类ID", required = false, dataType = "Long")
    @GetMapping(value = "/getCategoryInfo")
    public ResultDto<List<AppCategoryDTO>> getCategoryInfo(@RequestParam(value = "parentId", defaultValue = "0") Long parentId){
        List<AppCategoryDTO> appCategoryDTOS = appCategoryService.selectListByParentId(parentId);
        return ResultDto.ok(appCategoryDTOS);
    }

    /**
     * 根据id查询商品分类
     * @param catId
     * @return
     */
    @ApiOperation(value = "查询商品分类")
    @ApiImplicitParam(name = "catId", value = "商品分类ID", required = false, dataType = "Long")
    @GetMapping(value = "/get/{catId}")
    public ResultDto<AppCategoryDTO> get(@PathVariable("catId") Long catId){
        AppCategoryDTO appCategoryDTO=appCategoryService.selectByPrimaryKey(catId);
        return ResultDto.ok(appCategoryDTO);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasRole('ROLE_CATE_SAVE')")
    @ApiOperation(value = "保存分类信息")
    @PostMapping("/save")
    public ResultDto save(@Validated @RequestBody AppCategoryDTO appCategoryDTO){
        appCategoryService.save(appCategoryDTO);
        return ResultDto.ok();
    }

    /**
     * 修改
     */
    @PreAuthorize("hasRole('ROLE_CATE_EDIT')")
    @ApiOperation(value = "修改分类信息")
    @PutMapping("/update")
    public ResultDto update(@Validated @RequestBody AppCategoryDTO appCategoryDTO){
        appCategoryService.updateById(appCategoryDTO);
        return ResultDto.ok();
    }

    /**
     * 删除
     */
    @PreAuthorize("hasRole('ROLE_CATE_DEL')")
    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/delete")
    public ResultDto delete(@RequestBody Long[] categoryIds){
        for(Long id :categoryIds){
            List<Long> categoryList = appCategoryService.queryCategoryIdList(id);
            if(categoryList.size() > 0){
                throw new BusinessException("请先删除子栏目");
            }
        }
        appCategoryService.deleteBatchIds(Arrays.asList(categoryIds));
        return ResultDto.ok();
    }

}