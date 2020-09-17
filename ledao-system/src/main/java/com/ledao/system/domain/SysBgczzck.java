package com.ledao.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 重组并购项目信息库对象 sys_bgczzck
 *
 * @author ledao
 * @date 2020-06-17
 */
public class SysBgczzck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 项目状态
     */
    @Excel(name = "项目状态")
    private String projectStatus;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private String no;

    /**
     * SPV名称
     */
    @Excel(name = "SPV名称")
    private String spvName;

    /**
     * 城市/地区
     */
    @Excel(name = "城市/地区")
    private String city;

    /**
     * 取得成本
     */
    @Excel(name = "取得成本")
    private BigDecimal acquisitionCost;

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
    private Date bxjsDate;

    /**
     * 付息周期
     */
    @Excel(name = "付息周期")
    private String interestPaymentCycle;

    /**
     * 付息日
     */
    @Excel(name = "付息日", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date interestPaymentDate;

    /**
     * 付息日备注
     */
    @Excel(name = "付息日备注")
    private String interestPaymentDateRemark;

    /**
     * 融资方
     */
    @Excel(name = "融资方")
    private String finalParty;

    /**
     * 融资协议编号
     */
    @Excel(name = "融资协议编号")
    private String finalAgreementNo;

    /**
     * 融资期限
     */
    @Excel(name = "融资期限")
    private String finalTerm;

    /**
     * 协议名称
     */
    @Excel(name = "协议名称")
    private String agreementName;

    /**
     * 投前签署协议编号
     */
    @Excel(name = "投前签署协议编号")
    private String agreementNo;

    /**
     * 行业
     */
    //@Excel(name = "行业")
    private String indestry;

    /**
     * 投资人名称
     */
    @Excel(name = "投资人名称")
    private String investorName;

    /**
     * 经营情况
     */
    @Excel(name = "经营情况")
    private String operation;

    /**
     * 法律进度
     */
    @Excel(name = "法律进度")
    private String legalProgress;

    /**
     * 相关手续办理进度
     */
    @Excel(name = "相关手续办理进度")
    private String xgsxbljd;

    /**
     * 项目状态备注
     */
    @Excel(name = "项目状态备注")
    private String projectStatusComments;

    /**
     * 证照类型
     */
    @Excel(name = "证照类型")
    private String licenseType;

    /**
     * 证照编号
     */
    @Excel(name = "证照编号")
    private String licenseNo;

    /**
     * 取得时间
     */
    @Excel(name = "取得时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date acquisitionTime;

    /**
     * 证照信息
     */
    @Excel(name = "证照信息")
    private String licenseInformation;

    /**
     * 项目取得证照备注
     */
    @Excel(name = "项目取得证照备注")
    private String projectRemark;

    /**
     * 土地性质
     */
    @Excel(name = "土地性质")
    private String natureLand;

    /**
     * 土地用途
     */
    @Excel(name = "土地用途")
    private String zoing;

    /**
     * 剩余使用年限
     */
    @Excel(name = "剩余使用年限")
    private String sysynx;

    /**
     * 土地基本信息容积率
     */
    @Excel(name = "土地基本信息容积率")
    private String tdPlotRatio;

    /**
     * 获得方式
     */
    @Excel(name = "获得方式")
    private String access;

    /**
     * 备注
     * */
    @Excel(name = "备注")
    private String comment;

    /**
     * 合同概要
     */
    @Excel(name = "合同概要")
    private String contractSummary;


    /**
     * 合同金额
     */
    @Excel(name = "合同金额")
    private String contractAmount;

    /**
     * 项目运行中协议编号
     */
    @Excel(name = "项目运行中协议编号")
    private String projectYxzName;

    /**
     * 项目运行中编号
     */
    @Excel(name = "项目运行中编号")
    private String projectYxzNo;

    /**
     * 土地面积/㎡
     */
    @Excel(name = "土地面积/㎡")
    private String landArea;

    /**
     * 规划信息容积率
     */
    @Excel(name = "规划信息容积率")
    private String ghPlotRatio;

    /**
     * 建筑密度
     */
    @Excel(name = "建筑密度")
    private String buildDensity;

    /**
     * 绿化面积
     */
    @Excel(name = "绿化面积")
    private String afforestedArea;

    /**
     * 商业占比
     */
    @Excel(name = "商业占比")
    private String businessShare;

    /**
     * 规划建筑面积
     */
    @Excel(name = "规划建筑面积")
    private String planBuildArea;

    @Excel(name = "备注")
    private String remarks;

    /**
     * 土地单价(元/亩)
     */
    @Excel(name = "土地单价(元/亩)")
    private BigDecimal landUnitPrice;

    /**
     * 建筑估值
     */
    @Excel(name = "建筑估值")
    private String buildValuation;

    /**
     * 土地总价
     */
    @Excel(name = "土地总价")
    private BigDecimal landTotalPrice;

    /**
     * 其它附属物
     */
    @Excel(name = "其它附属物")
    private String otherAppurtenance;

    /**
     * 估值依据
     */
    @Excel(name = "估值依据")
    private String valuationBasis;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

    /**
     * 项目估值备注
     */
    @Excel(name = "项目估值备注")
    private String projectValuationRemarks;

    /**
     * 回现资金或退出资金进度
     */
    @Excel(name = "回现资金或退出资金进度")
    private String cashBack;

    /**
     * 回现金额
     */
    @Excel(name = "回现金额")
    private String cashBackAmount;

    /**
     * 还款能力分析（较好、一般、无）
     */
    @Excel(name = "还款能力分析（较好、一般、无）")
    private String repaymentCapacity;

    /**
     * 后期项目建议
     */
    @Excel(name = "后期项目建议")
    private String laterProject;

    /**
     * 退出计划备注
     */
    @Excel(name = "退出计划备注")
    private String laterProjectRemark;

    /**
     * 保证人
     */
    // @Excel(name = "保证人")
    private String guarantor;

    /**
     * 保证方式
     */
    // @Excel(name = "保证方式")
    private String guarantee;

    /**
     * 保证期间
     */
    // @Excel(name = "保证期间")
    private String guaranteePeriod;

    /**
     * 保证文件编号
     */
    // @Excel(name = "保证文件编号")
    private String guaranteeDocumentNo;

    /**
     * 资产性质
     */
    // @Excel(name = "资产性质")
    private String natureAssets;

    /**
     * 地址
     */
    // @Excel(name = "地址")
    private String address;

    /**
     * 权属证号
     */
    // @Excel(name = "权属证号")
    private String owerCertificateNo;

    /**
     * 面积·㎡
     */
    //@Excel(name = "面积·㎡")
    private String areaMeasure;

    /**
     * 国企/非国企
     */
    //@Excel(name = "国企/非国企")
    private String soe;

    private String beginTime;

    private String endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setSpvName(String spvName) {
        this.spvName = spvName;
    }

    public String getSpvName() {
        return spvName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getAcquisitionCost() {
        return acquisitionCost;
    }

    public void setAcquisitionCost(BigDecimal acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    public BigDecimal getContractPrincipal() {
        return contractPrincipal;
    }

    public void setContractPrincipal(BigDecimal contractPrincipal) {
        this.contractPrincipal = contractPrincipal;
    }

    public void setPrincipalBalance(BigDecimal principalBalance) {
        this.principalBalance = principalBalance;
    }

    public BigDecimal getPrincipalBalance() {
        return principalBalance;
    }

    public void setInterestBalance(BigDecimal interestBalance) {
        this.interestBalance = interestBalance;
    }

    public BigDecimal getInterestBalance() {
        return interestBalance;
    }

    public void setPrincipalInterestBalance(BigDecimal principalInterestBalance) {
        this.principalInterestBalance = principalInterestBalance;
    }

    public BigDecimal getPrincipalInterestBalance() {
        return principalInterestBalance;
    }

    public void setBxjsDate(Date bxjsDate) {
        this.bxjsDate = bxjsDate;
    }

    public Date getBxjsDate() {
        return bxjsDate;
    }

    public void setInterestPaymentCycle(String interestPaymentCycle) {
        this.interestPaymentCycle = interestPaymentCycle;
    }

    public String getInterestPaymentCycle() {
        return interestPaymentCycle;
    }

    public Date getInterestPaymentDate() {
        return interestPaymentDate;
    }

    public void setInterestPaymentDate(Date interestPaymentDate) {
        this.interestPaymentDate = interestPaymentDate;
    }

    public String getInterestPaymentDateRemark() {
        return interestPaymentDateRemark;
    }

    public void setInterestPaymentDateRemark(String interestPaymentDateRemark) {
        this.interestPaymentDateRemark = interestPaymentDateRemark;
    }

    public void setFinalParty(String finalParty) {
        this.finalParty = finalParty;
    }

    public String getFinalParty() {
        return finalParty;
    }

    public void setFinalAgreementNo(String finalAgreementNo) {
        this.finalAgreementNo = finalAgreementNo;
    }

    public String getFinalAgreementNo() {
        return finalAgreementNo;
    }

    public void setFinalTerm(String finalTerm) {
        this.finalTerm = finalTerm;
    }

    public String getFinalTerm() {
        return finalTerm;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setIndestry(String indestry) {
        this.indestry = indestry;
    }

    public String getIndestry() {
        return indestry;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setLegalProgress(String legalProgress) {
        this.legalProgress = legalProgress;
    }

    public String getLegalProgress() {
        return legalProgress;
    }

    public void setXgsxbljd(String xgsxbljd) {
        this.xgsxbljd = xgsxbljd;
    }

    public String getXgsxbljd() {
        return xgsxbljd;
    }

    public void setProjectStatusComments(String projectStatusComments) {
        this.projectStatusComments = projectStatusComments;
    }

    public String getProjectStatusComments() {
        return projectStatusComments;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setAcquisitionTime(Date acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public Date getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setLicenseInformation(String licenseInformation) {
        this.licenseInformation = licenseInformation;
    }

    public String getLicenseInformation() {
        return licenseInformation;
    }

    public void setProjectRemark(String projectRemark) {
        this.projectRemark = projectRemark;
    }

    public String getProjectRemark() {
        return projectRemark;
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

    public void setTdPlotRatio(String tdPlotRatio) {
        this.tdPlotRatio = tdPlotRatio;
    }

    public String getTdPlotRatio() {
        return tdPlotRatio;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public void setContractSummary(String contractSummary) {
        this.contractSummary = contractSummary;
    }

    public String getContractSummary() {
        return contractSummary;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setProjectYxzName(String projectYxzName) {
        this.projectYxzName = projectYxzName;
    }

    public String getProjectYxzName() {
        return projectYxzName;
    }

    public void setProjectYxzNo(String projectYxzNo) {
        this.projectYxzNo = projectYxzNo;
    }

    public String getProjectYxzNo() {
        return projectYxzNo;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setGhPlotRatio(String ghPlotRatio) {
        this.ghPlotRatio = ghPlotRatio;
    }

    public String getGhPlotRatio() {
        return ghPlotRatio;
    }

    public void setBuildDensity(String buildDensity) {
        this.buildDensity = buildDensity;
    }

    public String getBuildDensity() {
        return buildDensity;
    }

    public void setAfforestedArea(String afforestedArea) {
        this.afforestedArea = afforestedArea;
    }

    public String getAfforestedArea() {
        return afforestedArea;
    }

    public void setBusinessShare(String businessShare) {
        this.businessShare = businessShare;
    }

    public String getBusinessShare() {
        return businessShare;
    }

    public void setPlanBuildArea(String planBuildArea) {
        this.planBuildArea = planBuildArea;
    }

    public String getPlanBuildArea() {
        return planBuildArea;
    }

    public void setLandUnitPrice(BigDecimal landUnitPrice) {
        this.landUnitPrice = landUnitPrice;
    }

    public BigDecimal getLandUnitPrice() {
        return landUnitPrice;
    }

    public void setBuildValuation(String buildValuation) {
        this.buildValuation = buildValuation;
    }

    public String getBuildValuation() {
        return buildValuation;
    }

    public void setLandTotalPrice(BigDecimal landTotalPrice) {
        this.landTotalPrice = landTotalPrice;
    }

    public BigDecimal getLandTotalPrice() {
        return landTotalPrice;
    }

    public void setOtherAppurtenance(String otherAppurtenance) {
        this.otherAppurtenance = otherAppurtenance;
    }

    public String getOtherAppurtenance() {
        return otherAppurtenance;
    }

    public void setValuationBasis(String valuationBasis) {
        this.valuationBasis = valuationBasis;
    }

    public String getValuationBasis() {
        return valuationBasis;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setProjectValuationRemarks(String projectValuationRemarks) {
        this.projectValuationRemarks = projectValuationRemarks;
    }

    public String getProjectValuationRemarks() {
        return projectValuationRemarks;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBackAmount(String cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }

    public String getCashBackAmount() {
        return cashBackAmount;
    }

    public void setRepaymentCapacity(String repaymentCapacity) {
        this.repaymentCapacity = repaymentCapacity;
    }

    public String getRepaymentCapacity() {
        return repaymentCapacity;
    }

    public void setLaterProject(String laterProject) {
        this.laterProject = laterProject;
    }

    public String getLaterProject() {
        return laterProject;
    }

    public void setLaterProjectRemark(String laterProjectRemark) {
        this.laterProjectRemark = laterProjectRemark;
    }

    public String getLaterProjectRemark() {
        return laterProjectRemark;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteeDocumentNo(String guaranteeDocumentNo) {
        this.guaranteeDocumentNo = guaranteeDocumentNo;
    }

    public String getGuaranteeDocumentNo() {
        return guaranteeDocumentNo;
    }

    public void setNatureAssets(String natureAssets) {
        this.natureAssets = natureAssets;
    }

    public String getNatureAssets() {
        return natureAssets;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setOwerCertificateNo(String owerCertificateNo) {
        this.owerCertificateNo = owerCertificateNo;
    }

    public String getOwerCertificateNo() {
        return owerCertificateNo;
    }

    public void setAreaMeasure(String areaMeasure) {
        this.areaMeasure = areaMeasure;
    }

    public String getAreaMeasure() {
        return areaMeasure;
    }

    public void setSoe(String soe) {
        this.soe = soe;
    }

    public String getSoe() {
        return soe;
    }

    public void setZoing(String zoing) {
        this.zoing = zoing;
    }

    public String getZoing() {
        return zoing;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectName", getProjectName())
                .append("projectStatus", getProjectStatus())
                .append("no", getNo())
                .append("spvName", getSpvName())
                .append("city", getCity())
                .append("acquisitionCost", getAcquisitionCost())
                .append("contractPrincipal", getContractPrincipal())
                .append("principalBalance", getPrincipalBalance())
                .append("interestBalance", getInterestBalance())
                .append("principalInterestBalance", getPrincipalInterestBalance())
                .append("bxjsDate", getBxjsDate())
                .append("interestPaymentCycle", getInterestPaymentCycle())
                .append("interestPaymentDate", getInterestPaymentDate())
                .append("interestPaymentDateRemark", getInterestPaymentDateRemark())
                .append("finalParty", getFinalParty())
                .append("finalAgreementNo", getFinalAgreementNo())
                .append("finalTerm", getFinalTerm())
                .append("agreementName", getAgreementName())
                .append("agreementNo", getAgreementNo())
                .append("indestry", getIndestry())
                .append("investorName", getInvestorName())
                .append("operation", getOperation())
                .append("legalProgress", getLegalProgress())
                .append("xgsxbljd", getXgsxbljd())
                .append("projectStatusComments", getProjectStatusComments())
                .append("licenseType", getLicenseType())
                .append("licenseNo", getLicenseNo())
                .append("acquisitionTime", getAcquisitionTime())
                .append("licenseInformation", getLicenseInformation())
                .append("projectRemark", getProjectRemark())
                .append("natureLand", getNatureLand())
                .append("sysynx", getSysynx())
                .append("tdPlotRatio", getTdPlotRatio())
                .append("access", getAccess())
                .append("contractSummary", getContractSummary())
                .append("contractAmount", getContractAmount())
                .append("projectYxzName", getProjectYxzName())
                .append("projectYxzNo", getProjectYxzNo())
                .append("landArea", getLandArea())
                .append("ghPlotRatio", getGhPlotRatio())
                .append("buildDensity", getBuildDensity())
                .append("afforestedArea", getAfforestedArea())
                .append("businessShare", getBusinessShare())
                .append("planBuildArea", getPlanBuildArea())
                .append("landUnitPrice", getLandUnitPrice())
                .append("buildValuation", getBuildValuation())
                .append("landTotalPrice", getLandTotalPrice())
                .append("otherAppurtenance", getOtherAppurtenance())
                .append("valuationBasis", getValuationBasis())
                .append("totalPrice", getTotalPrice())
                .append("projectValuationRemarks", getProjectValuationRemarks())
                .append("cashBack", getCashBack())
                .append("cashBackAmount", getCashBackAmount())
                .append("repaymentCapacity", getRepaymentCapacity())
                .append("laterProject", getLaterProject())
                .append("laterProjectRemark", getLaterProjectRemark())
                .append("guarantor", getGuarantor())
                .append("guarantee", getGuarantee())
                .append("guaranteePeriod", getGuaranteePeriod())
                .append("guaranteeDocumentNo", getGuaranteeDocumentNo())
                .append("natureAssets", getNatureAssets())
                .append("address", getAddress())
                .append("owerCertificateNo", getOwerCertificateNo())
                .append("areaMeasure", getAreaMeasure())
                .append("soe", getSoe())
                .append("zoing", getZoing())
                .toString();
    }
}