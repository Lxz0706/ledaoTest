package com.ledao.activity.dao;

import com.ledao.common.annotation.Excel;

import java.util.Date;
import java.util.List;

public class SysApplyInImport {
    @Excel(name = "序号")
    private long no;
    @Excel(name = "档案类别")
    private String documentTypeLab;
    private String documentType;
    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;
    @Excel(name = "部门")
    private String depName;
    /** 公司名称 字典项 */
    @Excel(name = "公司名称")
    private String companyNameLab;
    private String companyName;
    /** 债务人名称 */
    @Excel(name = "债务人名称")
    private String debtorName;
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

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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
