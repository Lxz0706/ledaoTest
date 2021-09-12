package com.ledao.activity.dao;

import com.ledao.common.annotation.Excel;

import java.util.Date;
import java.util.List;

public class SysApplyInImportDaily {
    @Excel(name = "档案类别")
    private String documentTypeLab;
    private String documentType;
    /** 公司名称 字典项 */
    @Excel(name = "公司名称")
    private String companyNameLab;
    private String companyName;
    @Excel(name = "实际提交人")
    private String applyUserLab;
    private String applyUser;
    @Excel(name = "代提交人")
    private String realCreateNameLab;
    private String realCreateName;
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;
    @Excel(name = "审批状态")
    private String approveStatuLab;
    private String approveStatu;
    @Excel(name = "备注")
    private String remark;
    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviserNameLab;
    private String reviserName;

    private List<SysApplyInImportFile> files;

    private List<SysApplyInImportFileDaily> filesDaily;

    public List<SysApplyInImportFileDaily> getFilesDaily() {
        return filesDaily;
    }

    public void setFilesDaily(List<SysApplyInImportFileDaily> filesDaily) {
        this.filesDaily = filesDaily;
    }

    public String getApplyUserLab() {
        return applyUserLab;
    }

    public void setApplyUserLab(String applyUserLab) {
        this.applyUserLab = applyUserLab;
    }

    public String getRealCreateNameLab() {
        return realCreateNameLab;
    }

    public void setRealCreateNameLab(String realCreateNameLab) {
        this.realCreateNameLab = realCreateNameLab;
    }

    public String getReviserNameLab() {
        return reviserNameLab;
    }

    public void setReviserNameLab(String reviserNameLab) {
        this.reviserNameLab = reviserNameLab;
    }

    public String getCompanyNameLab() {
        return companyNameLab;
    }

    public void setCompanyNameLab(String companyNameLab) {
        this.companyNameLab = companyNameLab;
    }

    public String getDocumentTypeLab() {
        return documentTypeLab;
    }

    public void setDocumentTypeLab(String documentTypeLab) {
        this.documentTypeLab = documentTypeLab;
    }

    public String getApproveStatuLab() {
        return approveStatuLab;
    }

    public void setApproveStatuLab(String approveStatuLab) {
        this.approveStatuLab = approveStatuLab;
    }

    public List<SysApplyInImportFile> getFiles() {
        return files;
    }

    public void setFiles(List<SysApplyInImportFile> files) {
        this.files = files;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getRealCreateName() {
        return realCreateName;
    }

    public void setRealCreateName(String realCreateName) {
        this.realCreateName = realCreateName;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApproveStatu() {
        return approveStatu;
    }

    public void setApproveStatu(String approveStatu) {
        this.approveStatu = approveStatu;
    }

    public String getReviserName() {
        return reviserName;
    }

    public void setReviserName(String reviserName) {
        this.reviserName = reviserName;
    }
}
