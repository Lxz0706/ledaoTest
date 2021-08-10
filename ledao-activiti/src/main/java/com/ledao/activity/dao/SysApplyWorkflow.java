package com.ledao.activity.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案出入库审批流程对象 sys_apply_workflow
 * 
 * @author lxz
 * @date 2021-08-06
 */
public class SysApplyWorkflow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long workflowId;

    /** 申请id */
    @Excel(name = "申请id")
    private Long applyId;

    /** 审批人 */
    @Excel(name = "审批人")
    private String approveUser;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String approveStatu;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setWorkflowId(Long workflowId) 
    {
        this.workflowId = workflowId;
    }

    public Long getWorkflowId() 
    {
        return workflowId;
    }
    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }
    public void setApproveUser(String approveUser) 
    {
        this.approveUser = approveUser;
    }

    public String getApproveUser() 
    {
        return approveUser;
    }
    public void setApproveStatu(String approveStatu) 
    {
        this.approveStatu = approveStatu;
    }

    public String getApproveStatu() 
    {
        return approveStatu;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workflowId", getWorkflowId())
            .append("applyId", getApplyId())
            .append("approveUser", getApproveUser())
            .append("approveStatu", getApproveStatu())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
