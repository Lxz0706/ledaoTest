package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 司法拍卖项目对象 sys_judicial
 *
 * @author ledao
 * @date 2020-09-14
 */
public class SysJudicial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private String itemId;

    /**
     * 项目title
     */
    @Excel(name = "项目title")
    private String itemTitle;

    /**
     * 项目所有人
     */
    @Excel(name = "项目所有人")
    private String itemOwner;

    /**
     * 项目类型
     */
    @Excel(name = "项目类型")
    private String itemType;

    /**
     * 项目状态
     */
    @Excel(name = "项目状态")
    private String itemStatus;

    /**
     * 项目网址
     */
    @Excel(name = "项目网址")
    private String itemLink;

    /**
     * 项目起拍价
     */
    @Excel(name = "项目起拍价")
    private BigDecimal itemInitialprice;

    /**
     * 项目当前价
     */
    @Excel(name = "项目当前价")
    private BigDecimal itemCurrentprice;

    /**
     * 项目评估价
     */
    @Excel(name = "项目评估价")
    private BigDecimal itemConsultprice;

    /**
     * 项目市场价
     */
    @Excel(name = "项目市场价")
    private BigDecimal itemMarketprice;

    /**
     * 项目开始时间
     */
    @Excel(name = "项目开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemStartTime;

    /**
     * 项目结束时间
     */
    @Excel(name = "项目结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemEndTime;

    /**
     * 项目所在省
     */
    @Excel(name = "项目所在省")
    private String itemProvince;

    /**
     * 项目所在市
     */
    @Excel(name = "项目所在市")
    private String itemCity;

    /**
     * 项目所在区(县)
     */
    @Excel(name = "项目所在区(县)")
    private String itemCounty;

    /**
     * 项目来源
     */
    @Excel(name = "项目来源")
    private String itemSource;

    private String tagging;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemOwner(String itemOwner) {
        this.itemOwner = itemOwner;
    }

    public String getItemOwner() {
        return itemOwner;
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

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemInitialprice(BigDecimal itemInitialprice) {
        this.itemInitialprice = itemInitialprice;
    }

    public BigDecimal getItemInitialprice() {
        return itemInitialprice;
    }

    public void setItemCurrentprice(BigDecimal itemCurrentprice) {
        this.itemCurrentprice = itemCurrentprice;
    }

    public BigDecimal getItemCurrentprice() {
        return itemCurrentprice;
    }

    public void setItemConsultprice(BigDecimal itemConsultprice) {
        this.itemConsultprice = itemConsultprice;
    }

    public BigDecimal getItemConsultprice() {
        return itemConsultprice;
    }

    public void setItemMarketprice(BigDecimal itemMarketprice) {
        this.itemMarketprice = itemMarketprice;
    }

    public BigDecimal getItemMarketprice() {
        return itemMarketprice;
    }

    public void setItemStartTime(Date itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    public Date getItemStartTime() {
        return itemStartTime;
    }

    public void setItemEndTime(Date itemEndTime) {
        this.itemEndTime = itemEndTime;
    }

    public Date getItemEndTime() {
        return itemEndTime;
    }

    public void setItemProvince(String itemProvince) {
        this.itemProvince = itemProvince;
    }

    public String getItemProvince() {
        return itemProvince;
    }

    public void setItemCity(String itemCity) {
        this.itemCity = itemCity;
    }

    public String getItemCity() {
        return itemCity;
    }

    public void setItemCounty(String itemCounty) {
        this.itemCounty = itemCounty;
    }

    public String getItemCounty() {
        return itemCounty;
    }

    public void setItemSource(String itemSource) {
        this.itemSource = itemSource;
    }

    public String getItemSource() {
        return itemSource;
    }

    public String getTagging() {
        return tagging;
    }

    public void setTagging(String tagging) {
        this.tagging = tagging;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("itemId", getItemId())
                .append("itemTitle", getItemTitle())
                .append("itemOwner", getItemOwner())
                .append("itemType", getItemType())
                .append("itemStatus", getItemStatus())
                .append("itemLink", getItemLink())
                .append("itemInitialprice", getItemInitialprice())
                .append("itemCurrentprice", getItemCurrentprice())
                .append("itemConsultprice", getItemConsultprice())
                .append("itemMarketprice", getItemMarketprice())
                .append("itemStartTime", getItemStartTime())
                .append("itemEndTime", getItemEndTime())
                .append("itemProvince", getItemProvince())
                .append("itemCity", getItemCity())
                .append("itemCounty", getItemCounty())
                .append("itemSource", getItemSource())
                .append("tagging", getTagging())
                .toString();
    }
}
