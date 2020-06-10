package com.ledao.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 资产信息库对象 sys_zck
 *
 * @author ledao
 * @date 2020-06-10
 */
public class SysZck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 资产包名称
     */
    @Excel(name = "资产包名称")
    private String assetPackageName;

    /**
     * 资产状态
     */
    @Excel(name = "资产状态")
    private String assetStatus;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Integer no;

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
     * 合同本金
     */
    @Excel(name = "合同本金")
    private Double contractPrincipal;

    /**
     * 本金余额
     */
    @Excel(name = "本金余额")
    private Double principalBalance;

    /**
     * 利息余额
     */
    @Excel(name = "利息余额")
    private Double interestBalance;

    /**
     * 本息余额
     */
    @Excel(name = "本息余额")
    private Double principalInterestBalance;

    /**
     * 本息计算基准日
     */
    @Excel(name = "本息计算基准日", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bxjsDate;

    /**
     * 贷款行
     */
    @Excel(name = "贷款行")
    private String loanBank;

    /**
     * 贷款合同编号
     */
    @Excel(name = "贷款合同编号")
    private String loanContractNo;

    /**
     * 借款期限
     */
    @Excel(name = "借款期限", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loanTime;

    /**
     * 保证人
     */
    @Excel(name = "保证人")
    private String guarantor;

    /**
     * 保证人自然人数量
     */
    @Excel(name = "保证人自然人数量")
    private Integer bzrZrrNumber;

    /**
     * 保证人法人数量
     */
    @Excel(name = "保证人法人数量")
    private Integer bzrFrNumber;

    /**
     * 保证金额
     */
    @Excel(name = "保证金额")
    private Double guaranteeAmount;

    /**
     * 保证合同编号
     */
    @Excel(name = "保证合同编号")
    private String guaranteeContractNo;

    /**
     * 保证合同签订时间
     */
    @Excel(name = "保证合同签订时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date guaranteeContractSignTime;

    /**
     * 最高额保证金额
     */
    @Excel(name = "最高额保证金额")
    private Double maximumGuaranteeAmount;

    /**
     * 最高额保证合同·保函编号
     */
    @Excel(name = "最高额保证合同·保函编号")
    private String maximumGuaranteeContract;

    /**
     * 最高额保证合同签订时间
     */
    @Excel(name = "最高额保证合同签订时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date maximumGuaratnteeSignTime;

    /**
     * 担保的主债权发生期间
     */
    @Excel(name = "担保的主债权发生期间")
    private String zzqDateTime;

    /**
     * 保证方式
     */
    @Excel(name = "保证方式")
    private String guaranteeMode;

    /**
     * 保证期间
     */
    @Excel(name = "保证期间")
    private String guaranteePeriod;

    /**
     * 备注(包括与债务人关系，名下财产，还款意愿等)
     */
    @Excel(name = "备注(包括与债务人关系，名下财产，还款意愿等)")
    private String guaranteeRemarks;

    /**
     * 资产性质
     */
    @Excel(name = "资产性质")
    private String assetNature;

    /**
     * 住址
     */
    @Excel(name = "住址")
    private String address;

    /**
     * 权属证号
     */
    @Excel(name = "权属证号")
    private String qsNo;

    /**
     * 面积·㎡
     */
    @Excel(name = "面积·㎡")
    private String area;

    /**
     * 查封情况
     */
    @Excel(name = "查封情况")
    private String sealUp;

    /**
     * 抵押情况
     */
    @Excel(name = "抵押情况")
    private String mortgage;

    /**
     * 备注（包括权属人与债务人关系，处置及回款情况）
     */
    @Excel(name = "备注（包括权属人与债务人关系，处置及回款情况）")
    private String otherRemark;

    /**
     * 借款人行业
     */
    @Excel(name = "借款人行业")
    private String jkrIndustry;

    /**
     * 国企/非国企
     */
    @Excel(name = "国企/非国企")
    private String soe;

    /**
     * 经营情况
     */
    @Excel(name = "经营情况")
    private String operation;

    /**
     * 法院
     */
    @Excel(name = "法院")
    private String court;

    /**
     * 诉讼情况
     */
    @Excel(name = "诉讼情况")
    private String litigation;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String judicialRemark;

    /**
     * 地址（包含楼盘名称）
     */
    @Excel(name = "地址（包含楼盘名称）")
    private String dywAddress;

    /**
     * 抵（质）押金额(本金)
     */
    @Excel(name = "抵（质）押金额(本金)")
    private Double mortgageAmount;

    /**
     * 抵（质）押合同编号
     */
    @Excel(name = "抵（质）押合同编号")
    private String mortgageContractNo;

    /**
     * 抵（质）押合同签订时间
     */
    @Excel(name = "抵（质）押合同签订时间")
    private Date dzywqdTime;

    /**
     * 最高额抵押金额
     */
    @Excel(name = "最高额抵押金额")
    private Long maximumMortgageAmount;

    /**
     * 最高额抵押合同编号
     */
    @Excel(name = "最高额抵押合同编号")
    private String maximumMortgageContractNo;

    /**
     * 最高额抵押合同签订时间
     */
    @Excel(name = "最高额抵押合同签订时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date zgeContractTime;

    /**
     * 担保的主债权发生期间
     */
    @Excel(name = "担保的主债权发生期间")
    private String dbzzqTime;

    /**
     * 抵押人
     */
    @Excel(name = "抵押人")
    private String mortgagor;

    /**
     * 权属证号
     */
    @Excel(name = "权属证号")
    private String zgeQsNo;

    /**
     * 他项权证编号
     */
    @Excel(name = "他项权证编号")
    private String txQzNo;

    /**
     * 查封情况/查封法院/案号
     */
    @Excel(name = "查封情况/查封法院/案号")
    private String caseNo;

    /**
     * 抵押顺位
     */
    @Excel(name = "抵押顺位")
    private String mortgageRank;

    /**
     * 前序顺位抵押金额
     */
    @Excel(name = "前序顺位抵押金额")
    private Double qxswMortgeageAmount;

    /**
     * 出租情况
     */
    @Excel(name = "出租情况")
    private String rentalSituation;

    /**
     * 抵前or抵后
     */
    @Excel(name = "抵前or抵后")
    private String beforeOrAterAarrival;

    /**
     * 出租相关细节
     */
    @Excel(name = "出租相关细节")
    private String rentalDetails;

    /**
     * 是否有未成年人
     */
    @Excel(name = "是否有未成年人")
    private String juveniles;

    /**
     * 是否唯一住房
     */
    @Excel(name = "是否唯一住房")
    private String onlyHouse;

    /**
     * 是否需要腾房
     */
    @Excel(name = "是否需要腾房")
    private String tengfang;

    /**
     * 土地性质
     */
    @Excel(name = "土地性质")
    private String natureLand;

    /**
     * 剩余使用年限
     */
    @Excel(name = "剩余使用年限")
    private String sysynx;

    /**
     * 容积率
     */
    @Excel(name = "容积率")
    private String plotRatio;

    /**
     * 获得方式
     */
    @Excel(name = "获得方式")
    private String access;

    /**
     * 土地用途
     */
    @Excel(name = "土地用途")
    private String zoning;

    /**
     * 土地面积
     */
    @Excel(name = "土地面积")
    private Double landArea;

    /**
     * 建筑结构
     */
    @Excel(name = "建筑结构")
    private String buildStructure;

    /**
     * 建成日期
     */
    @Excel(name = "建成日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completionDate;

    /**
     * 所在层数/总层数
     */
    @Excel(name = "所在层数/总层数")
    private String floors;

    /**
     * 抵/置押物类型
     */
    @Excel(name = "抵/置押物类型")
    private String collateType;

    /**
     * 建筑面积/㎡
     */
    @Excel(name = "建筑面积/㎡")
    private Double floorage;

    /**
     * 土地单价
     */
    @Excel(name = "土地单价")
    private Double landUnitPrice;

    /**
     * 土地总价
     */
    @Excel(name = "土地总价")
    private Double landTotalPrice;

    /**
     * 其它抵置押物单价
     */
    @Excel(name = "其它抵置押物单价")
    private Double otherCollateralUnitPrice;

    /**
     * 其它抵置押物总价
     */
    @Excel(name = "其它抵置押物总价")
    private Double otherCollateralTotalPrice;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private Double totalPrice;

    /**
     * 估值依据
     */
    @Excel(name = "估值依据")
    private String valuationBasis;

    /**
     * 盖帽值
     */
    @Excel(name = "盖帽值")
    private String capValue;

    /**
     * 抵置押物备注
     */
    @Excel(name = "抵置押物备注")
    private String dzywRemark;

    /**
     * 借款人、保证人网搜情况
     */
    @Excel(name = "借款人、保证人网搜情况")
    private String borrowerWsqk;

    /**
     * 财产线索
     */
    @Excel(name = "财产线索")
    private String propertyClues;

    /**
     * 估值建议
     */
    @Excel(name = "估值建议")
    private Double zhhknl;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String jkrgzRemark;

    /**
     * 定价
     */
    @Excel(name = "定价")
    private Double price;

    /**
     * 处置方式
     */
    @Excel(name = "处置方式")
    private String desposalMode;

    /**
     * 处置价格
     */
    @Excel(name = "处置价格")
    private Double desposalPrice;

    /**
     * 客户
     */
    @Excel(name = "客户")
    private String customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssetPackageName(String assetPackageName) {
        this.assetPackageName = assetPackageName;
    }

    public String getAssetPackageName() {
        return assetPackageName;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getNo() {
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

    public void setContractPrincipal(Double contractPrincipal) {
        this.contractPrincipal = contractPrincipal;
    }

    public Double getContractPrincipal() {
        return contractPrincipal;
    }

    public void setPrincipalBalance(Double principalBalance) {
        this.principalBalance = principalBalance;
    }

    public Double getPrincipalBalance() {
        return principalBalance;
    }

    public void setInterestBalance(Double interestBalance) {
        this.interestBalance = interestBalance;
    }

    public Double getInterestBalance() {
        return interestBalance;
    }

    public void setPrincipalInterestBalance(Double principalInterestBalance) {
        this.principalInterestBalance = principalInterestBalance;
    }

    public Double getPrincipalInterestBalance() {
        return principalInterestBalance;
    }

    public void setBxjsDate(Date bxjsDate) {
        this.bxjsDate = bxjsDate;
    }

    public Date getBxjsDate() {
        return bxjsDate;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setBzrZrrNumber(Integer bzrZrrNumber) {
        this.bzrZrrNumber = bzrZrrNumber;
    }

    public Integer getBzrZrrNumber() {
        return bzrZrrNumber;
    }

    public void setBzrFrNumber(Integer bzrFrNumber) {
        this.bzrFrNumber = bzrFrNumber;
    }

    public Integer getBzrFrNumber() {
        return bzrFrNumber;
    }

    public void setGuaranteeAmount(Double guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public Double getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeContractNo(String guaranteeContractNo) {
        this.guaranteeContractNo = guaranteeContractNo;
    }

    public String getGuaranteeContractNo() {
        return guaranteeContractNo;
    }

    public void setGuaranteeContractSignTime(Date guaranteeContractSignTime) {
        this.guaranteeContractSignTime = guaranteeContractSignTime;
    }

    public Date getGuaranteeContractSignTime() {
        return guaranteeContractSignTime;
    }

    public void setMaximumGuaranteeAmount(Double maximumGuaranteeAmount) {
        this.maximumGuaranteeAmount = maximumGuaranteeAmount;
    }

    public Double getMaximumGuaranteeAmount() {
        return maximumGuaranteeAmount;
    }

    public void setMaximumGuaranteeContract(String maximumGuaranteeContract) {
        this.maximumGuaranteeContract = maximumGuaranteeContract;
    }

    public String getMaximumGuaranteeContract() {
        return maximumGuaranteeContract;
    }

    public void setMaximumGuaratnteeSignTime(Date maximumGuaratnteeSignTime) {
        this.maximumGuaratnteeSignTime = maximumGuaratnteeSignTime;
    }

    public Date getMaximumGuaratnteeSignTime() {
        return maximumGuaratnteeSignTime;
    }

    public void setZzqDateTime(String zzqDateTime) {
        this.zzqDateTime = zzqDateTime;
    }

    public String getZzqDateTime() {
        return zzqDateTime;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteeRemarks(String guaranteeRemarks) {
        this.guaranteeRemarks = guaranteeRemarks;
    }

    public String getGuaranteeRemarks() {
        return guaranteeRemarks;
    }

    public void setAssetNature(String assetNature) {
        this.assetNature = assetNature;
    }

    public String getAssetNature() {
        return assetNature;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setQsNo(String qsNo) {
        this.qsNo = qsNo;
    }

    public String getQsNo() {
        return qsNo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setSealUp(String sealUp) {
        this.sealUp = sealUp;
    }

    public String getSealUp() {
        return sealUp;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setJkrIndustry(String jkrIndustry) {
        this.jkrIndustry = jkrIndustry;
    }

    public String getJkrIndustry() {
        return jkrIndustry;
    }

    public void setSoe(String soe) {
        this.soe = soe;
    }

    public String getSoe() {
        return soe;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getCourt() {
        return court;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation;
    }

    public String getLitigation() {
        return litigation;
    }

    public void setJudicialRemark(String judicialRemark) {
        this.judicialRemark = judicialRemark;
    }

    public String getJudicialRemark() {
        return judicialRemark;
    }

    public void setDywAddress(String dywAddress) {
        this.dywAddress = dywAddress;
    }

    public String getDywAddress() {
        return dywAddress;
    }

    public void setMortgageAmount(Double mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

    public Double getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageContractNo(String mortgageContractNo) {
        this.mortgageContractNo = mortgageContractNo;
    }

    public String getMortgageContractNo() {
        return mortgageContractNo;
    }

    public void setDzywqdTime(Date dzywqdTime) {
        this.dzywqdTime = dzywqdTime;
    }

    public Date getDzywqdTime() {
        return dzywqdTime;
    }

    public void setMaximumMortgageAmount(Long maximumMortgageAmount) {
        this.maximumMortgageAmount = maximumMortgageAmount;
    }

    public Long getMaximumMortgageAmount() {
        return maximumMortgageAmount;
    }

    public void setMaximumMortgageContractNo(String maximumMortgageContractNo) {
        this.maximumMortgageContractNo = maximumMortgageContractNo;
    }

    public String getMaximumMortgageContractNo() {
        return maximumMortgageContractNo;
    }

    public void setZgeContractTime(Date zgeContractTime) {
        this.zgeContractTime = zgeContractTime;
    }

    public Date getZgeContractTime() {
        return zgeContractTime;
    }

    public void setDbzzqTime(String dbzzqTime) {
        this.dbzzqTime = dbzzqTime;
    }

    public String getDbzzqTime() {
        return dbzzqTime;
    }

    public void setMortgagor(String mortgagor) {
        this.mortgagor = mortgagor;
    }

    public String getMortgagor() {
        return mortgagor;
    }

    public void setZgeQsNo(String zgeQsNo) {
        this.zgeQsNo = zgeQsNo;
    }

    public String getZgeQsNo() {
        return zgeQsNo;
    }

    public void setTxQzNo(String txQzNo) {
        this.txQzNo = txQzNo;
    }

    public String getTxQzNo() {
        return txQzNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setMortgageRank(String mortgageRank) {
        this.mortgageRank = mortgageRank;
    }

    public String getMortgageRank() {
        return mortgageRank;
    }

    public void setQxswMortgeageAmount(Double qxswMortgeageAmount) {
        this.qxswMortgeageAmount = qxswMortgeageAmount;
    }

    public Double getQxswMortgeageAmount() {
        return qxswMortgeageAmount;
    }

    public void setRentalSituation(String rentalSituation) {
        this.rentalSituation = rentalSituation;
    }

    public String getRentalSituation() {
        return rentalSituation;
    }

    public void setBeforeOrAterAarrival(String beforeOrAterAarrival) {
        this.beforeOrAterAarrival = beforeOrAterAarrival;
    }

    public String getBeforeOrAterAarrival() {
        return beforeOrAterAarrival;
    }

    public void setRentalDetails(String rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    public String getRentalDetails() {
        return rentalDetails;
    }

    public void setJuveniles(String juveniles) {
        this.juveniles = juveniles;
    }

    public String getJuveniles() {
        return juveniles;
    }

    public void setOnlyHouse(String onlyHouse) {
        this.onlyHouse = onlyHouse;
    }

    public String getOnlyHouse() {
        return onlyHouse;
    }

    public void setTengfang(String tengfang) {
        this.tengfang = tengfang;
    }

    public String getTengfang() {
        return tengfang;
    }

    public void setNatureLand(String natureLand) {
        this.natureLand = natureLand;
    }

    public String getNatureLand() {
        return natureLand;
    }

    public void setSysynx(String sysynx) {
        this.sysynx = sysynx;
    }

    public String getSysynx() {
        return sysynx;
    }

    public void setPlotRatio(String plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getPlotRatio() {
        return plotRatio;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public void setZoning(String zoning) {
        this.zoning = zoning;
    }

    public String getZoning() {
        return zoning;
    }

    public void setLandArea(Double landArea) {
        this.landArea = landArea;
    }

    public Double getLandArea() {
        return landArea;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
    }

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getFloors() {
        return floors;
    }

    public void setCollateType(String collateType) {
        this.collateType = collateType;
    }

    public String getCollateType() {
        return collateType;
    }

    public void setFloorage(Double floorage) {
        this.floorage = floorage;
    }

    public Double getFloorage() {
        return floorage;
    }

    public void setLandUnitPrice(Double landUnitPrice) {
        this.landUnitPrice = landUnitPrice;
    }

    public Double getLandUnitPrice() {
        return landUnitPrice;
    }

    public void setLandTotalPrice(Double landTotalPrice) {
        this.landTotalPrice = landTotalPrice;
    }

    public Double getLandTotalPrice() {
        return landTotalPrice;
    }

    public void setOtherCollateralUnitPrice(Double otherCollateralUnitPrice) {
        this.otherCollateralUnitPrice = otherCollateralUnitPrice;
    }

    public Double getOtherCollateralUnitPrice() {
        return otherCollateralUnitPrice;
    }

    public void setOtherCollateralTotalPrice(Double otherCollateralTotalPrice) {
        this.otherCollateralTotalPrice = otherCollateralTotalPrice;
    }

    public Double getOtherCollateralTotalPrice() {
        return otherCollateralTotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setValuationBasis(String valuationBasis) {
        this.valuationBasis = valuationBasis;
    }

    public String getValuationBasis() {
        return valuationBasis;
    }

    public void setCapValue(String capValue) {
        this.capValue = capValue;
    }

    public String getCapValue() {
        return capValue;
    }

    public void setDzywRemark(String dzywRemark) {
        this.dzywRemark = dzywRemark;
    }

    public String getDzywRemark() {
        return dzywRemark;
    }

    public void setBorrowerWsqk(String borrowerWsqk) {
        this.borrowerWsqk = borrowerWsqk;
    }

    public String getBorrowerWsqk() {
        return borrowerWsqk;
    }

    public void setPropertyClues(String propertyClues) {
        this.propertyClues = propertyClues;
    }

    public String getPropertyClues() {
        return propertyClues;
    }

    public void setZhhknl(Double zhhknl) {
        this.zhhknl = zhhknl;
    }

    public Double getZhhknl() {
        return zhhknl;
    }

    public void setJkrgzRemark(String jkrgzRemark) {
        this.jkrgzRemark = jkrgzRemark;
    }

    public String getJkrgzRemark() {
        return jkrgzRemark;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setDesposalMode(String desposalMode) {
        this.desposalMode = desposalMode;
    }

    public String getDesposalMode() {
        return desposalMode;
    }

    public void setDesposalPrice(Double desposalPrice) {
        this.desposalPrice = desposalPrice;
    }

    public Double getDesposalPrice() {
        return desposalPrice;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("assetPackageName", getAssetPackageName())
                .append("assetStatus", getAssetStatus())
                .append("no", getNo())
                .append("borrower", getBorrower())
                .append("city", getCity())
                .append("contractPrincipal", getContractPrincipal())
                .append("principalBalance", getPrincipalBalance())
                .append("interestBalance", getInterestBalance())
                .append("principalInterestBalance", getPrincipalInterestBalance())
                .append("bxjsDate", getBxjsDate())
                .append("loanBank", getLoanBank())
                .append("loanContractNo", getLoanContractNo())
                .append("loanTime", getLoanTime())
                .append("guarantor", getGuarantor())
                .append("bzrZrrNumber", getBzrZrrNumber())
                .append("bzrFrNumber", getBzrFrNumber())
                .append("guaranteeAmount", getGuaranteeAmount())
                .append("guaranteeContractNo", getGuaranteeContractNo())
                .append("guaranteeContractSignTime", getGuaranteeContractSignTime())
                .append("maximumGuaranteeAmount", getMaximumGuaranteeAmount())
                .append("maximumGuaranteeContract", getMaximumGuaranteeContract())
                .append("maximumGuaratnteeSignTime", getMaximumGuaratnteeSignTime())
                .append("zzqDateTime", getZzqDateTime())
                .append("guaranteeMode", getGuaranteeMode())
                .append("guaranteePeriod", getGuaranteePeriod())
                .append("guaranteeRemarks", getGuaranteeRemarks())
                .append("assetNature", getAssetNature())
                .append("address", getAddress())
                .append("qsNo", getQsNo())
                .append("area", getArea())
                .append("sealUp", getSealUp())
                .append("mortgage", getMortgage())
                .append("otherRemark", getOtherRemark())
                .append("jkrIndustry", getJkrIndustry())
                .append("soe", getSoe())
                .append("operation", getOperation())
                .append("court", getCourt())
                .append("litigation", getLitigation())
                .append("judicialRemark", getJudicialRemark())
                .append("dywAddress", getDywAddress())
                .append("mortgageAmount", getMortgageAmount())
                .append("mortgageContractNo", getMortgageContractNo())
                .append("dzywqdTime", getDzywqdTime())
                .append("maximumMortgageAmount", getMaximumMortgageAmount())
                .append("maximumMortgageContractNo", getMaximumMortgageContractNo())
                .append("zgeContractTime", getZgeContractTime())
                .append("dbzzqTime", getDbzzqTime())
                .append("mortgagor", getMortgagor())
                .append("zgeQsNo", getZgeQsNo())
                .append("txQzNo", getTxQzNo())
                .append("caseNo", getCaseNo())
                .append("mortgageRank", getMortgageRank())
                .append("qxswMortgeageAmount", getQxswMortgeageAmount())
                .append("rentalSituation", getRentalSituation())
                .append("beforeOrAterAarrival", getBeforeOrAterAarrival())
                .append("rentalDetails", getRentalDetails())
                .append("juveniles", getJuveniles())
                .append("onlyHouse", getOnlyHouse())
                .append("tengfang", getTengfang())
                .append("natureLand", getNatureLand())
                .append("sysynx", getSysynx())
                .append("plotRatio", getPlotRatio())
                .append("access", getAccess())
                .append("zoning", getZoning())
                .append("landArea", getLandArea())
                .append("buildStructure", getBuildStructure())
                .append("completionDate", getCompletionDate())
                .append("floors", getFloors())
                .append("collateType", getCollateType())
                .append("floorage", getFloorage())
                .append("landUnitPrice", getLandUnitPrice())
                .append("landTotalPrice", getLandTotalPrice())
                .append("otherCollateralUnitPrice", getOtherCollateralUnitPrice())
                .append("otherCollateralTotalPrice", getOtherCollateralTotalPrice())
                .append("totalPrice", getTotalPrice())
                .append("valuationBasis", getValuationBasis())
                .append("capValue", getCapValue())
                .append("dzywRemark", getDzywRemark())
                .append("borrowerWsqk", getBorrowerWsqk())
                .append("propertyClues", getPropertyClues())
                .append("zhhknl", getZhhknl())
                .append("jkrgzRemark", getJkrgzRemark())
                .append("price", getPrice())
                .append("desposalMode", getDesposalMode())
                .append("desposalPrice", getDesposalPrice())
                .append("customer", getCustomer())
                .toString();
    }
}