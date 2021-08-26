package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 项目法律信息对象 sys_monomer_law
 *
 * @author lxz
 * @date 2021-08-23
 */
public class SysMonomerLaw extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long monomerLawId;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private Long projectId;

    /**
     * 项目类型（1 投资 2 大型单体 3 投后）
     */
    @Excel(name = "项目类型", readConverterExp = "1=,投=资,2=,大=型单体,3=,投=后")
    private String projectType;

    /**
     * 司法状态
     */
    @Excel(name = "司法状态")
    private String judicialStatus;

    /**
     * 管辖法院
     */
    @Excel(name = "管辖法院")
    private String competentCourt;

    /**
     * 法官及联系方式
     */
    @Excel(name = "法官及联系方式")
    private String judgeContact;

    /**
     * 律所
     */
    @Excel(name = "律所")
    private String lawFirm;

    /**
     * 律师及联系方式
     */
    @Excel(name = "律师及联系方式")
    private String lawyerContact;

    /**
     * 所属债权合同编号
     */
    @Excel(name = "所属债权合同编号")
    private String contractNo;

    /**
     * 诉讼时效起算日
     */
    @Excel(name = "诉讼时效起算日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date limitationAction;

    /**
     * 开庭时间
     */
    @Excel(name = "开庭时间")
    private String openTime;

    /**
     * 查封标的
     */
    @Excel(name = "查封标的")
    private String sealUpSubjectMatter;

    /**
     * 查封日期
     */
    @Excel(name = "查封日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sealUpDate;

    /**
     * 执行时效起算日
     */
    @Excel(name = "执行时效起算日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date limitationExecution;

    /**
     * 起诉立案日期
     */
    @Excel(name = "起诉立案日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateFiling;

    /**
     * 一审判决案号及日期
     */
    @Excel(name = "一审判决案号及日期")
    private String firstInstance;

    /**
     * 二审判决案号及日期
     */
    @Excel(name = "二审判决案号及日期")
    private String secondInstance;

    /**
     * 再审判决案号及日期
     */
    @Excel(name = "再审判决案号及日期")
    private String retrial;

    /**
     * 执行立案日期
     */
    @Excel(name = "执行立案日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enforcementFilingDate;

    /**
     * 出具评估报告日期
     */
    @Excel(name = "出具评估报告日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date appraisalReportDate;

    /**
     * 第一次挂拍日期
     */
    @Excel(name = "第一次挂拍日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstShotDate;

    /**
     * 第二次挂拍日期
     */
    @Excel(name = "第二次挂拍日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date secondShotDate;

    /**
     * 变卖挂拍日期
     */
    @Excel(name = "变卖挂拍日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sellShotDate;

    /**
     * 拍卖成交日期
     */
    @Excel(name = "拍卖成交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date successfulBidderDate;

    /**
     * 执行款到账日期
     */
    @Excel(name = "执行款到账日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentReceivedDate;

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
     * 删除标志（0 存在 2 删除）
     */
    private String delFlag;

    public void setMonomerLawId(Long monomerLawId) {
        this.monomerLawId = monomerLawId;
    }

    public Long getMonomerLawId() {
        return monomerLawId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setJudicialStatus(String judicialStatus) {
        this.judicialStatus = judicialStatus;
    }

    public String getJudicialStatus() {
        return judicialStatus;
    }

    public void setCompetentCourt(String competentCourt) {
        this.competentCourt = competentCourt;
    }

    public String getCompetentCourt() {
        return competentCourt;
    }

    public void setJudgeContact(String judgeContact) {
        this.judgeContact = judgeContact;
    }

    public String getJudgeContact() {
        return judgeContact;
    }

    public void setLawFirm(String lawFirm) {
        this.lawFirm = lawFirm;
    }

    public String getLawFirm() {
        return lawFirm;
    }

    public void setLawyerContact(String lawyerContact) {
        this.lawyerContact = lawyerContact;
    }

    public String getLawyerContact() {
        return lawyerContact;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setLimitationAction(Date limitationAction) {
        this.limitationAction = limitationAction;
    }

    public Date getLimitationAction() {
        return limitationAction;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setSealUpSubjectMatter(String sealUpSubjectMatter) {
        this.sealUpSubjectMatter = sealUpSubjectMatter;
    }

    public String getSealUpSubjectMatter() {
        return sealUpSubjectMatter;
    }

    public void setSealUpDate(Date sealUpDate) {
        this.sealUpDate = sealUpDate;
    }

    public Date getSealUpDate() {
        return sealUpDate;
    }

    public void setLimitationExecution(Date limitationExecution) {
        this.limitationExecution = limitationExecution;
    }

    public Date getLimitationExecution() {
        return limitationExecution;
    }

    public void setDateFiling(Date dateFiling) {
        this.dateFiling = dateFiling;
    }

    public Date getDateFiling() {
        return dateFiling;
    }

    public void setFirstInstance(String firstInstance) {
        this.firstInstance = firstInstance;
    }

    public String getFirstInstance() {
        return firstInstance;
    }

    public void setSecondInstance(String secondInstance) {
        this.secondInstance = secondInstance;
    }

    public String getSecondInstance() {
        return secondInstance;
    }

    public void setRetrial(String retrial) {
        this.retrial = retrial;
    }

    public String getRetrial() {
        return retrial;
    }

    public void setEnforcementFilingDate(Date enforcementFilingDate) {
        this.enforcementFilingDate = enforcementFilingDate;
    }

    public Date getEnforcementFilingDate() {
        return enforcementFilingDate;
    }

    public void setAppraisalReportDate(Date appraisalReportDate) {
        this.appraisalReportDate = appraisalReportDate;
    }

    public Date getAppraisalReportDate() {
        return appraisalReportDate;
    }

    public void setFirstShotDate(Date firstShotDate) {
        this.firstShotDate = firstShotDate;
    }

    public Date getFirstShotDate() {
        return firstShotDate;
    }

    public void setSecondShotDate(Date secondShotDate) {
        this.secondShotDate = secondShotDate;
    }

    public Date getSecondShotDate() {
        return secondShotDate;
    }

    public void setSellShotDate(Date sellShotDate) {
        this.sellShotDate = sellShotDate;
    }

    public Date getSellShotDate() {
        return sellShotDate;
    }

    public void setSuccessfulBidderDate(Date successfulBidderDate) {
        this.successfulBidderDate = successfulBidderDate;
    }

    public Date getSuccessfulBidderDate() {
        return successfulBidderDate;
    }

    public void setPaymentReceivedDate(Date paymentReceivedDate) {
        this.paymentReceivedDate = paymentReceivedDate;
    }

    public Date getPaymentReceivedDate() {
        return paymentReceivedDate;
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

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("monomerLawId", getMonomerLawId())
                .append("projectId", getProjectId())
                .append("projectType", getProjectType())
                .append("judicialStatus", getJudicialStatus())
                .append("competentCourt", getCompetentCourt())
                .append("judgeContact", getJudgeContact())
                .append("lawFirm", getLawFirm())
                .append("lawyerContact", getLawyerContact())
                .append("contractNo", getContractNo())
                .append("limitationAction", getLimitationAction())
                .append("openTime", getOpenTime())
                .append("sealUpSubjectMatter", getSealUpSubjectMatter())
                .append("sealUpDate", getSealUpDate())
                .append("limitationExecution", getLimitationExecution())
                .append("dateFiling", getDateFiling())
                .append("firstInstance", getFirstInstance())
                .append("secondInstance", getSecondInstance())
                .append("retrial", getRetrial())
                .append("enforcementFilingDate", getEnforcementFilingDate())
                .append("appraisalReportDate", getAppraisalReportDate())
                .append("firstShotDate", getFirstShotDate())
                .append("secondShotDate", getSecondShotDate())
                .append("sellShotDate", getSellShotDate())
                .append("successfulBidderDate", getSuccessfulBidderDate())
                .append("paymentReceivedDate", getPaymentReceivedDate())
                .append("createBy", getCreateBy())
                .append("creator", getCreator())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("reviser", getReviser())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
