package com.momo.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Chao.Cui.VWED on 2015/12/14.
 * 同步的jvap 域中用户信息
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DomainUser {

    @JsonProperty(value = "DT_RowId")
    private Long id;

    @JsonProperty(value = "sn")
    private String sn;

    @JsonProperty(value = "distinguishedName")
    private String distinguishedName;

    @JsonProperty(value = "sAMAccountName")
    private String sAMAccountName;

    @JsonProperty(value = "mail")
    private String mail;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "telephoneNumber")
    private String telephoneNumber;
    @JsonProperty(value = "whenCreated")
    private String whenCreated;
    @JsonProperty(value = "whenChanged")
    private String whenChanged;
    @JsonProperty(value = "proxyAddress")
    private String proxyAddress;
    @JsonProperty(value = "physicalDeliveryOfficeName")
    private String physicalDeliveryOfficeName;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "mailNickname")
    private String mailNickname;
    @JsonProperty(value = "givenName")
    private String givenName;
    @JsonProperty(value = "cn")
    private String cn;
    @JsonProperty(value = "company")
    private String company;
    @JsonProperty(value = "userAccountControl")
    private String userAccountControl;
    @JsonProperty(value = "msExchUserCulture")
    private String msExchUserCulture;
    @JsonProperty(value = "department")
    private String department;
    @JsonProperty(value = "displayName")
    private String displayName;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "userPrincipalName")
    private String userPrincipalName;
    @JsonProperty(value = "userRole")
    private String userRole;
    @JsonProperty(value = "userGroup")
    private String userGroup;

    @JsonProperty(value = "userDescript")
    private String userDescript;

    @JsonProperty(value = "objectGUID")
    private String objectGUID;

//    /*left join with table UserPermission*/
//    @JsonProperty(value = "permissionRole")
//    private String permissionRole;
//
//    @JsonProperty(value = "createTime")
//    private String createTime;
//
//    @JsonProperty(value = "updateTime")
//    private String updateTime;
//
//    @JsonProperty(value = "permissionDescript")
//    private String permissionDescript;

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getWhenChanged() {
        return whenChanged;
    }

    public void setWhenChanged(String whenChanged) {
        this.whenChanged = whenChanged;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public String getPhysicalDeliveryOfficeName() {
        return physicalDeliveryOfficeName;
    }

    public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
        this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailNickname() {
        return mailNickname;
    }

    public void setMailNickname(String mailNickname) {
        this.mailNickname = mailNickname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(String userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getMsExchUserCulture() {
        return msExchUserCulture;
    }

    public void setMsExchUserCulture(String msExchUserCulture) {
        this.msExchUserCulture = msExchUserCulture;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getObjectGUID() {
        return objectGUID;
    }

    public void setObjectGUID(String objectGUID) {
        this.objectGUID = objectGUID;
    }

    public String getUserDescript() {
        return userDescript;
    }

    public void setUserDescript(String userDescript) {
        this.userDescript = userDescript;
    }
}
