package com.ledao.activity.dao;

import com.ledao.common.annotation.Excel;

import java.util.Date;

public class SysApplyInImportFileDaily {
    @Excel(name = "序号")
    private long no;

    @Excel(name = "种类")
    private String dailyDocumentTypeLab;
    private String dailyDocumentType;

    @Excel(name = "公司名称")
    private String companyNameLab;
    private String companyName;

    @Excel(name = "文件名称")
    private String fileName;

    @Excel(name = "附件类型")
    private String fileTypeLab;
    private String fileType;

    @Excel(name = "扫描件类型")
    private String fileScanTypeLab;
    private String fileScanType;

    @Excel(name = "份数")
    private Long counts;
    @Excel(name = "页数/时长")
    private Long pages;

    @Excel(name = "档案状态")
    private String documentStatuLab;
    private String documentStatu;

    @Excel(name = "柜号")
    private String cabinetNo;
    @Excel(name = "档案袋编号")
    private String bagNo;

    @Excel(name = "档案类型")
    private String busiDocumentTypeLab;
    private String busiDocumentType;


    @Excel(name = "文件类型")
    private String fileGetTypeLab;
    private String fileGetType;

    @Excel(name = "档案级别")
    private String documentLevelLab;
    private String documentLevel;

    @Excel(name = "附件路径")
    private String fileUrl;
    @Excel(name = "文件后缀")
    private String fileDetailType;
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getBusiDocumentTypeLab() {
        return busiDocumentTypeLab;
    }

    public void setBusiDocumentTypeLab(String busiDocumentTypeLab) {
        this.busiDocumentTypeLab = busiDocumentTypeLab;
    }

    public String getBusiDocumentType() {
        return busiDocumentType;
    }

    public void setBusiDocumentType(String busiDocumentType) {
        this.busiDocumentType = busiDocumentType;
    }

    public String getCompanyNameLab() {
        return companyNameLab;
    }

    public void setCompanyNameLab(String companyNameLab) {
        this.companyNameLab = companyNameLab;
    }

    public String getFileTypeLab() {
        return fileTypeLab;
    }

    public void setFileTypeLab(String fileTypeLab) {
        this.fileTypeLab = fileTypeLab;
    }

    public String getFileScanTypeLab() {
        return fileScanTypeLab;
    }

    public void setFileScanTypeLab(String fileScanTypeLab) {
        this.fileScanTypeLab = fileScanTypeLab;
    }

    public String getDocumentStatuLab() {
        return documentStatuLab;
    }

    public void setDocumentStatuLab(String documentStatuLab) {
        this.documentStatuLab = documentStatuLab;
    }

    public String getDailyDocumentTypeLab() {
        return dailyDocumentTypeLab;
    }

    public void setDailyDocumentTypeLab(String dailyDocumentTypeLab) {
        this.dailyDocumentTypeLab = dailyDocumentTypeLab;
    }

    public String getFileGetTypeLab() {
        return fileGetTypeLab;
    }

    public void setFileGetTypeLab(String fileGetTypeLab) {
        this.fileGetTypeLab = fileGetTypeLab;
    }

    public String getDocumentLevelLab() {
        return documentLevelLab;
    }

    public void setDocumentLevelLab(String documentLevelLab) {
        this.documentLevelLab = documentLevelLab;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getDailyDocumentType() {
        return dailyDocumentType;
    }

    public void setDailyDocumentType(String dailyDocumentType) {
        this.dailyDocumentType = dailyDocumentType;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileDetailType() {
        return fileDetailType;
    }

    public void setFileDetailType(String fileDetailType) {
        this.fileDetailType = fileDetailType;
    }
}
