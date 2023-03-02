package com.ledao.system.dao;

import java.math.BigDecimal;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 估值计算对象 sys_valuationModel
 *
 * @author lxz
 * @date 2022-11-08
 */
public class SysValuationModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long valuationId;

    /**
     * 主表id
     */
    @Excel(name = "主表id")
    private Long mainId;

    /**
     * 网拍id
     */
    @Excel(name = "网拍id")
    private String itemId;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String itemType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String itemStatus;

    /**
     * 参考物建筑面积（m²）
     */
    @Excel(name = "参考物建筑面积", readConverterExp = "m=²")
    private BigDecimal building;

    /**
     * 参考物土地面积（m²）
     */
    @Excel(name = "参考物土地面积", readConverterExp = "m=²")
    private BigDecimal area;

    /**
     * 参考物有证建筑面积（m²）
     */
    @Excel(name = "参考物有证建筑面积", readConverterExp = "m=²")
    private BigDecimal building1;

    /**
     * 赋值单价（有证）
     */
    @Excel(name = "赋值单价", readConverterExp = "有=证")
    private BigDecimal price1;

    /**
     * 参考物无证建筑面积
     */
    @Excel(name = "参考物无证建筑面积")
    private BigDecimal building2;

    /**
     * 赋值单价（无证）
     */
    @Excel(name = "赋值单价", readConverterExp = "无=证")
    private BigDecimal price2;

    /**
     * 成交价
     */
    @Excel(name = "成交价")
    private BigDecimal itemCurrentPrice;

    /**
     * 评估价
     */
    private BigDecimal itemMarketPrice;

    /**
     * 建筑物单价
     */
    @Excel(name = "建筑物单价")
    private BigDecimal unitPrice;

    /**
     * 土地单价
     */
    @Excel(name = "土地单价")
    private BigDecimal areaUnitPrice;

    /**
     * 与参考物之间距离（米）
     */
    @Excel(name = "与参考物之间距离", readConverterExp = "米=")
    private Long latLon;

    /**
     * 标的物建筑面积（m²）
     */
    @Excel(name = "标的物建筑面积", readConverterExp = "m=²")
    private BigDecimal floorSpace;

    /**
     * 标的物土地面积（m²）
     */
    @Excel(name = "标的物土地面积", readConverterExp = "m=²")
    private BigDecimal areaSpace;

    /**
     * 标的物有证建筑面积（m²）
     */
    @Excel(name = "标的物有证建筑面积", readConverterExp = "m=²")
    private BigDecimal building3;

    /**
     * 赋值单价（有证）
     */
    @Excel(name = "赋值单价", readConverterExp = "有=证")
    private BigDecimal price3;

    /**
     * 标的物无证建筑面积（m²）
     */
    @Excel(name = "标的物无证建筑面积", readConverterExp = "m=²")
    private BigDecimal building4;

    /**
     * 赋值单价（无证）
     */
    @Excel(name = "赋值单价", readConverterExp = "无=证")
    private BigDecimal price4;

    /**
     * 系数1
     */
    @Excel(name = "系数1")
    private BigDecimal num1;

    /**
     * 系数2
     */
    @Excel(name = "系数2")
    private BigDecimal num2;

    /**
     * 总价
     */
    @Excel(name = "总价")
    private BigDecimal valuation;

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public void setValuationId(Long valuationId) {
        this.valuationId = valuationId;
    }

    public Long getValuationId() {
        return valuationId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setBuilding(BigDecimal building) {
        this.building = building;
    }

    public BigDecimal getBuilding() {
        return building;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setBuilding1(BigDecimal building1) {
        this.building1 = building1;
    }

    public BigDecimal getBuilding1() {
        return building1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setBuilding2(BigDecimal building2) {
        this.building2 = building2;
    }

    public BigDecimal getBuilding2() {
        return building2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setItemCurrentPrice(BigDecimal itemCurrentPrice) {
        this.itemCurrentPrice = itemCurrentPrice;
    }

    public BigDecimal getItemCurrentPrice() {
        return itemCurrentPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setAreaUnitPrice(BigDecimal areaUnitPrice) {
        this.areaUnitPrice = areaUnitPrice;
    }

    public BigDecimal getAreaUnitPrice() {
        return areaUnitPrice;
    }

    public void setLatLon(Long latLon) {
        this.latLon = latLon;
    }

    public Long getLatLon() {
        return latLon;
    }

    public void setFloorSpace(BigDecimal floorSpace) {
        this.floorSpace = floorSpace;
    }

    public BigDecimal getFloorSpace() {
        return floorSpace;
    }

    public void setAreaSpace(BigDecimal areaSpace) {
        this.areaSpace = areaSpace;
    }

    public BigDecimal getAreaSpace() {
        return areaSpace;
    }

    public void setBuilding3(BigDecimal building3) {
        this.building3 = building3;
    }

    public BigDecimal getBuilding3() {
        return building3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setBuilding4(BigDecimal building4) {
        this.building4 = building4;
    }

    public BigDecimal getBuilding4() {
        return building4;
    }

    public void setPrice4(BigDecimal price4) {
        this.price4 = price4;
    }

    public BigDecimal getPrice4() {
        return price4;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public void setValuation(BigDecimal valuation) {
        this.valuation = valuation;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public BigDecimal getItemMarketPrice() {
        return itemMarketPrice;
    }

    public void setItemMarketPrice(BigDecimal itemMarketPrice) {
        this.itemMarketPrice = itemMarketPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("valuationId", getValuationId())
                .append("itemId", getItemId())
                .append("itemId", getItemId())
                .append("itemType", getItemType())
                .append("itemStatus", getItemStatus())
                .append("building", getBuilding())
                .append("area", getArea())
                .append("building1", getBuilding1())
                .append("price1", getPrice1())
                .append("building2", getBuilding2())
                .append("price2", getPrice2())
                .append("itemCurrentPrice", getItemCurrentPrice())
                .append("unitPrice", getUnitPrice())
                .append("areaUnitPrice", getAreaUnitPrice())
                .append("latLon", getLatLon())
                .append("floorSpace", getFloorSpace())
                .append("areaSpace", getAreaSpace())
                .append("building3", getBuilding3())
                .append("price3", getPrice3())
                .append("building4", getBuilding4())
                .append("price4", getPrice4())
                .append("num1", getNum1())
                .append("num2", getNum2())
                .append("valuation", getValuation())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("itemMarketPrice", getItemMarketPrice())
                .toString();
    }
}
