package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 项目选择对象 sys_item
 *
 * @author lxz
 * @date 2020-12-02
 */
public class SysItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long itemId;

    /**
     * 主表id
     */
    private Long customerId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 客户标签
     */
    @Excel(name = "客户标签")
    private String customerLable;

    /**
     * 成交金额
     */
    @Excel(name = "成交金额")
    private Double gmv;

    /**
     * 成交日期
     */
    @Excel(name = "成交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tradeDate;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 共享人
     */
    private String shareUserId;

    /**
     * 共享人名称
     */
    private String shareUserName;

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setCustomerLable(String customerLable) {
        this.customerLable = customerLable;
    }

    public String getCustomerLable() {
        return customerLable;
    }

    public void setGmv(Double gmv) {
        this.gmv = gmv;
    }

    public Double getGmv() {
        return gmv;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
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
                .append("itemId", getItemId())
                .append("customerId", getCustomerId())
                .append("projectId", getProjectId())
                .append("customerLable", getCustomerLable())
                .append("gmv", getGmv())
                .append("tradeDate", getTradeDate())
                .append("remarks", getRemarks())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("projectName", getProjectName())
                .append("shareUserId", getShareUserId())
                .append("shareUserName", getShareUserName())
                .toString();
    }
}
