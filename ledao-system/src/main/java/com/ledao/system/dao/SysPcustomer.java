package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 项目客户对象 sys_pcustomer
 *
 * @author lxz
 * @date 2021-02-02
 */
public class SysPcustomer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long dealCustomerId;

    /**
     * 客户id
     */
    @Excel(name = "客户id")
    private String customerId;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户标签
     */
    @Excel(name = "客户标签")
    private String customerLable;

    /**
     * 部门类型
     */
    @Excel(name = "部门类型")
    private String deptType;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private Long projectId;

    /**
     * 删除标志（默认 0 删除2）
     */
    private String delFlag;
    /**
     * 合作项目id
     */
    @Excel(name = "合作项目id")
    private Long itemId;

    /**
     * 共享人
     */
    private String shareUserId;

    /**
     * 共享人名称
     */
    private String shareUserName;

    public Long getDealCustomerId() {
        return dealCustomerId;
    }

    public void setDealCustomerId(Long dealCustomerId) {
        this.dealCustomerId = dealCustomerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLable() {
        return customerLable;
    }

    public void setCustomerLable(String customerLable) {
        this.customerLable = customerLable;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dealCustomerId", getDealCustomerId())
                .append("customerId", getCustomerId())
                .append("customerName", getCustomerName())
                .append("customerLable", getCustomerLable())
                .append("deptType", getDeptType())
                .append("projectId", getProjectId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("itemId", getItemId())
                .append("shareUserId", getShareUserId())
                .append("shareUserName", getShareUserName())
                .toString();
    }
}
