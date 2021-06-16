package com.ledao.activity.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 档案管理出入库流程对象 sys_workflow
 *
 * @author lxz
 * @date 2021-05-26
 */
public class SysWorkflow extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long workFlowId;

    /**
     * 流程类别
     */
    @Excel(name = "流程类别")
    private String workFlowType;

    /**
     * 所属部门id
     */
    @Excel(name = "所属部门id")
    private Long deptId;

    /**
     * 所属部门名称
     */
    @Excel(name = "所属部门名称")
    private String deptName;

    /**
     * 所属部门类别
     */
    @Excel(name = "所属部门类别")
    private String deptType;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private Long projectId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String corporateName;

    /**
     * 档案种类
     */
    @Excel(name = "档案种类")
    private String documentType;

    /**
     * 档案级别
     */
    @Excel(name = "档案级别")
    private String documentLevel;

    /**
     * 档案是否归还
     */
    @Excel(name = "档案是否归还")
    private String documentRevertFlag;

    /**
     * 档案归还时间
     */
    @Excel(name = "档案归还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date documentRevertTime;

    /**
     * 档案id
     */
    @Excel(name = "档案id")
    private Long documentId;

    /**
     * 档案名称
     */
    @Excel(name = "档案名称")
    private String documentName;

    /**
     * 档案路径
     */
    @Excel(name = "档案路径")
    private String documentUrl;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称")
    private String creator;

    /**
     * 修改人名称
     */
    @Excel(name = "修改人名称")
    private String reviser;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applyUser;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /**
     * 实际开始时间
     */
    @Excel(name = "实际开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityStartTime;

    /**
     * 实际结束时间
     */
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityEndTime;

    /**
     * 流程实例ID
     */
    @Excel(name = "流程实例ID")
    private String instanceId;

    private String referee;

    /**
     * 出库原因
     */
    @Excel(name = "出库原因")
    private String retrieval;

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowType(String workFlowType) {
        this.workFlowType = workFlowType;
    }

    public String getWorkFlowType() {
        return workFlowType;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentLevel(String documentLevel) {
        this.documentLevel = documentLevel;
    }

    public String getDocumentLevel() {
        return documentLevel;
    }

    public void setDocumentRevertFlag(String documentRevertFlag) {
        this.documentRevertFlag = documentRevertFlag;
    }

    public String getDocumentRevertFlag() {
        return documentRevertFlag;
    }

    public void setDocumentRevertTime(Date documentRevertTime) {
        this.documentRevertTime = documentRevertTime;
    }

    public Date getDocumentRevertTime() {
        return documentRevertTime;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
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

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setRealityStartTime(Date realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    public Date getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityEndTime(Date realityEndTime) {
        this.realityEndTime = realityEndTime;
    }

    public Date getRealityEndTime() {
        return realityEndTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getRetrieval() {
        return retrieval;
    }

    public void setRetrieval(String retrieval) {
        this.retrieval = retrieval;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workFlowId", getWorkFlowId())
                .append("workFlowType", getWorkFlowType())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("deptType", getDeptType())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("corporateName", getCorporateName())
                .append("documentType", getDocumentType())
                .append("documentLevel", getDocumentLevel())
                .append("documentRevertFlag", getDocumentRevertFlag())
                .append("documentRevertTime", getDocumentRevertTime())
                .append("documentId", getDocumentId())
                .append("documentName", getDocumentName())
                .append("documentUrl", getDocumentUrl())
                .append("remarks", getRemarks())
                .append("createBy", getCreateBy())
                .append("creator", getCreator())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("reviser", getReviser())
                .append("updateTime", getUpdateTime())
                .append("applyUser", getApplyUser())
                .append("applyTime", getApplyTime())
                .append("realityStartTime", getRealityStartTime())
                .append("realityEndTime", getRealityEndTime())
                .append("instanceId", getInstanceId())
                .append("referee", getReferee())
                .append("retrieval", getRetrieval())
                .toString();
    }
}
