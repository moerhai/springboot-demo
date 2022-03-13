package com.mohai.one.app.wares.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 22:48
 */
@ApiModel(value= "商品信息")
public class AppProdDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long proId;

    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String proName;

    @ApiModelProperty(value = "商品类型")
    private String proType;

    @ApiModelProperty(value = "商品原价")
    @NotNull(message = "原价必填")
    private BigDecimal proOriprice;

    @ApiModelProperty(value = "商品现价")
    @NotNull(message = "商品价格必填")
    private BigDecimal proPrice;

    @ApiModelProperty(value = "商品简介")
    private String proBrief;

    @ApiModelProperty(value = "商品缩略图")
    private String proPic;

    @ApiModelProperty(value = "商品销量")
    @NotNull(message = "销量必填")
    @Min(value = 0)
    private Integer proSoldNum;

    @ApiModelProperty(value = "商品库存")
    @NotNull(message = "库存必填")
    @Min(value = 0)
    private Integer proTotalStocks;

    @ApiModelProperty(value = "商品状态")
    private String proStatus;

    @ApiModelProperty(value = "商品发布时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date proPutawayTime;

    @ApiModelProperty(value = "商品分类id")
    private Long categoryId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String updateUser;

    private String proContent;


    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public BigDecimal getProOriprice() {
        return proOriprice;
    }

    public void setProOriprice(BigDecimal proOriprice) {
        this.proOriprice = proOriprice;
    }

    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    public String getProBrief() {
        return proBrief;
    }

    public void setProBrief(String proBrief) {
        this.proBrief = proBrief;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public Integer getProSoldNum() {
        return proSoldNum;
    }

    public void setProSoldNum(Integer proSoldNum) {
        this.proSoldNum = proSoldNum;
    }

    public Integer getProTotalStocks() {
        return proTotalStocks;
    }

    public void setProTotalStocks(Integer proTotalStocks) {
        this.proTotalStocks = proTotalStocks;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public Date getProPutawayTime() {
        return proPutawayTime;
    }

    public void setProPutawayTime(Date proPutawayTime) {
        this.proPutawayTime = proPutawayTime;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getProContent() {
        return proContent;
    }

    public void setProContent(String proContent) {
        this.proContent = proContent;
    }
}