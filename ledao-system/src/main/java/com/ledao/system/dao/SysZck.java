package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 资产信息库对象 sys_zck
 *
 * @author lxz
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
    //@Excel(name = "资产包名称")
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
     * 合同本金
     */
    @Excel(name = "合同本金")
    private BigDecimal contractPrincipal;

    /**
     * 本金余额
     */
    @Excel(name = "本金余额")
    private BigDecimal principalBalance;

    /**
     * 利息余额
     */
    @Excel(name = "利息余额")
    private BigDecimal interestBalance;

    /**
     * 本息余额
     */
    @Excel(name = "本息余额")
    private BigDecimal principalInterestBalance;

    /**
     * 本息计算基准日
     */
    @Excel(name = "本息计算基准日", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @Excel(name = "借款期限", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    private BigDecimal guaranteeAmount;

    /**
     * 保证合同编号
     */
    @Excel(name = "保证合同编号")
    private String guaranteeContractNo;

    /**
     * 保证合同签订时间
     */
    @Excel(name = "保证合同签订时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date guaranteeContractSignTime;

    /**
     * 最高额保证金额
     */
    @Excel(name = "最高额保证金额")
    private BigDecimal maximumGuaranteeAmount;

    /**
     * 最高额保证合同·保函编号
     */
    @Excel(name = "最高额保证合同·保函编号")
    private String maximumGuaranteeContract;

    /**
     * 最高额保证合同签订时间
     */
    @Excel(name = "最高额保证合同签订时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @Excel(name = "其他资产性质")
    private String assetNature;

    /**
     * 住址
     */
    @Excel(name = "其他住址")
    private String address;

    /**
     * 权属证号
     */
    @Excel(name = "其他资产权属证号")
    private String qsNo;

    /**
     * 面积·㎡
     */
    @Excel(name = "其他资产面积·㎡")
    private String area;

    /**
     * 查封情况
     */
    @Excel(name = "其他资产查封情况")
    private String sealUp;

    /**
     * 抵押情况
     */
    @Excel(name = "其他资产抵押情况")
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
    private BigDecimal mortgageAmount;

    /**
     * 抵（质）押合同编号
     */
    @Excel(name = "一般抵（质）押合同编号")
    private String mortgageContractNo;

    /**
     * 抵（质）押合同签订时间
     */
    @Excel(name = "一般抵（质）押合同签订时间")
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
    @Excel(name = "最高额抵押合同签订时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    private BigDecimal qxswMortgeageAmount;

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
    private BigDecimal landArea;

    /**
     * 建筑结构
     */
    @Excel(name = "建筑结构")
    private String buildStructure;

    /**
     * 建成日期
     */
    @Excel(name = "建成日期", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date completionDate;

    /**
     * 所在层数/总层数
     */
    @Excel(name = "所在层数/总层数")
    private String floors;

    /**
     * 抵/置押物类型
     */
    @Excel(name = "抵/质押物类型")
    private String collateType;

    /**
     * 建筑面积/㎡
     */
    @Excel(name = "建筑面积/㎡")
    private BigDecimal floorage;

    /**
     * 土地单价
     */
    @Excel(name = "土地单价")
    private BigDecimal landUnitPrice;

    /**
     * 土地总价
     */
    @Excel(name = "土地总价")
    private BigDecimal landTotalPrice;

    /**
     * 其它抵置押物单价
     */
    @Excel(name = "其它抵质押物单价")
    private BigDecimal otherCollateralUnitPrice;

    /**
     * 其它抵置押物总价
     */
    @Excel(name = "其它抵质押物总价")
    private BigDecimal otherCollateralTotalPrice;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

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
    @Excel(name = "抵质押物备注")
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
    private BigDecimal zhhknl;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String jkrgzRemark;

    /**
     * 定价
     */
    @Excel(name = "定价")
    private BigDecimal price;

    /**
     * 处置方式
     */
    @Excel(name = "处置方式")
    private String desposalMode;

    /**
     * 处置价格
     */
    @Excel(name = "处置价格")
    private BigDecimal desposalPrice;

    /**
     * 客户
     */
    @Excel(name = "客户")
    private String customer;

    /**
     * 资产包ID
     */
    private Long zcbId;


    /**
     * 资产包状态
     * */
    private String zcbStatus;

    /**
     * 资产包名称
     * */
    private String zcbName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetPackageName() {
        return assetPackageName;
    }

    public void setAssetPackageName(String assetPackageName) {
        this.assetPackageName = assetPackageName;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Date getBxjsDate() {
        return bxjsDate;
    }

    public void setBxjsDate(Date bxjsDate) {
        this.bxjsDate = bxjsDate;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public Integer getBzrZrrNumber() {
        return bzrZrrNumber;
    }

    public void setBzrZrrNumber(Integer bzrZrrNumber) {
        this.bzrZrrNumber = bzrZrrNumber;
    }

    public Integer getBzrFrNumber() {
        return bzrFrNumber;
    }

    public void setBzrFrNumber(Integer bzrFrNumber) {
        this.bzrFrNumber = bzrFrNumber;
    }

    public BigDecimal getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public String getGuaranteeContractNo() {
        return guaranteeContractNo;
    }

    public void setGuaranteeContractNo(String guaranteeContractNo) {
        this.guaranteeContractNo = guaranteeContractNo;
    }

    public Date getGuaranteeContractSignTime() {
        return guaranteeContractSignTime;
    }

    public void setGuaranteeContractSignTime(Date guaranteeContractSignTime) {
        this.guaranteeContractSignTime = guaranteeContractSignTime;
    }

    public BigDecimal getMaximumGuaranteeAmount() {
        return maximumGuaranteeAmount;
    }

    public void setMaximumGuaranteeAmount(BigDecimal maximumGuaranteeAmount) {
        this.maximumGuaranteeAmount = maximumGuaranteeAmount;
    }

    public String getMaximumGuaranteeContract() {
        return maximumGuaranteeContract;
    }

    public void setMaximumGuaranteeContract(String maximumGuaranteeContract) {
        this.maximumGuaranteeContract = maximumGuaranteeContract;
    }

    public Date getMaximumGuaratnteeSignTime() {
        return maximumGuaratnteeSignTime;
    }

    public void setMaximumGuaratnteeSignTime(Date maximumGuaratnteeSignTime) {
        this.maximumGuaratnteeSignTime = maximumGuaratnteeSignTime;
    }

    public String getZzqDateTime() {
        return zzqDateTime;
    }

    public void setZzqDateTime(String zzqDateTime) {
        this.zzqDateTime = zzqDateTime;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getGuaranteeRemarks() {
        return guaranteeRemarks;
    }

    public void setGuaranteeRemarks(String guaranteeRemarks) {
        this.guaranteeRemarks = guaranteeRemarks;
    }

    public String getAssetNature() {
        return assetNature;
    }

    public void setAssetNature(String assetNature) {
        this.assetNature = assetNature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQsNo() {
        return qsNo;
    }

    public void setQsNo(String qsNo) {
        this.qsNo = qsNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSealUp() {
        return sealUp;
    }

    public void setSealUp(String sealUp) {
        this.sealUp = sealUp;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getJkrIndustry() {
        return jkrIndustry;
    }

    public void setJkrIndustry(String jkrIndustry) {
        this.jkrIndustry = jkrIndustry;
    }

    public String getSoe() {
        return soe;
    }

    public void setSoe(String soe) {
        this.soe = soe;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getLitigation() {
        return litigation;
    }

    public void setLitigation(String litigation) {
        this.litigation = litigation;
    }

    public String getJudicialRemark() {
        return judicialRemark;
    }

    public void setJudicialRemark(String judicialRemark) {
        this.judicialRemark = judicialRemark;
    }

    public String getDywAddress() {
        return dywAddress;
    }

    public void setDywAddress(String dywAddress) {
        this.dywAddress = dywAddress;
    }

    public BigDecimal getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageAmount(BigDecimal mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

    public String getMortgageContractNo() {
        return mortgageContractNo;
    }

    public void setMortgageContractNo(String mortgageContractNo) {
        this.mortgageContractNo = mortgageContractNo;
    }

    public Date getDzywqdTime() {
        return dzywqdTime;
    }

    public void setDzywqdTime(Date dzywqdTime) {
        this.dzywqdTime = dzywqdTime;
    }

    public Long getMaximumMortgageAmount() {
        return maximumMortgageAmount;
    }

    public void setMaximumMortgageAmount(Long maximumMortgageAmount) {
        this.maximumMortgageAmount = maximumMortgageAmount;
    }

    public String getMaximumMortgageContractNo() {
        return maximumMortgageContractNo;
    }

    public void setMaximumMortgageContractNo(String maximumMortgageContractNo) {
        this.maximumMortgageContractNo = maximumMortgageContractNo;
    }

    public Date getZgeContractTime() {
        return zgeContractTime;
    }

    public void setZgeContractTime(Date zgeContractTime) {
        this.zgeContractTime = zgeContractTime;
    }

    public String getDbzzqTime() {
        return dbzzqTime;
    }

    public void setDbzzqTime(String dbzzqTime) {
        this.dbzzqTime = dbzzqTime;
    }

    public String getMortgagor() {
        return mortgagor;
    }

    public void setMortgagor(String mortgagor) {
        this.mortgagor = mortgagor;
    }

    public String getZgeQsNo() {
        return zgeQsNo;
    }

    public void setZgeQsNo(String zgeQsNo) {
        this.zgeQsNo = zgeQsNo;
    }

    public String getTxQzNo() {
        return txQzNo;
    }

    public void setTxQzNo(String txQzNo) {
        this.txQzNo = txQzNo;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getMortgageRank() {
        return mortgageRank;
    }

    public void setMortgageRank(String mortgageRank) {
        this.mortgageRank = mortgageRank;
    }

    public BigDecimal getQxswMortgeageAmount() {
        return qxswMortgeageAmount;
    }

    public void setQxswMortgeageAmount(BigDecimal qxswMortgeageAmount) {
        this.qxswMortgeageAmount = qxswMortgeageAmount;
    }

    public String getRentalSituation() {
        return rentalSituation;
    }

    public void setRentalSituation(String rentalSituation) {
        this.rentalSituation = rentalSituation;
    }

    public String getBeforeOrAterAarrival() {
        return beforeOrAterAarrival;
    }

    public void setBeforeOrAterAarrival(String beforeOrAterAarrival) {
        this.beforeOrAterAarrival = beforeOrAterAarrival;
    }

    public String getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(String rentalDetails) {
        this.rentalDetails = rentalDetails;
    }

    public String getJuveniles() {
        return juveniles;
    }

    public void setJuveniles(String juveniles) {
        this.juveniles = juveniles;
    }

    public String getOnlyHouse() {
        return onlyHouse;
    }

    public void setOnlyHouse(String onlyHouse) {
        this.onlyHouse = onlyHouse;
    }

    public String getTengfang() {
        return tengfang;
    }

    public void setTengfang(String tengfang) {
        this.tengfang = tengfang;
    }

    public String getNatureLand() {
        return natureLand;
    }

    public void setNatureLand(String natureLand) {
        this.natureLand = natureLand;
    }

    public String getSysynx() {
        return sysynx;
    }

    public void setSysynx(String sysynx) {
        this.sysynx = sysynx;
    }

    public String getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(String plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getZoning() {
        return zoning;
    }

    public void setZoning(String zoning) {
        this.zoning = zoning;
    }

    public BigDecimal getLandArea() {
        return landArea;
    }

    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }

    public String getBuildStructure() {
        return buildStructure;
    }

    public void setBuildStructure(String buildStructure) {
        this.buildStructure = buildStructure;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getCollateType() {
        return collateType;
    }

    public void setCollateType(String collateType) {
        this.collateType = collateType;
    }

    public BigDecimal getFloorage() {
        return floorage;
    }

    public void setFloorage(BigDecimal floorage) {
        this.floorage = floorage;
    }

    public BigDecimal getLandUnitPrice() {
        return landUnitPrice;
    }

    public void setLandUnitPrice(BigDecimal landUnitPrice) {
        this.landUnitPrice = landUnitPrice;
    }

    public BigDecimal getLandTotalPrice() {
        return landTotalPrice;
    }

    public void setLandTotalPrice(BigDecimal landTotalPrice) {
        this.landTotalPrice = landTotalPrice;
    }

    public BigDecimal getOtherCollateralUnitPrice() {
        return otherCollateralUnitPrice;
    }

    public void setOtherCollateralUnitPrice(BigDecimal otherCollateralUnitPrice) {
        this.otherCollateralUnitPrice = otherCollateralUnitPrice;
    }

    public BigDecimal getOtherCollateralTotalPrice() {
        return otherCollateralTotalPrice;
    }

    public void setOtherCollateralTotalPrice(BigDecimal otherCollateralTotalPrice) {
        this.otherCollateralTotalPrice = otherCollateralTotalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getValuationBasis() {
        return valuationBasis;
    }

    public void setValuationBasis(String valuationBasis) {
        this.valuationBasis = valuationBasis;
    }

    public String getCapValue() {
        return capValue;
    }

    public void setCapValue(String capValue) {
        this.capValue = capValue;
    }

    public String getDzywRemark() {
        return dzywRemark;
    }

    public void setDzywRemark(String dzywRemark) {
        this.dzywRemark = dzywRemark;
    }

    public String getBorrowerWsqk() {
        return borrowerWsqk;
    }

    public void setBorrowerWsqk(String borrowerWsqk) {
        this.borrowerWsqk = borrowerWsqk;
    }

    public String getPropertyClues() {
        return propertyClues;
    }

    public void setPropertyClues(String propertyClues) {
        this.propertyClues = propertyClues;
    }

    public BigDecimal getZhhknl() {
        return zhhknl;
    }

    public void setZhhknl(BigDecimal zhhknl) {
        this.zhhknl = zhhknl;
    }

    public String getJkrgzRemark() {
        return jkrgzRemark;
    }

    public void setJkrgzRemark(String jkrgzRemark) {
        this.jkrgzRemark = jkrgzRemark;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDesposalMode() {
        return desposalMode;
    }

    public void setDesposalMode(String desposalMode) {
        this.desposalMode = desposalMode;
    }

    public BigDecimal getDesposalPrice() {
        return desposalPrice;
    }

    public void setDesposalPrice(BigDecimal desposalPrice) {
        this.desposalPrice = desposalPrice;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getZcbId() {
        return zcbId;
    }

    public void setZcbId(Long zcbId) {
        this.zcbId = zcbId;
    }

    public String getZcbStatus() {
        return zcbStatus;
    }

    public void setZcbStatus(String zcbStatus) {
        this.zcbStatus = zcbStatus;
    }

    public String getZcbName() {
        return zcbName;
    }

    public void setZcbName(String zcbName) {
        this.zcbName = zcbName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
                .append("zcbId", getZcbId())
                .append("delFlag",getDelFlag())
                .append("parentId",getParentId())
                .append("zcbStatus",getZcbStatus())
                .toString();
    }
}