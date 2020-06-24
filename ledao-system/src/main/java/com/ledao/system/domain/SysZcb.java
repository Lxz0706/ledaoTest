package com.ledao.system.domain;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 资产包对象 sys_zcb
 *
 * @author lxz
 * @date 2020-06-11
 */
public class SysZcb extends BaseEntity {
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
     * 资产包状态
     */
    @Excel(name = "资产包状态")
    private String assetStatus;

    private BigDecimal collateralTotal;

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

    public BigDecimal getCollateralTotal() {
        return collateralTotal;
    }

    public void setCollateralTotal(BigDecimal collateralTotal) {
        this.collateralTotal = collateralTotal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("assetPackageName", getAssetPackageName())
                .append("assetStatus", getAssetStatus())
                .toString();
    }
}
