package com.mohai.one.app.wares.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:47
 */
@ApiModel(value= "商品分类信息")
public class AppCategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long catId;

    @ApiModelProperty(value = "父级分类id",notes = "如果为空，默认为0")
    private Long parentId;

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String catName;

    @ApiModelProperty(value = "分类状态")
    private String catStatus;

    @ApiModelProperty(value = "排序")
    private Integer catSort;

    @ApiModelProperty(value = "分类图标")
    private String catIcon;

    @ApiModelProperty(value = "分类缩略图")
    private String catPic;

    @ApiModelProperty(value = "分类级别")
    private Integer catLevel;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String createUser;

    private String updateUser;

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(String catStatus) {
        this.catStatus = catStatus;
    }

    public Integer getCatSort() {
        return catSort;
    }

    public void setCatSort(Integer catSort) {
        this.catSort = catSort;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }

    public String getCatPic() {
        return catPic;
    }

    public void setCatPic(String catPic) {
        this.catPic = catPic;
    }

    public Integer getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(Integer catLevel) {
        this.catLevel = catLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}