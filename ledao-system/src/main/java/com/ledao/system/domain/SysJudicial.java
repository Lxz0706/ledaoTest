package com.ledao.system.domain;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 司法对象 sys_judicial
 *
 * @author lxz
 * @date 2020-06-09
 */
public class SysJudicial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 所有人
     */
    @Excel(name = "所有人")
    private String owner;

    /**
     * 所有人类型
     */
    @Excel(name = "所有人类型")
    private String ownertype;

    /**
     * 网站链接
     */
    @Excel(name = "网站链接")
    private String websiteLinks;

    /**
     * 起拍价
     */
    @Excel(name = "起拍价")
    private String startPrice;

    /**
     * 评估价
     */
    @Excel(name = "评估价")
    private String appraisalPrice;

    /**
     * 当前/成交价
     */
    @Excel(name = "当前/成交价")
    private String finalPrice;

    /**
     * 表中数据
     */
    @Excel(name = "表中数据")
    private String tableData;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date endTime;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwnertype(String ownertype) {
        this.ownertype = ownertype;
    }

    public String getOwnertype() {
        return ownertype;
    }

    public void setWebsiteLinks(String websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public String getWebsiteLinks() {
        return websiteLinks;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getAppraisalPrice() {
        return appraisalPrice;
    }

    public void setAppraisalPrice(String appraisalPrice) {
        this.appraisalPrice = appraisalPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getTableData() {
        return tableData;
    }

    public void setTableData(String tableData) {
        this.tableData = tableData;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("owner", getOwner())
                .append("ownertype", getOwnertype())
                .append("websiteLinks", getWebsiteLinks())
                .append("startPrice", getStartPrice())
                .append("appraisalPrice", getAppraisalPrice())
                .append("finalPrice", getFinalPrice())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("status", getStatus())
                .toString();
    }
}
