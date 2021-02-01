package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户库对象 sys_customer
 *
 * @author lxz
 * @date 2020-11-18
 */
public class SysCustomer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 所属机构
     */
    @Excel(name = "所属机构")
    private String affiliation;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactNumber;

    /**
     * 职务
     */
    @Excel(name = "职务")
    private String duties;

    /**
     * 联系地址
     */
    @Excel(name = "联系地址")
    private String contactAddress;

    /**
     * 资源优势
     */
    @Excel(name = "资源优势")
    private String resources;

    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    private String city;

    /**
     * 县/区
     */
    @Excel(name = "县/区")
    private String county;

    /**
     * 删除标志（0 未删 2 删除）
     */
    private String delFlag;

    /*
     * 关联项目
     * */
    private String projectName;

    /**
     * 所属部门ID
     */
    //@Excel(name = "所属部门ID")
    private Long deptId;

    /**
     * 所属部门
     */
    //@Excel(name = "所属部门")
    private String deptName;

    /**
     * 是否添加微信
     */
    //@Excel(name = "是否添加微信")
    private String wechatFlag;

    /**
     * 原因
     */
    //@Excel(name = "原因")
    private String reason;


    /**
     * 微信号
     */
    //@Excel(name = "微信号")
    private String weChatNumber;

    //客户标签
    private String customerLable;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getDuties() {
        return duties;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getResources() {
        return resources;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWechatFlag() {
        return wechatFlag;
    }

    public void setWechatFlag(String wechatFlag) {
        this.wechatFlag = wechatFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setWeChatNumber(String weChatNumber) {
        this.weChatNumber = weChatNumber;
    }

    public String getWeChatNumber() {
        return weChatNumber;
    }

    public String getCustomerLable() {
        return customerLable;
    }

    public void setCustomerLable(String customerLable) {
        this.customerLable = customerLable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("customerId", getCustomerId())
                .append("customerName", getCustomerName())
                .append("affiliation", getAffiliation())
                .append("contacts", getContacts())
                .append("contactNumber", getContactNumber())
                .append("duties", getDuties())
                .append("contactAddress", getContactAddress())
                .append("resources", getResources())
                .append("province", getProvince())
                .append("city", getCity())
                .append("county", getCounty())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("wechatFlag", getWechatFlag())
                .append("reason", getReason())
                .append("weChatNumber", getWeChatNumber())
                .toString();
    }
}
