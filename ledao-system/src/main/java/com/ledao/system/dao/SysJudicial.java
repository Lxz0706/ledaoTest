package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.elasticsearch.annotations.Document;

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
    //@Excel(name = "项目id")
    private String itemId;

    /**
     * 项目title
     */
    //@Excel(name = "项目title")
    private String itemTitle;

    /**
     * 项目所有人
     */
    //@Excel(name = "项目所有人")
    private String itemOwner;

    /**
     * 项目类型
     */
    // @Excel(name = "项目类型")
    private String itemType;

    /**
     * 项目状态
     */
    //@Excel(name = "项目状态")
    private String itemStatus;

    /**
     * 项目网址
     */
    //@Excel(name = "项目网址")
    private String itemLink;

    /**
     * 项目起拍价
     */
    //@Excel(name = "项目起拍价")
    private BigDecimal itemInitialprice;

    /**
     * 项目当前价
     */
    //@Excel(name = "项目当前价")
    private BigDecimal itemCurrentprice;

    /**
     * 项目评估价
     */
    //@Excel(name = "项目评估价")
    private BigDecimal itemConsultprice;

    /**
     * 项目市场价
     */
    //@Excel(name = "项目市场价")
    private BigDecimal itemMarketprice;

    /**
     * 项目开始时间
     */
    //@Excel(name = "项目开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemStartTime;

    /**
     * 项目结束时间
     */
    //@Excel(name = "项目结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date itemEndTime;

    /**
     * 项目所在省
     */
    //@Excel(name = "项目所在省")
    private String itemProvince;

    /**
     * 项目所在市
     */
    //@Excel(name = "项目所在市")
    private String itemCity;

    /**
     * 项目所在区(县)
     */
    //@Excel(name = "项目所在区(县)")
    private String itemCounty;

    /**
     * 项目来源
     */
    //@Excel(name = "项目来源")
    private String itemSource;

    private String taggings;

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    private Integer totalCount;

    private Integer pageNumber;

    private Integer pageSizeNum;

    /**
     * 高德地图坐标
     */
    //@Excel(name = "高德地图坐标")
    private String coordinate;

    /**
     * 阿里标签
     */
    //@Excel(name = "阿里标签")
    private String tags;

    /**
     * 处置单位
     */
    //@Excel(name = "处置单位")
    private String shopname;

    /**
     * 报名人数
     */
    //@Excel(name = "报名人数")
    private Long applyer;

    /**
     * 设置提醒人数
     */
    //@Excel(name = "设置提醒人数")
    private Long notify;

    /**
     * 围观人数
     */
    //@Excel(name = "围观人数")
    private Long looker;

    /**
     * 标的物位置
     */
    //@Excel(name = "标的物位置")
    private String address;

    /**
     * 采集时间
     */
    //@Excel(name = "采集时间")
    private Date timestamp;

    @Excel(name = "是否范围内")
    private boolean flag;

    @Excel(name = "圆范围")
    private String radiues;

    @Excel(name = "圆心经度")
    private double lng1;

    @Excel(name = "圆心纬度")
    private double lat1;

    @Excel(name = "转换后经度")
    private double lng2;

    @Excel(name = "转换后纬度")
    private double lat2;

    @Excel(name = "itemId")
    private String itemIds;

    /**
     * 建筑面积
     */
    private Long floorSpace;

    /**
     * 圆点坐标
     */
    public String latLon;

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

    public String getTaggings() {
        return taggings;
    }

    public void setTaggings(String taggings) {
        this.taggings = taggings;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSizeNum() {
        return pageSizeNum;
    }

    public void setPageSizeNum(Integer pageSizeNum) {
        this.pageSizeNum = pageSizeNum;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Long getApplyer() {
        return applyer;
    }

    public void setApplyer(Long applyer) {
        this.applyer = applyer;
    }

    public Long getNotify() {
        return notify;
    }

    public void setNotify(Long notify) {
        this.notify = notify;
    }

    public Long getLooker() {
        return looker;
    }

    public void setLooker(Long looker) {
        this.looker = looker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRadiues() {
        return radiues;
    }

    public void setRadiues(String radiues) {
        this.radiues = radiues;
    }

    public double getLng1() {
        return lng1;
    }

    public void setLng1(double lng1) {
        this.lng1 = lng1;
    }

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLng2() {
        return lng2;
    }

    public void setLng2(double lng2) {
        this.lng2 = lng2;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public Long getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(Long floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
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
                .append("coordinate", getCoordinate())
                .append("tags", getTags())
                .append("shopname", getShopname())
                .append("applyer", getApplyer())
                .append("notify", getNotify())
                .append("looker", getLooker())
                .append("address", getAddress())
                .append("timestamp", getTimestamp())
                .append("floorSpace", getFloorSpace())
                .toString();
    }
}
