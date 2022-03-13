package com.mohai.one.app.wares.controller;

import com.mohai.one.app.core.dto.PageParam;
import com.mohai.one.app.core.dto.ResultDto;
import com.mohai.one.app.wares.dto.AppProdDTO;
import com.mohai.one.app.wares.service.AppProdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:44
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/wares/appProd")
public class AppProdController {

    @Autowired
    private AppProdService appProdService;

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "分页查询商品")
    @PostMapping(value = "/list")
    public ResultDto<List<AppProdDTO>> list(@RequestBody PageParam pageParam){
        List<AppProdDTO> appProdDTOS = appProdService.findProdPage(pageParam);
        return ResultDto.ok(appProdDTOS);
    }

    /**
     * 根据主键查询单条商品信息
     * @param proId
     * @return
     */
    @ApiOperation(value = "查询商品")
    @ApiImplicitParam(name = "proId", value = "商品ID", required = false, dataType = "Long")
    @GetMapping(value = "/get/{proId}")
    public ResultDto<AppProdDTO> get(@PathVariable("proId") Long proId){
        AppProdDTO appProdDTO = appProdService.selectByPrimaryKey(proId);
        return ResultDto.ok(appProdDTO);
    }

    /**
     * 保存
     */
    @PreAuthorize("hasRole('ROLE_PRO_SAVE')")
    @ApiOperation(value = "保存商品信息")
    @PostMapping("/save")
    public ResultDto save(@Validated @RequestBody AppProdDTO appProdDTO){
        appProdService.save(appProdDTO);
        return ResultDto.ok();
    }

    /**
     * 修改
     */
    @PreAuthorize("hasRole('ROLE_PRO_EDIT')")
    @ApiOperation(value = "修改商品信息")
    @PutMapping("/update")
    public ResultDto update(@Validated @RequestBody AppProdDTO appProdDTO){
        appProdService.updateByProdId(appProdDTO);
        return ResultDto.ok();
    }

    /**
     * 删除
     */
    @PreAuthorize("hasRole('ROLE_PRO_DEL')")
    @ApiOperation(value = "删除商品信息")
    @ApiImplicitParam(name = "proId", value = "商品ID", required = false, dataType = "Long")
    @DeleteMapping("/delete/{proId}")
    public ResultDto delete(@PathVariable("proId") Long proId){
        appProdService.updateProdStatus(proId);
        return ResultDto.ok();
    }

}