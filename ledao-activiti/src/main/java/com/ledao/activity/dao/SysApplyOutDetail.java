package com.ledao.activity.dao;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案出库详情记录对象 sys_apply_out_detail
 * 
 * @author lxz
 * @date 2021-08-10
 */
public class SysApplyOutDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long outDetailId;

    /** 资产编号 */
    @Excel(name = "资产编号")
    private String assetNumber;

    /** 资产编号 */
    @Excel(name = "资产包")
    private String assetPag;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 日常经营类档案类型 */
    @Excel(name = "档案类型")
    private String dailyDocumentType;


    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 附件类型--字典项 */
    @Excel(name = "附件类型--字典项")
    private String fileType;

    /** 扫描件类型--字典项 */
    @Excel(name = "扫描件类型--字典项")
    private String fileScanType;

    /** 份数 */
    @Excel(name = "份数")
    private Long counts;

    /** 页数 */
    @Excel(name = "页数")
    private Long pages;

    /** 申请id */
    @Excel(name = "申请id")
    private Long applyId;

    /** 档案id */
    @Excel(name = "档案id")
    private Long documentId;

    /** 借出是否包含字纸--字典项 */
    @Excel(name = "借出是否包含字纸--字典项")
    private String isPage;

    /** 借出是否包电子扫描件--字典项 */
    @Excel(name = "借出是否包电子扫描件--字典项")
    private String isElec;

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
    private String remarks;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;

    @Excel(name = "项目名称")
    private String projectName;

    /** 档案状态--字典项 */
    @Excel(name = "档案状态--字典项")
    private String documentStatu;

    /** 柜号 */
    @Excel(name = "柜号")
    private String cabinetNo;

    /** 档案袋编号 */
    @Excel(name = "档案袋编号")
    private String bagNo;

    /** 档案类型--字典项 */
    @Excel(name = "档案类型--字典项")
    private String documentGetType;

    /** 文件类型--字典项 */
    @Excel(name = "文件类型--字典项")
    private String fileGetType;

    /** 档案级别 */
    @Excel(name = "档案级别")
    private String documentLevel;

    private String companyName;

    private String dailyDocumentTypeContract;

    public String getDailyDocumentTypeContract() {
        return dailyDocumentTypeContract;
    }

    public void setDailyDocumentTypeContract(String dailyDocumentTypeContract) {
        this.dailyDocumentTypeContract = dailyDocumentTypeContract;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDocumentStatu() {
        return documentStatu;
    }

    public void setDocumentStatu(String documentStatu) {
        this.documentStatu = documentStatu;
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

    public String getDocumentGetType() {
        return documentGetType;
    }

    public void setDocumentGetType(String documentGetType) {
        this.documentGetType = documentGetType;
    }

    public String getFileGetType() {
        return fileGetType;
    }

    public void setFileGetType(String fileGetType) {
        this.fileGetType = fileGetType;
    }

    public String getDocumentLevel() {
        return documentLevel;
    }

    public void setDocumentLevel(String documentLevel) {
        this.documentLevel = documentLevel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetPag() {
        return assetPag;
    }

    public void setAssetPag(String assetPag) {
        this.assetPag = assetPag;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDailyDocumentType() {
        return dailyDocumentType;
    }

    public void setDailyDocumentType(String dailyDocumentType) {
        this.dailyDocumentType = dailyDocumentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileScanType() {
        return fileScanType;
    }

    public void setFileScanType(String fileScanType) {
        this.fileScanType = fileScanType;
    }

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public void setOutDetailId(Long outDetailId)
    {
        this.outDetailId = outDetailId;
    }

    public Long getOutDetailId() 
    {
        return outDetailId;
    }
    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }
    public void setDocumentId(Long documentId) 
    {
        this.documentId = documentId;
    }

    public Long getDocumentId() 
    {
        return documentId;
    }
    public void setIsPage(String isPage) 
    {
        this.isPage = isPage;
    }

    public String getIsPage() 
    {
        return isPage;
    }
    public void setIsElec(String isElec) 
    {
        this.isElec = isElec;
    }

    public String getIsElec() 
    {
        return isElec;
    }
    public void setIsReturn(String isReturn) 
    {
        this.isReturn = isReturn;
    }

    public String getIsReturn() 
    {
        return isReturn;
    }
    public void setPlanReturnTime(Date planReturnTime) 
    {
        this.planReturnTime = planReturnTime;
    }

    public Date getPlanReturnTime() 
    {
        return planReturnTime;
    }
    public void setRealReturnTime(Date realReturnTime) 
    {
        this.realReturnTime = realReturnTime;
    }

    public Date getRealReturnTime() 
    {
        return realReturnTime;
    }
    public void setOutReason(String outReason) 
    {
        this.outReason = outReason;
    }

    public String getOutReason() 
    {
        return outReason;
    }
    public void setIsOut(String isOut) 
    {
        this.isOut = isOut;
    }

    public String getIsOut() 
    {
        return isOut;
    }
    public void setIsReceive(String isReceive) 
    {
        this.isReceive = isReceive;
    }

    public String getIsReceive() 
    {
        return isReceive;
    }
    public void setIsReturned(String isReturned) 
    {
        this.isReturned = isReturned;
    }

    public String getIsReturned() 
    {
        return isReturned;
    }
    public void setIsReceived(String isReceived) 
    {
        this.isReceived = isReceived;
    }

    public String getIsReceived() 
    {
        return isReceived;
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
            .append("outDetailId", getOutDetailId())
            .append("assetNumber", getAssetNumber())
            .append("contractNo", getContractNo())
            .append("assetPag", getAssetPag())
            .append("dailyDocumentType", getDailyDocumentType())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
            .append("fileScanType", getFileScanType())
            .append("counts", getCounts())
            .append("pages", getPages())
            .append("applyId", getApplyId())
            .append("documentId", getDocumentId())
            .append("isPage", getIsPage())
            .append("isElec", getIsElec())
            .append("isReturn", getIsReturn())
            .append("planReturnTime", getPlanReturnTime())
            .append("realReturnTime", getRealReturnTime())
            .append("outReason", getOutReason())
            .append("isOut", getIsOut())
            .append("isReceive", getIsReceive())
            .append("isReturned", getIsReturned())
            .append("isReceived", getIsReceived())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("reviser", getReviser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
