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

    private String[] customerLables;

    private String[] deptIds;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String creator;

    /**
     * 修改者
     */
    // @Excel(name = "修改者")
    private String reviser;

    private String beginTime;

    private String endTime;

    private String isAdmin;


    /**
     * 代理人ID
     */
    private String agentId;

    /**
     * 代理人
     */
    private String agent;

    /**
     * 是否匹配
     */
    private Boolean flag = false;

    /**
     * 共享人
     */
    private String shareUserId;

    /**
     * 共享人名称
     */
    private String shareUserName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 地区总数
     */
    private Long cityCount;

    /**
     * 客户标签总数
     */
    private Long customerLableCount;

    private String parentDeptName;

    private String deptType;

    private Long weChatNum;

    private String shareholder;

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

    public String[] getCustomerLables() {
        return customerLables;
    }

    public void setCustomerLables(String[] customerLables) {
        this.customerLables = customerLables;
    }

    public String[] getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String[] deptIds) {
        this.deptIds = deptIds;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    public String getReviser() {
        return reviser;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getShareUserName() {
        return shareUserName;
    }

    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCityCount() {
        return cityCount;
    }

    public void setCityCount(Long cityCount) {
        this.cityCount = cityCount;
    }

    public Long getCustomerLableCount() {
        return customerLableCount;
    }

    public void setCustomerLableCount(Long customerLableCount) {
        this.customerLableCount = customerLableCount;
    }

    public String getParentDeptName() {
        return parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public Long getWeChatNum() {
        return weChatNum;
    }

    public void setWeChatNum(Long weChatNum) {
        this.weChatNum = weChatNum;
    }

    public String getShareholder() {
        return shareholder;
    }

    public void setShareholder(String shareholder) {
        this.shareholder = shareholder;
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
                .append("customerLables", getCustomerLables())
                .append("customerLable", getCustomerLable())
                .append("creator", getCreator())
                .append("reviser", getReviser())
                .append("agent", getAgent())
                .append("agentId", getAgentId())
                .append("customerLable", getCustomerLable())
                .append("shareUserId", getShareUserId())
                .append("shareUserName", getShareUserName())
                .append("remarks", getRemarks())
                .append("cityCount", getCityCount())
                .append("customerLableCount", getCustomerLableCount())
                .append("deptType", getDeptType())
                .toString();
    }
}
