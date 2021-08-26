package com.ledao.activity.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案入库申请对象 sys_apply_in
 * 
 * @author lxz
 * @date 2021-08-09
 */
public class SysApplyIn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long applyId;

    /** 字典项  入库申请0，出库申请1 */
    @Excel(name = "字典项  入库申请0，出库申请1")
    private String applyType;

    /** 档案种类，字典项：业务、日常经营 */
    @Excel(name = "档案种类，字典项：业务、日常经营")
    private String documentType;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 公司名称 字典项 */
    @Excel(name = "公司名称 字典项")
    private String companyName;

    /** 债务人名称 */
    @Excel(name = "债务人名称")
    private String debtorName;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyUser;
    /** 申请人 */
    @Excel(name = "申请人姓名")
    private String applyUserName;


    /** 申请时间 */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 下一节点审批人 */
    @Excel(name = "下一节点审批人")
    private String approveUser;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String approveStatu;

    /** 实际提交人 */
    @Excel(name = "代提交人")
    private String realCreateBy;

    /** 实际提交人 */
    @Excel(name = "代提交人")
    private String realCreateName;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviserName;

    /** 是否归还--字典项 */
    @Excel(name = "是否归还--字典项")
    private String isReturn;

    /** 计划归还时间 */
    @Excel(name = "计划归还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planReturnTime;

    /** 实际归还时间 */
    @Excel(name = "实际归还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realReturnTime;

    /** 出库原因 */
    @Excel(name = "出库原因")
    private String outReason;

    /** 是否出库--字典项 */
    @Excel(name = "是否出库--字典项")
    private String isOut;

    /** 是否出库--字典项 */
    @Excel(name = "是否出库--字典项")
    private String isReceive;

    /** 是否已还--字典项 */
    @Excel(name = "是否已还--字典项")
    private String isReturned;

    /** 归还接受--字典项 */
    @Excel(name = "归还接受--字典项")
    private String isReceived;

    /** 备注 */
    @Excel(name = "备注")
    private String remarksOut;

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public Date getPlanReturnTime() {
        return planReturnTime;
    }

    public void setPlanReturnTime(Date planReturnTime) {
        this.planReturnTime = planReturnTime;
    }

    public Date getRealReturnTime() {
        return realReturnTime;
    }

    public void setRealReturnTime(Date realReturnTime) {
        this.realReturnTime = realReturnTime;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    public String getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(String isReceive) {
        this.isReceive = isReceive;
    }

    public String getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(String isReturned) {
        this.isReturned = isReturned;
    }

    public String getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(String isReceived) {
        this.isReceived = isReceived;
    }

    public String getRemarksOut() {
        return remarksOut;
    }

    public void setRemarksOut(String remarksOut) {
        this.remarksOut = remarksOut;
    }

    public String getRealCreateName() {
        return realCreateName;
    }

    public void setRealCreateName(String realCreateName) {
        this.realCreateName = realCreateName;
    }

    public String getReviserName() {
        return reviserName;
    }

    public void setReviserName(String reviserName) {
        this.reviserName = reviserName;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }
    
    private List<SysDocumentFile> documentFiles;

    public List<SysDocumentFile> getDocumentFiles() {
		return documentFiles;
	}

	public void setDocumentFiles(List<SysDocumentFile> documentFiles) {
		this.documentFiles = documentFiles;
	}

	public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }
    public void setApplyType(String applyType) 
    {
        this.applyType = applyType;
    }

    public String getApplyType() 
    {
        return applyType;
    }
    public void setDocumentType(String documentType) 
    {
        this.documentType = documentType;
    }

    public String getDocumentType() 
    {
        return documentType;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setDebtorName(String debtorName) 
    {
        this.debtorName = debtorName;
    }

    public String getDebtorName() 
    {
        return debtorName;
    }
    public void setApplyUser(String applyUser) 
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser() 
    {
        return applyUser;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
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
    public void setRealCreateBy(String realCreateBy) 
    {
        this.realCreateBy = realCreateBy;
    }

    public String getRealCreateBy() 
    {
        return realCreateBy;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setReviser(String reviser) 
    {
        this.reviser = reviser;
    }

    public String getReviser() 
    {
        return reviser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("applyId", getApplyId())
            .append("applyType", getApplyType())
            .append("documentType", getDocumentType())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("companyName", getCompanyName())
            .append("debtorName", getDebtorName())
            .append("applyUser", getApplyUser())
            .append("applyUserName", getApplyUserName())
            .append("applyTime", getApplyTime())
            .append("approveUser", getApproveUser())
            .append("approveStatu", getApproveStatu())
            .append("realCreateBy", getRealCreateBy())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("reviser", getReviser())
            .append("reviserName", getReviser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
