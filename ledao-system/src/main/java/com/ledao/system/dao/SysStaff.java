package com.ledao.system.dao;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import com.ledao.system.dao.read.OperTypeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 员工信息对象 sys_staff
 *
 * @author lxz
 * @date 2024-03-13
 */

@ExcelIgnoreUnannotated
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class SysStaff extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    private Long staffId;

    /**
     * 所属部门id
     */
    private Long departmentId;

    /**
     * 所属部门
     */
    @ExcelProperty(value = "所属部门")
    @ColumnWidth(16)
    private String departmentName;

    /**
     * 员工姓名
     */
    @ExcelProperty(value = "员工姓名")
    @ColumnWidth(20)
    private String staffName;

    /**
     * 职位
     */
    @ExcelProperty(value = "职位")
    @ColumnWidth(15)
    private String position;

    /**
     * 劳动关系
     */
    @ExcelProperty(value = "劳动关系")
    @ColumnWidth(16)
    private String laborRelations;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    @ColumnWidth(15)
    private String sex;

    /**
     * 入职日期
     */
    @ExcelProperty(value = "入职日期")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date entryDate;

    /**
     * 司龄
     */
    @ExcelProperty(value = "司龄")
    @ColumnWidth(15)
    private Long secretaryLing;

    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌")
    @ColumnWidth(15)
    private String politicalOutlook;

    /**
     * 联系方式1
     */
    @ExcelProperty(value = "联系方式1")
    @ColumnWidth(20)
    private String contact1;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date birthday;
    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    @ColumnWidth(15)
    private Long age;

    /**
     * 身份证号码
     */
    @ExcelProperty(value = "身份证号码")
    @ColumnWidth(30)
    private String idNo;

    /**
     * 原籍
     */
    @ExcelProperty(value = "原籍")
    @ColumnWidth(30)
    private String origin;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历")
    @ColumnWidth(15)
    private String education;

    /**
     * 专业
     */
    @ExcelProperty(value = "专业")
    @ColumnWidth(25)
    private String speciality;

    /**
     * 婚育
     */
    @ExcelProperty(value = "婚育")
    @ColumnWidth(20)
    private String marriage;

    /**
     * 民族
     */
    @ExcelProperty(value = "民族")
    @ColumnWidth(15)
    private String nation;

    /**
     * 公司履历
     */
    @ExcelProperty(value = "公司履历")
    @ColumnWidth(50)
    private String companyResume;

    /**
     * 住址
     */
    @ExcelProperty(value = "住址")
    @ColumnWidth(50)
    private String address;

    /**
     * 紧急联系人
     */
    @ExcelProperty(value = "紧急联系人")
    @ColumnWidth(20)
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @ExcelProperty(value = "紧急联系人电话")
    @ColumnWidth(20)
    private String emergencyContactPhone;

    /**
     * 毕业院校
     */
    @ExcelProperty(value = "毕业院校")
    @ColumnWidth(20)
    private String graduationInstitution;

    /**
     * 删除标志（0 正常 2删除）
     */
    private String delFlag;

    /**
     * 员工状态
     */
    @ExcelProperty(value = "员工状态")
    @ColumnWidth(15)
    private String status;

    /**
     * 离职日期
     */
    @ExcelProperty(value = "离职日期")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date leaveDate;

    /**
     * 联系方式2
     */
    @ExcelProperty(value = "联系方式2")
    @ColumnWidth(20)
    private String contact2;

    /**
     * 核算入职日期
     */
    @ExcelProperty(value = "核算入职日期")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date accounteEntryDate;

    /**
     * 简历路径
     */
    @ExcelProperty(value = "简历路径")
    @ColumnWidth(50)
    private String resumeUrl;

    /**
     * 简历类型
     */
    //@ExcelProperty(value =  "简历类型")
    private String resumeType;

    private Long fileCount;

    /**
     * 性别人数
     */
    public Long sexCount;
    /**
     * 司龄人数
     */
    private Long secretaryLingCount;
    /**
     * 学历人数
     */
    private Long educationCount;

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
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

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setLaborRelations(String laborRelations) {
        this.laborRelations = laborRelations;
    }

    public String getLaborRelations() {
        return laborRelations;
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

    public void setSecretaryLing(Long secretaryLing) {
        this.secretaryLing = secretaryLing;
    }

    public Long getSecretaryLing() {
        return secretaryLing;
    }

    public void setPoliticalOutlook(String politicalOutlook) {
        this.politicalOutlook = politicalOutlook;
    }

    public String getPoliticalOutlook() {
        return politicalOutlook;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact1() {
        return contact1;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setCompanyResume(String companyResume) {
        this.companyResume = companyResume;
    }

    public String getCompanyResume() {
        return companyResume;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setGraduationInstitution(String graduationInstitution) {
        this.graduationInstitution = graduationInstitution;
    }

    public String getGraduationInstitution() {
        return graduationInstitution;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact2() {
        return contact2;
    }

    public void setAccounteEntryDate(Date accounteEntryDate) {
        this.accounteEntryDate = accounteEntryDate;
    }

    public Date getAccounteEntryDate() {
        return accounteEntryDate;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeType(String resumeType) {
        this.resumeType = resumeType;
    }

    public String getResumeType() {
        return resumeType;
    }

    public Long getFileCount() {
        return fileCount;
    }

    public void setFileCount(Long fileCount) {
        this.fileCount = fileCount;
    }

    public Long getSexCount() {
        return sexCount;
    }

    public void setSexCount(Long sexCount) {
        this.sexCount = sexCount;
    }

    public Long getSecretaryLingCount() {
        return secretaryLingCount;
    }

    public void setSecretaryLingCount(Long secretaryLingCount) {
        this.secretaryLingCount = secretaryLingCount;
    }

    public Long getEducationCount() {
        return educationCount;
    }

    public void setEducationCount(Long educationCount) {
        this.educationCount = educationCount;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("staffId", getStaffId())
                .append("departmentId", getDepartmentId())
                .append("departmentName", getDepartmentName())
                .append("staffName", getStaffName())
                .append("position", getPosition())
                .append("laborRelations", getLaborRelations())
                .append("sex", getSex())
                .append("entryDate", getEntryDate())
                .append("secretaryLing", getSecretaryLing())
                .append("politicalOutlook", getPoliticalOutlook())
                .append("contact1", getContact1())
                .append("birthday", getBirthday())
                .append("idNo", getIdNo())
                .append("origin", getOrigin())
                .append("education", getEducation())
                .append("speciality", getSpeciality())
                .append("marriage", getMarriage())
                .append("nation", getNation())
                .append("companyResume", getCompanyResume())
                .append("address", getAddress())
                .append("emergencyContact", getEmergencyContact())
                .append("emergencyContactPhone", getEmergencyContactPhone())
                .append("graduationInstitution", getGraduationInstitution())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("leaveDate", getLeaveDate())
                .append("contact2", getContact2())
                .append("accounteEntryDate", getAccounteEntryDate())
                .append("resumeUrl", getResumeUrl())
                .append("resumeType", getResumeType())
                .append("sexCount", getSexCount())
                .append("secretaryLingCount", getSecretaryLingCount())
                .append("educationCount", getEducationCount())
                .toString();
    }
}
