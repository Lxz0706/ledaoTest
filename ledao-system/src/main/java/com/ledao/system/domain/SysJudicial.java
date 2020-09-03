package com.ledao.system.domain;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 司法对象 sys_judicial
 *
 * @author ledao
 * @date 2020-08-26
 */
public class SysJudicial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 所有人
     */
    @Excel(name = "所有人")
    private String owner;

    /**
     * 拍卖物类型
     */
    @Excel(name = "拍卖物类型")
    private String auctionsType;

    /**
     * 网站链接
     */
    @Excel(name = "网站链接")
    private String websiteLinks;

    /**
     * 起拍价
     */
    @Excel(name = "起拍价")
    private String startPrice;

    /**
     * 评估价
     */
    @Excel(name = "评估价")
    private String appraisalPrice;

    /**
     * 当前/成交价
     */
    @Excel(name = "当前/成交价")
    private String finalPrice;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 省
     */
    @Excel(name = "省")
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    private String city;

    /**
     * 区或县
     */
    @Excel(name = "区或县")
    private String county;

    /**
     * 项目编号，和sys_judicial_table_data表一一对应
     */
    @Excel(name = "项目编号，和sys_judicial_table_data表一一对应")
    private String itemId;

    private String title;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setAuctionsType(String auctionsType) {
        this.auctionsType = auctionsType;
    }

    public String getAuctionsType() {
        return auctionsType;
    }

    public void setWebsiteLinks(String websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public String getWebsiteLinks() {
        return websiteLinks;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setAppraisalPrice(String appraisalPrice) {
        this.appraisalPrice = appraisalPrice;
    }

    public String getAppraisalPrice() {
        return appraisalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("owner", getOwner())
                .append("auctionsType", getAuctionsType())
                .append("websiteLinks", getWebsiteLinks())
                .append("startPrice", getStartPrice())
                .append("appraisalPrice", getAppraisalPrice())
                .append("finalPrice", getFinalPrice())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("status", getStatus())
                .append("province", getProvince())
                .append("city", getCity())
                .append("county", getCounty())
                .append("itemId", getItemId())
                .append("title",getTitle())
                .toString();
    }
}