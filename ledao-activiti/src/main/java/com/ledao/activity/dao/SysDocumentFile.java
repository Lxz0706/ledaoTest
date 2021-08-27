package com.ledao.activity.dao;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案对象 sys_document_file
 * 
 * @author lxz
 * @date 2021-08-04
 */
public class SysDocumentFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long documentId;

    /** 档案种类，字典项：业务、日常经营 */
    @Excel(name = "档案种类，字典项：业务、日常经营")
    private String documentType;

    private String documentTypeVal;

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

    /** 文件id */
    @Excel(name = "文件id")
    private String fileIds;

    /** 档案入库id */
    @Excel(name = "档案入库id")
    private long applyId;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;
    @Excel(name = "创建人名称")
    private String creatorName;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;

    @Excel(name = "修改人名称")
    private String reviserName;

    /** 修改人名称 */
    @Excel(name = "附件名称")
    private String fileNames;

    @Excel(name = "项目名称")
    private String projectName;

    @Excel(name = "公司名称")
    private String companyName;

    @Excel(name = "债务人名称")
    private String debtorName;

    public String getDocumentTypeVal() {
        return documentTypeVal;
    }

    public void setDocumentTypeVal(String documentTypeVal) {
        this.documentTypeVal = documentTypeVal;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getReviserName() {
        return reviserName;
    }

    public void setReviserName(String reviserName) {
        this.reviserName = reviserName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    /**
     * 添加附件
     */
    private List<SysFileDetail> fileDetails;


    public String getDailyDocumentType() {
        return dailyDocumentType;
    }

    public void setDailyDocumentType(String dailyDocumentType) {
        this.dailyDocumentType = dailyDocumentType;
    }

    public String getAssetPag() {
        return assetPag;
    }

    public void setAssetPag(String assetPag) {
        this.assetPag = assetPag;
    }

    public void setDocumentId(Long documentId) 
    {
        this.documentId = documentId;
    }

    public Long getDocumentId() 
    {
        return documentId;
    }
    public void setDocumentType(String documentType) 
    {
        this.documentType = documentType;
    }

    public String getDocumentType() 
    {
        return documentType;
    }
    public void setAssetNumber(String assetNumber) 
    {
        this.assetNumber = assetNumber;
    }

    public String getAssetNumber() 
    {
        return assetNumber;
    }
    public void setContractNo(String contractNo) 
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }
    public void setFileScanType(String fileScanType) 
    {
        this.fileScanType = fileScanType;
    }

    public String getFileScanType() 
    {
        return fileScanType;
    }
    public void setCounts(Long counts) 
    {
        this.counts = counts;
    }

    public Long getCounts() 
    {
        return counts;
    }
    public void setPages(Long pages) 
    {
        this.pages = pages;
    }

    public Long getPages() 
    {
        return pages;
    }
    public void setDocumentStatu(String documentStatu) 
    {
        this.documentStatu = documentStatu;
    }

    public String getDocumentStatu() 
    {
        return documentStatu;
    }
    public void setCabinetNo(String cabinetNo) 
    {
        this.cabinetNo = cabinetNo;
    }

    public String getCabinetNo() 
    {
        return cabinetNo;
    }
    public void setBagNo(String bagNo) 
    {
        this.bagNo = bagNo;
    }

    public String getBagNo() 
    {
        return bagNo;
    }
    public void setDocumentGetType(String documentGetType) 
    {
        this.documentGetType = documentGetType;
    }

    public String getDocumentGetType() 
    {
        return documentGetType;
    }
    public void setFileGetType(String fileGetType) 
    {
        this.fileGetType = fileGetType;
    }

    public String getFileGetType() 
    {
        return fileGetType;
    }
    public void setDocumentLevel(String documentLevel) 
    {
        this.documentLevel = documentLevel;
    }

    public String getDocumentLevel() 
    {
        return documentLevel;
    }
    public void setFileIds(String fileIds) 
    {
        this.fileIds = fileIds;
    }

    public String getFileIds() 
    {
        return fileIds;
    }
    public void setApplyId(long applyId) 
    {
        this.applyId = applyId;
    }

    public long getApplyId() 
    {
        return applyId;
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
            .append("documentId", getDocumentId())
            .append("documentType", getDocumentType())
            .append("documentTypeVal", getDocumentTypeVal())
            .append("assetNumber", getAssetNumber())
            .append("contractNo", getContractNo())
            .append("assetPag", getAssetPag())
            .append("dailyDocumentType", getDailyDocumentType())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
            .append("fileScanType", getFileScanType())
            .append("counts", getCounts())
            .append("pages", getPages())
            .append("documentStatu", getDocumentStatu())
            .append("cabinetNo", getCabinetNo())
            .append("bagNo", getBagNo())
            .append("documentGetType", getDocumentGetType())
            .append("fileGetType", getFileGetType())
            .append("documentLevel", getDocumentLevel())
            .append("fileIds", getFileIds())
            .append("applyId", getApplyId())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("reviser", getReviser())
            .append("updateTime", getUpdateTime())
            .toString();
    }

	public List<SysFileDetail> getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(List<SysFileDetail> fileDetails) {
		this.fileDetails = fileDetails;
	}
}
