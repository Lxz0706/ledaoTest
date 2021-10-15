package com.ledao.activity.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 各个流程节点审批图对象 sys_workflow_process
 * 
 * @author lxz
 * @date 2021-10-13
 */
public class SysWorkflowProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long id;

    /** 申请id */
    @Excel(name = "申请id")
    private Long applyId;

    /** 审批人姓名 */
    @Excel(name = "审批人姓名")
    private String applyUserName;

    /** 审批人账号 */
    @Excel(name = "审批人账号")
    private String applyLoginName;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String applyStatu;

    /** 显示文字 */
    @Excel(name = "显示文字")
    private String showLable;

    /** 操作顺序 */
    @Excel(name = "操作顺序")
    private int sortOrder;

    private String nameEndStr;

    public String getNameEndStr() {
        return nameEndStr;
    }

    public void setNameEndStr(String nameEndStr) {
        this.nameEndStr = nameEndStr;
    }

    /** 审批时间 */
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    private String remark2;

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId()
    {
        return applyId;
    }
    public void setApplyUserName(String applyUserName) 
    {
        this.applyUserName = applyUserName;
    }

    public String getApplyUserName() 
    {
        return applyUserName;
    }
    public void setApplyLoginName(String applyLoginName) 
    {
        this.applyLoginName = applyLoginName;
    }

    public String getApplyLoginName() 
    {
        return applyLoginName;
    }
    public void setApplyStatu(String applyStatu) 
    {
        this.applyStatu = applyStatu;
    }

    public String getApplyStatu() 
    {
        return applyStatu;
    }
    public void setShowLable(String showLable) 
    {
        this.showLable = showLable;
    }

    public String getShowLable() 
    {
        return showLable;
    }

    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyId", getApplyId())
            .append("applyUserName", getApplyUserName())
            .append("applyLoginName", getApplyLoginName())
            .append("applyStatu", getApplyStatu())
            .append("showLable", getShowLable())
            .append("applyTime", getApplyTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
