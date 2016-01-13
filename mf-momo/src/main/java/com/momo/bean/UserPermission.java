package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 记录用户的权限信息，单独存放一表
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserPermission {
    @JsonProperty(value = "DT_RowId")
    private Long id;

    @JsonProperty(value = "distinguishedName")
    private String distinguishedName;

    @JsonProperty(value = "permissionRole")
    private String permissionRole;

    @JsonProperty(value = "createTime")
    private String createTime;

    @JsonProperty(value = "updateTime")
    private String updateTime;

    @JsonProperty(value = "permissionDescript")
    private String permissionDescript;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPermissionRole() {
        return permissionRole;
    }

    public void setPermissionRole(String permissionRole) {
        this.permissionRole = permissionRole;
    }

    public String getPermissionDescript() {
        return permissionDescript;
    }

    public void setPermissionDescript(String permissionDescript) {
        this.permissionDescript = permissionDescript;
    }
}

