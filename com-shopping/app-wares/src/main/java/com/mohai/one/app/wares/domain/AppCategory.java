package com.mohai.one.app.wares.domain;

import java.io.Serializable;
import java.util.Date;

public class AppCategory implements Serializable {
    private Long catId;

    private Long parentId;

    private String catName;

    private String catStatus;

    private Integer catSort;

    private String catIcon;

    private String catPic;

    private Integer catLevel;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public AppCategory(Long catId, Long parentId, String catName, String catStatus, Integer catSort, String catIcon, String catPic, Integer catLevel, Date createTime, Date updateTime, String createUser, String updateUser) {
        this.catId = catId;
        this.parentId = parentId;
        this.catName = catName;
        this.catStatus = catStatus;
        this.catSort = catSort;
        this.catIcon = catIcon;
        this.catPic = catPic;
        this.catLevel = catLevel;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

    public AppCategory() {
        super();
    }

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
        this.catName = catName == null ? null : catName.trim();
    }

    public String getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(String catStatus) {
        this.catStatus = catStatus == null ? null : catStatus.trim();
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
        this.catIcon = catIcon == null ? null : catIcon.trim();
    }

    public String getCatPic() {
        return catPic;
    }

    public void setCatPic(String catPic) {
        this.catPic = catPic == null ? null : catPic.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", catId=").append(catId);
        sb.append(", parentId=").append(parentId);
        sb.append(", catName=").append(catName);
        sb.append(", catStatus=").append(catStatus);
        sb.append(", catSort=").append(catSort);
        sb.append(", catIcon=").append(catIcon);
        sb.append(", catPic=").append(catPic);
        sb.append(", catLevel=").append(catLevel);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}