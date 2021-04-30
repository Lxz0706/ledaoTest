package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流转服务费对象 sys_cover_charge
 *
 * @author lxz
 * @date 2020-12-18
 */
public class SysCoverCharge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 主表ID
     */
    //@Excel(name = "主表ID")
    private Long projectManagementId;

    /**
     * 时间
     */
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paidDate;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amountPaid;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 资金类型
     */
    @Excel(name = "资金类型")
    private String fundType;

    /**
     * 图片路径
     */
    //@Excel(name = "图片路径")
    private String imgUrl;

    /**
     * 财务确认意见
     */
    @Excel(name = "财务确认意见")
    private String finance;

    private String state;

    private String cw;

    private String path;
    private String fileImgName;

    /**
     * 是否上传附件
     */
    private boolean imgFlag = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectManagementId() {
        return projectManagementId;
    }

    public void setProjectManagementId(Long projectManagementId) {
        this.projectManagementId = projectManagementId;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileImgName() {
        return fileImgName;
    }

    public void setFileImgName(String fileImgName) {
        this.fileImgName = fileImgName;
    }

    public boolean isImgFlag() {
        return imgFlag;
    }

    public void setImgFlag(boolean imgFlag) {
        this.imgFlag = imgFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectManagementId", getProjectManagementId())
                .append("paidDate", getPaidDate())
                .append("amountPaid", getAmountPaid())
                .append("remarks", getRemarks())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("fundType", getFundType())
                .append("imgUrl", getImgUrl())
                .append("finance", getFinance())
                .append("state", getState())
                .toString();
    }
}
