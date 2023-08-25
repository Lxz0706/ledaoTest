package com.ledao.activity.dao;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.ledao.system.dao.read.ApplyTypeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案入库申请对象 sys_apply_in
 *
 * @author lxz
 * @date 2021-08-09
 */
@ExcelIgnoreUnannotated
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class SysApplyIn extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long applyId;

    /**
     * 字典项  入库申请0，出库申请1
     */
    private String applyType;
    @ExcelProperty(value = "入库/出库")
    @ColumnWidth(16)
    private String applyTypeToString;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    @ColumnWidth(value = 20)
    private String projectName;

    /**
     * 公司名称 字典项
     */
    private String companyName;

    @ExcelProperty(value = "公司名称")
    @ColumnWidth(value = 20)
    private String companyNameToString;

    /**
     * 债务人名称
     */
    @ExcelProperty(value = "债务人名字")
    @ColumnWidth(value = 20)
    private String debtorName;

    /**
     * 申请人
     */
    @ExcelProperty(value = "实际提交人")
    private String applyUserName;

    /**
     * 实际提交人
     */
    @ExcelProperty(value = "代提交人")
    private String realCreateName;

    /**
     * 申请时间
     */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "申请时间")
    private Date applyTime;

    @ExcelProperty(value = "审批状态")
    private String approveStatuToString;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remarks;

    /**
     * 修改人名称
     */
    @ExcelProperty(value = "修改人名称")
    private String reviserName;


    /**
     * 档案种类，字典项：业务、日常经营
     */

    private String documentType;

    //@ExcelProperty(value = "档案类别")
    private String documentTypeToString;

    /**
     * 项目id
     */
    //@ExcelProperty(value = "项目id")
    private Long projectId;


    /**
     * 债务人名称
     */
    //@ExcelProperty(value = "债务人id")
    private String debtorId;

    /**
     * 申请人
     */
    //@ExcelProperty(value = "申请人")
    private String applyUser;


    /**
     * 下一节点审批人
     */
    //@ExcelProperty(value = "下一节点审批人")
    private String approveUser;

    /**
     * 审批状态
     */
    private String approveStatu;


    /**
     * 实际提交人
     */
    private String realCreateBy;


    /**
     * 创建人名称
     */
    //@ExcelProperty(value = "创建人名称")
    private String creator;

    /**
     * 修改人名称
     */
    //@ExcelProperty(value = "修改人名称")
    private String reviser;

    /**
     * 是否归还--字典项
     */
    //@ExcelProperty(value = "是否归还--字典项")
    private String isReturn;

    /**
     * 计划归还时间
     */
    //@DateTimeFormat("yyyy-MM-dd")
    //@ExcelProperty(value = "计划归还时间")
    private Date planReturnTime;

    /**
     * 实际归还时间
     */
    //@DateTimeFormat("yyyy-MM-dd")
    //@ExcelProperty(value = "实际归还时间")
    private Date realReturnTime;

    /**
     * 出库原因
     */
    //@ExcelProperty(value = "出库原因")
    private String outReason;

    /**
     * 出库原因
     */
    //@ExcelProperty(value = "流程id")
    private String instanceId;

    /**
     * 是否出库--字典项
     */
    //@ExcelProperty(value = "是否出库--字典项")
    private String isOut;

    /**
     * 是否出库--字典项
     */
    //@ExcelProperty(value = "是否出库--字典项")
    private String isReceive;

    /**
     * 是否已还--字典项
     */
    //@ExcelProperty(value = "是否已还--字典项")
    private String isReturned;

    /**
     * 归还接受--字典项
     */
    //@ExcelProperty(value = "归还接受--字典项")
    private String isReceived;

    /**
     * 备注
     */
    //@ExcelProperty(value = "备注")
    private String remarksOut;

    //@ExcelProperty(value = "档案角色")
    private String roleType;

    //@ExcelProperty(value = "任务id")
    private String taskId;

    private String projectZckType;

    private long documentId;

    private String inType;

    private String fileName;

    /**
     * 是否直属主管
     */
    private String isDirector;

    /**
     * 柜号
     */
    private String cabinetNo;

    /**
     * 档案袋编号
     */
    private String bagNo;

    /**
     * 档案状态--字典项
     */
    private String documentStatu;

    /**
     * 附件类型--字典项
     */
    private String fileType;

    public String getApplyTypeToString() {
        return applyTypeToString;
    }

    public void setApplyTypeToString(String applyTypeToString) {
        this.applyTypeToString = applyTypeToString;
    }

    public String getDocumentTypeToString() {
        return documentTypeToString;
    }

    public void setDocumentTypeToString(String documentTypeToString) {
        this.documentTypeToString = documentTypeToString;
    }

    public String getCompanyNameToString() {
        return companyNameToString;
    }

    public void setCompanyNameToString(String companyNameToString) {
        this.companyNameToString = companyNameToString;
    }

    public String getIsDirector() {
        return isDirector;
    }

    public void setIsDirector(String isDirector) {
        this.isDirector = isDirector;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getProjectZckType() {
        return projectZckType;
    }

    public void setProjectZckType(String projectZckType) {
        this.projectZckType = projectZckType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(String debtorId) {
        this.debtorId = debtorId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

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

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorName() {
        return debtorName;
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

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveStatu(String approveStatu) {
        this.approveStatu = approveStatu;
    }

    public String getApproveStatu() {
        return approveStatu;
    }

    public void setRealCreateBy(String realCreateBy) {
        this.realCreateBy = realCreateBy;
    }

    public String getRealCreateBy() {
        return realCreateBy;
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

    public String getApproveStatuToString() {
        return approveStatuToString;
    }

    public void setApproveStatuToString(String approveStatuToString) {
        this.approveStatuToString = approveStatuToString;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }

    public String getBagNo() {
        return bagNo;
    }

    public void setBagNo(String bagNo) {
        this.bagNo = bagNo;
    }

    public String getDocumentStatu() {
        return documentStatu;
    }

    public void setDocumentStatu(String documentStatu) {
        this.documentStatu = documentStatu;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("applyId", getApplyId())
                .append("applyType", getApplyType())
                .append("documentType", getDocumentType())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("companyName", getCompanyName())
                .append("debtorName", getDebtorName())
                .append("roleType", getRoleType())
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
                .append("cabinetNo", getCabinetNo())
                .append("bagNo", getBagNo())
                .append("documentStatu", getDocumentStatu())
                .append("fileType", getFileType())
                .toString();
    }
}
