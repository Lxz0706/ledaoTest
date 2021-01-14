package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 sys_project_type
 *
 * @author lxz
 * @date 2020-12-15
 */
public class SysProjectType {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String projectType;


    /*
     * 投资金额
     * */
    private BigDecimal investmentAmount;


    /**
     * 已支付金额
     */
    private BigDecimal yzfje;


    /**
     * 已回收金额
     */
    private BigDecimal yhsje;

    /**
     * 回现金额
     */
    private BigDecimal recapture;

    /**
     * 总已结算
     */
    private BigDecimal zyjs;

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
    }

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public BigDecimal getYzfje() {
        return yzfje;
    }

    public void setYzfje(BigDecimal yzfje) {
        this.yzfje = yzfje;
    }

    public BigDecimal getYhsje() {
        return yhsje;
    }

    public void setYhsje(BigDecimal yhsje) {
        this.yhsje = yhsje;
    }

    public BigDecimal getRecapture() {
        return recapture;
    }

    public void setRecapture(BigDecimal recapture) {
        this.recapture = recapture;
    }

    public BigDecimal getZyjs() {
        return zyjs;
    }

    public void setZyjs(BigDecimal zyjs) {
        this.zyjs = zyjs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectType", getProjectType())
                .toString();
    }
}
