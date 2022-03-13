package com.mohai.one.app.wares.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AppProd implements Serializable {
    private Long proId;

    private String proName;

    private String proType;

    private BigDecimal proOriprice;

    private BigDecimal proPrice;

    private String proBrief;

    private String proPic;

    private Integer proSoldNum;

    private Integer proTotalStocks;

    private String proStatus;

    private Date proPutawayTime;

    private Long categoryId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String proContent;

    private static final long serialVersionUID = 1L;

    public AppProd(Long proId, String proName, String proType, BigDecimal proOriprice, BigDecimal proPrice, String proBrief, String proPic, Integer proSoldNum, Integer proTotalStocks, String proStatus, Date proPutawayTime, Long categoryId, Date createTime, String createUser, Date updateTime, String updateUser) {
        this.proId = proId;
        this.proName = proName;
        this.proType = proType;
        this.proOriprice = proOriprice;
        this.proPrice = proPrice;
        this.proBrief = proBrief;
        this.proPic = proPic;
        this.proSoldNum = proSoldNum;
        this.proTotalStocks = proTotalStocks;
        this.proStatus = proStatus;
        this.proPutawayTime = proPutawayTime;
        this.categoryId = categoryId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public AppProd(Long proId, String proName, String proType, BigDecimal proOriprice, BigDecimal proPrice, String proBrief, String proPic, Integer proSoldNum, Integer proTotalStocks, String proStatus, Date proPutawayTime, Long categoryId, Date createTime, String createUser, Date updateTime, String updateUser, String proContent) {
        this.proId = proId;
        this.proName = proName;
        this.proType = proType;
        this.proOriprice = proOriprice;
        this.proPrice = proPrice;
        this.proBrief = proBrief;
        this.proPic = proPic;
        this.proSoldNum = proSoldNum;
        this.proTotalStocks = proTotalStocks;
        this.proStatus = proStatus;
        this.proPutawayTime = proPutawayTime;
        this.categoryId = categoryId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.proContent = proContent;
    }

    public AppProd() {
        super();
    }

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
        this.proName = proName == null ? null : proName.trim();
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
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
        this.proBrief = proBrief == null ? null : proBrief.trim();
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic == null ? null : proPic.trim();
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
        this.proStatus = proStatus == null ? null : proStatus.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getProContent() {
        return proContent;
    }

    public void setProContent(String proContent) {
        this.proContent = proContent == null ? null : proContent.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", proId=").append(proId);
        sb.append(", proName=").append(proName);
        sb.append(", proType=").append(proType);
        sb.append(", proOriprice=").append(proOriprice);
        sb.append(", proPrice=").append(proPrice);
        sb.append(", proBrief=").append(proBrief);
        sb.append(", proPic=").append(proPic);
        sb.append(", proSoldNum=").append(proSoldNum);
        sb.append(", proTotalStocks=").append(proTotalStocks);
        sb.append(", proStatus=").append(proStatus);
        sb.append(", proPutawayTime=").append(proPutawayTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", proContent=").append(proContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}