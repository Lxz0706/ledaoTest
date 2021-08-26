package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工信息对象 sys_staff
 *
 * @author lxz
 * @date 2021-06-23
 */
public class SysStaff extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    private Long staffId;

    /**
     * 员工名称
     */
    @Excel(name = "员工名称")
    private String staffName;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 入职日期
     */
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryDate;

    /**
     * 离职日期
     */
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveDate;

    /**
     * 联系方式1
     */
    @Excel(name = "联系方式1")
    private String contact1;

    /**
     * 联系方式2
     */
    @Excel(name = "联系方式2")
    private String contact2;

    /**
     * 所属部门id
     */
    @Excel(name = "所属部门id")
    private Long departmentId;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private String departmentName;

    /**
     * 学历
     */
    @Excel(name = "学历")
    private String education;

    /**
     * 员工状态
     */
    @Excel(name = "员工状态")
    private String status;

    /**
     * 删除标志（0 正常 2删除）
     */
    private String delFlag;

    /**
     * 学历人数
     */
    private Long educationCount;

    /**
     * 性别人数
     */
    public Long sexCount;

    /**
     * 核算入职日期
     */
    @Excel(name = "核算入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accounteEntryDate;

    /**
     * 司龄
     */
    @Excel(name = "司龄")
    private Long secretaryLing;

    /**
     * 司龄人数
     */
    private Long secretaryLingCount;

    /**
     * 简历地址
     */
    @Excel(name = "简历地址")
    private String resumeUrl;

    /**
     * 简历类型
     */
    private String resumeType;

    private Long fileCount;

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact2() {
        return contact2;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Long getEducationCount() {
        return educationCount;
    }

    public void setEducationCount(Long educationCount) {
        this.educationCount = educationCount;
    }

    public Long getSexCount() {
        return sexCount;
    }

    public void setSexCount(Long sexCount) {
        this.sexCount = sexCount;
    }

    public void setAccounteEntryDate(Date accounteEntryDate) {
        this.accounteEntryDate = accounteEntryDate;
    }

    public Date getAccounteEntryDate() {
        return accounteEntryDate;
    }

    public void setSecretaryLing(Long secretaryLing) {
        this.secretaryLing = secretaryLing;
    }

    public Long getSecretaryLing() {
        return secretaryLing;
    }

    public Long getSecretaryLingCount() {
        return secretaryLingCount;
    }

    public void setSecretaryLingCount(Long secretaryLingCount) {
        this.secretaryLingCount = secretaryLingCount;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getResumeType() {
        return resumeType;
    }

    public void setResumeType(String resumeType) {
        this.resumeType = resumeType;
    }

    public Long getFileCount() {
        return fileCount;
    }

    public void setFileCount(Long fileCount) {
        this.fileCount = fileCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("staffId", getStaffId())
                .append("staffName", getStaffName())
                .append("birthday", getBirthday())
                .append("sex", getSex())
                .append("entryDate", getEntryDate())
                .append("leaveDate", getLeaveDate())
                .append("contact1", getContact1())
                .append("contact2", getContact2())
                .append("departmentId", getDepartmentId())
                .append("departmentName", getDepartmentName())
                .append("education", getEducation())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("educationCount", getEducationCount())
                .append("sexCount", getSexCount())
                .append("accounteEntryDate", getAccounteEntryDate())
                .append("secretaryLing", getSecretaryLing())
                .append("secretaryLingCount", getSecretaryLingCount())
                .append("resumeUrl", getResumeUrl())
                .append("resumeType", getResumeType())
                .toString();
    }
}
