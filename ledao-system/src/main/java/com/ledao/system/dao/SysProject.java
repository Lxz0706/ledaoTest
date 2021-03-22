package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
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
    private Long no;

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

    private String guarantors;

    /**
     * 抵押物
     */
    @Excel(name = "抵押物")
    private String collateral;

    private String collaterals;

    /**
     * 质押物
     */
    private String pledge;

    private String pledges;

    /**
     * 项目经理ID
     */
    private Long projectManagerId;

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
    //@Excel(name = "买家ID")
    private String buyerId;

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
    private Date limitationAction;

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

    /**
     * 资产包名称
     */
    private String projectZckName;

    private BigDecimal totalPrice;

    private BigDecimal totalInterestBalance;

    /**
     * 债权状态
     */
    @Excel(name = "债权状态")
    private String debtStatus;

    /**
     * 现金回现
     */
    private BigDecimal recapture;

    /**
     * 合同本金中的总利息
     */
    private BigDecimal totalInterest;

    /**
     * 合同本金格式化
     */
    private String contractPrincipals;

    /**
     * 本金余额
     */
    private String principalBalances;

    /**
     * 利息余额
     */
    private String interestBalances;

    /**
     * 本息余额
     */
    private String principalInterestBalances;

    /**
     * 总本金余额
     */
    private BigDecimal TotalPrincipalBalance;

    /**
     * 意向客户
     */
    @Excel(name = "意向客户")
    private String potentialCustomers;

    /**
     * 意向客户ID
     */
    // @Excel(name = "意向客户ID")
    private String potentialCustomersId;

    /**
     * 查封日期提醒
     */
    //@Excel(name = "查封日期提醒")
    private String seizure;

    /**
     * 执行时效提醒
     */
    //@Excel(name = "执行时效提醒")
    private String ageing;

    /**
     * 诉讼时效提醒
     */
    //@Excel(name = "诉讼时效提醒")
    private String limitation;

    /**
     * 成交客户id
     */
    //@Excel(name = "成交客户id")
    private String dealCustomerId;

    /**
     * 成交客户名称
     */
    @Excel(name = "客户名称")
    private String dealCustomerName;

    private String isCreate;

    /**
     * 客户标签
     */
    //@Excel(name = "客户标签")
    private String customerLable;

    /**
     * 资产供应方id
     */
    //@Excel(name = "资产供应方id")
    private String assetSupplierId;

    /**
     * 资产供应方名称
     */
    // @Excel(name = "资产供应方名称")
    private String assetSupplierName;

    /**
     * 资金供应方id
     */
    //@Excel(name = "资金供应方id")
    private String fundingProviderId;

    /**
     * 资金供应发名称
     */
    // @Excel(name = "资金供应发名称")
    private String fundingProviderName;

    /**
     * 律师id
     */
    //@Excel(name = "律师id")
    private String lawyerId;

    /**
     * 律师名称
     */
    //@Excel(name = "律师名称")
    private String lawyerName;

    /**
     * 中介方id
     */
    @Excel(name = "律师id")
    private String intermediaryId;

    /**
     * 中介方名称
     */
    //@Excel(name = "律师名称")
    private String intermediaryName;

    /**
     * 物权意向客户id
     */
    //@Excel(name = "物权意向客户id")
    private String wqyxCustomerId;

    /**
     * 物权意向客户名称
     */
    //@Excel(name = "物权意向客户名称")
    private String wqyxCustomerName;

    /**
     * 物权成交客户id
     */
    //@Excel(name = "物权成交客户id")
    private String wqcjCustomerId;

    /**
     * 物权成交客户名称
     */
    //@Excel(name = "物权成交客户名称")
    private String wqcjCustomerName;

    /**
     * 债权意向客户id
     */
    //@Excel(name = "债权意向客户id")
    private String zqyxCustomerId;

    /**
     * 债券意向客户名称
     */
    //@Excel(name = "债券意向客户名称")
    private String zqyxCustomerName;

    /**
     * 债权成交客户id
     */
    //@Excel(name = "债权成交客户id")
    private String zqcjCustomerId;

    /**
     * 债权成交客户名称
     */
    //@Excel(name = "债权成交客户名称")
    private String zqcjCustomerName;

    /**
     * 其他id
     */
    //@Excel(name = "其他id")
    private String otherId;

    /**
     * 其他名称
     */
    //@Excel(name = "其他名称")
    private String otherName;

    /*
     * 剩余户数
     * */
    private Long syhs;

    /**
     * 项目总数
     */
    private Long projectTotalCount;

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

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
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

    public String getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        this.pledge = pledge;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public String getCollateral() {
        return collateral;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
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

    public Date getLimitationAction() {
        return limitationAction;
    }

    public void setLimitationAction(Date limitationAction) {
        this.limitationAction = limitationAction;
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

    public Date getLimitationExecution() {
        return limitationExecution;
    }

    public void setLimitationExecution(Date limitationExecution) {
        this.limitationExecution = limitationExecution;
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

    public String getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(String debtStatus) {
        this.debtStatus = debtStatus;
    }

    public BigDecimal getRecapture() {
        return recapture;
    }

    public void setRecapture(BigDecimal recapture) {
        this.recapture = recapture;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getContractPrincipals() {
        return contractPrincipals;
    }

    public void setContractPrincipals(String contractPrincipals) {
        this.contractPrincipals = contractPrincipals;
    }

    public String getPrincipalBalances() {
        return principalBalances;
    }

    public void setPrincipalBalances(String principalBalances) {
        this.principalBalances = principalBalances;
    }

    public String getInterestBalances() {
        return interestBalances;
    }

    public void setInterestBalances(String interestBalances) {
        this.interestBalances = interestBalances;
    }

    public String getPrincipalInterestBalances() {
        return principalInterestBalances;
    }

    public void setPrincipalInterestBalances(String principalInterestBalances) {
        this.principalInterestBalances = principalInterestBalances;
    }

    public BigDecimal getTotalPrincipalBalance() {
        return TotalPrincipalBalance;
    }

    public void setTotalPrincipalBalance(BigDecimal totalPrincipalBalance) {
        TotalPrincipalBalance = totalPrincipalBalance;
    }

    public String getGuarantors() {
        return guarantors;
    }

    public void setGuarantors(String guarantors) {
        this.guarantors = guarantors;
    }

    public String getCollaterals() {
        return collaterals;
    }

    public void setCollaterals(String collaterals) {
        this.collaterals = collaterals;
    }

    public String getPledges() {
        return pledges;
    }

    public void setPledges(String pledges) {
        this.pledges = pledges;
    }

    public String getPotentialCustomers() {
        return potentialCustomers;
    }

    public void setPotentialCustomers(String potentialCustomers) {
        this.potentialCustomers = potentialCustomers;
    }

    public String getPotentialCustomersId() {
        return potentialCustomersId;
    }

    public void setPotentialCustomersId(String potentialCustomersId) {
        this.potentialCustomersId = potentialCustomersId;
    }

    public String getSeizure() {
        return seizure;
    }

    public void setSeizure(String seizure) {
        this.seizure = seizure;
    }

    public String getAgeing() {
        return ageing;
    }

    public void setAgeing(String ageing) {
        this.ageing = ageing;
    }

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public String getDealCustomerId() {
        return dealCustomerId;
    }

    public void setDealCustomerId(String dealCustomerId) {
        this.dealCustomerId = dealCustomerId;
    }

    public String getDealCustomerName() {
        return dealCustomerName;
    }

    public void setDealCustomerName(String dealCustomerName) {
        this.dealCustomerName = dealCustomerName;
    }

    public String getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(String isCreate) {
        this.isCreate = isCreate;
    }

    public String getProjectZckName() {
        return projectZckName;
    }

    public void setProjectZckName(String projectZckName) {
        this.projectZckName = projectZckName;
    }

    public String getCustomerLable() {
        return customerLable;
    }

    public void setCustomerLable(String customerLable) {
        this.customerLable = customerLable;
    }

    public String getAssetSupplierId() {
        return assetSupplierId;
    }

    public void setAssetSupplierId(String assetSupplierId) {
        this.assetSupplierId = assetSupplierId;
    }

    public String getAssetSupplierName() {
        return assetSupplierName;
    }

    public void setAssetSupplierName(String assetSupplierName) {
        this.assetSupplierName = assetSupplierName;
    }

    public String getFundingProviderId() {
        return fundingProviderId;
    }

    public void setFundingProviderId(String fundingProviderId) {
        this.fundingProviderId = fundingProviderId;
    }

    public String getFundingProviderName() {
        return fundingProviderName;
    }

    public void setFundingProviderName(String fundingProviderName) {
        this.fundingProviderName = fundingProviderName;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getIntermediaryId() {
        return intermediaryId;
    }

    public void setIntermediaryId(String intermediaryId) {
        this.intermediaryId = intermediaryId;
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    public void setIntermediaryName(String intermediaryName) {
        this.intermediaryName = intermediaryName;
    }

    public String getWqyxCustomerId() {
        return wqyxCustomerId;
    }

    public void setWqyxCustomerId(String wqyxCustomerId) {
        this.wqyxCustomerId = wqyxCustomerId;
    }

    public String getWqyxCustomerName() {
        return wqyxCustomerName;
    }

    public void setWqyxCustomerName(String wqyxCustomerName) {
        this.wqyxCustomerName = wqyxCustomerName;
    }

    public String getWqcjCustomerId() {
        return wqcjCustomerId;
    }

    public void setWqcjCustomerId(String wqcjCustomerId) {
        this.wqcjCustomerId = wqcjCustomerId;
    }

    public String getWqcjCustomerName() {
        return wqcjCustomerName;
    }

    public void setWqcjCustomerName(String wqcjCustomerName) {
        this.wqcjCustomerName = wqcjCustomerName;
    }

    public String getZqyxCustomerId() {
        return zqyxCustomerId;
    }

    public void setZqyxCustomerId(String zqyxCustomerId) {
        this.zqyxCustomerId = zqyxCustomerId;
    }

    public String getZqyxCustomerName() {
        return zqyxCustomerName;
    }

    public void setZqyxCustomerName(String zqyxCustomerName) {
        this.zqyxCustomerName = zqyxCustomerName;
    }

    public String getZqcjCustomerId() {
        return zqcjCustomerId;
    }

    public void setZqcjCustomerId(String zqcjCustomerId) {
        this.zqcjCustomerId = zqcjCustomerId;
    }

    public String getZqcjCustomerName() {
        return zqcjCustomerName;
    }

    public void setZqcjCustomerName(String zqcjCustomerName) {
        this.zqcjCustomerName = zqcjCustomerName;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getSyhs() {
        return syhs;
    }

    public void setSyhs(Long syhs) {
        this.syhs = syhs;
    }

    public Long getProjectTotalCount() {
        return projectTotalCount;
    }

    public void setProjectTotalCount(Long projectTotalCount) {
        this.projectTotalCount = projectTotalCount;
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
                .append("pledge", getPledge())
                .append("projectManagerId", getProjectManagerId())
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
                .append("projectZckId", getProjectZckId())
                .append("parentId", getParentId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("lawFirm", getLawFirm())
                .append("lawyerContact", getLawyerContact())
                .append("debtStatus", getDebtStatus())
                .append("potentialCustomers", getPotentialCustomers())
                .append("potentialCustomersId", getPotentialCustomersId())
                .append("seizure", getSeizure())
                .append("ageing", getAgeing())
                .append("limitation", getLimitation())
                .append("dealCustomerId", getDealCustomerId())
                .append("dealCustomerName", getDealCustomerName())
                .append("customerLable", getCustomerLable())
                .append("assetSupplierId", getAssetSupplierId())
                .append("assetSupplierName", getAssetSupplierName())
                .append("fundingProviderId", getFundingProviderId())
                .append("fundingProviderName", getFundingProviderName())
                .append("lawyerId", getLawyerId())
                .append("lawyerName", getLawyerName())
                .append("intermediaryId", getIntermediaryId())
                .append("intermediaryName", getIntermediaryName())
                .append("wqyxCustomerId", getWqyxCustomerId())
                .append("wqyxCustomerName", getWqyxCustomerName())
                .append("wqcjCustomerId", getWqcjCustomerId())
                .append("wqcjCustomerName", getWqcjCustomerName())
                .append("zqyxCustomerId", getZqyxCustomerId())
                .append("zqyxCustomerName", getZqyxCustomerName())
                .append("zqcjCustomerId", getZqcjCustomerId())
                .append("zqcjCustomerName", getZqcjCustomerName())
                .append("otherId", getOtherId())
                .append("otherName", getOtherName())
                .toString();
    }
}
