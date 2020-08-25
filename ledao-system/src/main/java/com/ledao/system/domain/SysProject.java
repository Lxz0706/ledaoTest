package com.ledao.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 投后部项目管理对象 sys_project
 *
 * @author ledao
 * @date 2020-08-06
 */
public class SysProject extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 资产包状态
     */
    @Excel(name = "资产包状态")
    private String assetStatus;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private String no;

    /**
     * 借款人名称
     */
    @Excel(name = "借款人名称")
    private String borrower;

    /**
     * 城市/地区
     */
    @Excel(name = "城市/地区")
    private String city;

    /**
     * 放款银行
     */
    @Excel(name = "放款银行")
    private String loanBank;

    /**
     * 合同本金(元)
     */
    @Excel(name = "合同本金(元)")
    private BigDecimal contractPrincipal;

    /**
     * 本金余额(元)
     */
    @Excel(name = "本金余额(元)")
    private BigDecimal principalBalance;

    /**
     * 利息金额(元)
     */
    @Excel(name = "利息余额(元)")
    private BigDecimal interestBalance;

    /**
     * 本息余额(元)
     */
    @Excel(name = "本息余额(元)")
    private BigDecimal principalInterestBalance;

    /**
     * 保证人
     */
    @Excel(name = "保证人")
    private String guarantor;

    /**
     * 抵押物
     */
    @Excel(name = "抵押物")
    private String collateral;

    /**
     * 项目经理
     */
    @Excel(name = "项目经理")
    private String projectManager;

    /**
     * 项目经理ID
     */
    @Excel(name = "项目经理ID")
    private Long userId;

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
     * 司法状态备注
     */
    @Excel(name = "司法状态备注")
    private String judicialRemarks;

    /**
     * 抵押物/债权
     */
    @Excel(name = "抵押物/债权")
    private String collateralClaims;

    /**
     * 成交类型
     */
    @Excel(name = "成交类型")
    private String transactionType;

    /**
     * 买家
     */
    @Excel(name = "买家")
    private String buyer;

    /**
     * 买家ID
     */
    @Excel(name = "买家ID")
    private Long buyerId;

    /**
     * 买家来源
     */
    @Excel(name = "买家来源")
    private String buyerSources;

    /**
     * 买家联系方式
     */
    @Excel(name = "买家联系方式")
    private String buyerContact;

    /**
     * 所属债权合同编号
     */
    @Excel(name = "所属债权合同编号")
    private String contractNo;

    /**
     * 诉讼时效起算日
     */
    @Excel(name = "诉讼时效起算日")
    private String limitationAction;

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
    @Excel(name = "执行时效起算日")
    private String limitationExecution;

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
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父级ID
     */
    @Excel(name = "父级ID")
    private Long parentId;

    /**
     * 项目管理资产库ID
     */
    private Long projectZckId;

    private BigDecimal totalPrice;

    private BigDecimal totalInterestBalance;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public BigDecimal getContractPrincipal() {
        return contractPrincipal;
    }

    public void setContractPrincipal(BigDecimal contractPrincipal) {
        this.contractPrincipal = contractPrincipal;
    }

    public BigDecimal getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(BigDecimal principalBalance) {
        this.principalBalance = principalBalance;
    }

    public BigDecimal getInterestBalance() {
        return interestBalance;
    }

    public void setInterestBalance(BigDecimal interestBalance) {
        this.interestBalance = interestBalance;
    }

    public BigDecimal getPrincipalInterestBalance() {
        return principalInterestBalance;
    }

    public void setPrincipalInterestBalance(BigDecimal principalInterestBalance) {
        this.principalInterestBalance = principalInterestBalance;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void setJudicialRemarks(String judicialRemarks) {
        this.judicialRemarks = judicialRemarks;
    }

    public String getJudicialRemarks() {
        return judicialRemarks;
    }

    public void setCollateralClaims(String collateralClaims) {
        this.collateralClaims = collateralClaims;
    }

    public String getCollateralClaims() {
        return collateralClaims;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyer() {
        return buyer;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setBuyerSources(String buyerSources) {
        this.buyerSources = buyerSources;
    }

    public String getBuyerSources() {
        return buyerSources;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setLimitationAction(String limitationAction) {
        this.limitationAction = limitationAction;
    }

    public String getLimitationAction() {
        return limitationAction;
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

    public void setLimitationExecution(String limitationExecution) {
        this.limitationExecution = limitationExecution;
    }

    public String getLimitationExecution() {
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

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getProjectZckId() {
        return projectZckId;
    }

    public void setProjectZckId(Long projectZckId) {
        this.projectZckId = projectZckId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalInterestBalance() {
        return totalInterestBalance;
    }

    public void setTotalInterestBalance(BigDecimal totalInterestBalance) {
        this.totalInterestBalance = totalInterestBalance;
    }

    public String getLawFirm() {
        return lawFirm;
    }

    public void setLawFirm(String lawFirm) {
        this.lawFirm = lawFirm;
    }

    public String getLawyerContact() {
        return lawyerContact;
    }

    public void setLawyerContact(String lawyerContact) {
        this.lawyerContact = lawyerContact;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("assetStatus", getAssetStatus())
                .append("no", getNo())
                .append("borrower", getBorrower())
                .append("city", getCity())
                .append("loanBank", getLoanBank())
                .append("contractPrincipal", getContractPrincipal())
                .append("principalBalance", getPrincipalBalance())
                .append("interestBalance", getInterestBalance())
                .append("principalInterestBalance", getPrincipalInterestBalance())
                .append("guarantor", getGuarantor())
                .append("collateral", getCollateral())
                .append("projectManager", getProjectManager())
                .append("userId", getUserId())
                .append("judicialStatus", getJudicialStatus())
                .append("competentCourt", getCompetentCourt())
                .append("judgeContact", getJudgeContact())
                .append("judicialRemarks", getJudicialRemarks())
                .append("collateralClaims", getCollateralClaims())
                .append("transactionType", getTransactionType())
                .append("buyer", getBuyer())
                .append("buyerId", getBuyerId())
                .append("buyerSources", getBuyerSources())
                .append("buyerContact", getBuyerContact())
                .append("contractNo", getContractNo())
                .append("limitationAction", getLimitationAction())
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
                .append("delFlag", getDelFlag())
                .append("parentId", getParentId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("projectZckId", getProjectZckId())
                .append("lawFirm", getLawFirm())
                .append("lawyerContact", getLawyerContact())
                .toString();
    }
}
