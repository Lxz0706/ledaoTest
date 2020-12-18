package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 星标库对象 sys_tagging
 *
 * @author lxz
 * @date 2020-11-03
 */
public class SysTagging extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 主表ID
     */
    @Excel(name = "主表ID")
    private Long judicialId;

    //标题
    private String title;

    //所有人
    private String name;

    //网址
    private String link;

    //来源
    private String source;

    //类型
    private String itemType;

    //起拍价
    private BigDecimal itemInitialprice;

    /**
     * 项目当前价
     */
    private BigDecimal itemCurrentprice;

    /**
     * 项目评估价
     */
    private BigDecimal itemConsultprice;

    /**
     * 项目市场价
     */
    private BigDecimal itemMarketprice;

    /**
     * 项目开始时间
     */
    private Date itemStartTime;

    /**
     * 项目结束时间
     */
    private Date itemEndTime;

    /**
     * 项目状态
     * */
    private String itemStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJudicialId() {
        return judicialId;
    }

    public void setJudicialId(Long judicialId) {
        this.judicialId = judicialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getItemInitialprice() {
        return itemInitialprice;
    }

    public void setItemInitialprice(BigDecimal itemInitialprice) {
        this.itemInitialprice = itemInitialprice;
    }

    public BigDecimal getItemCurrentprice() {
        return itemCurrentprice;
    }

    public void setItemCurrentprice(BigDecimal itemCurrentprice) {
        this.itemCurrentprice = itemCurrentprice;
    }

    public BigDecimal getItemConsultprice() {
        return itemConsultprice;
    }

    public void setItemConsultprice(BigDecimal itemConsultprice) {
        this.itemConsultprice = itemConsultprice;
    }

    public BigDecimal getItemMarketprice() {
        return itemMarketprice;
    }

    public void setItemMarketprice(BigDecimal itemMarketprice) {
        this.itemMarketprice = itemMarketprice;
    }

    public Date getItemStartTime() {
        return itemStartTime;
    }

    public void setItemStartTime(Date itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    public Date getItemEndTime() {
        return itemEndTime;
    }

    public void setItemEndTime(Date itemEndTime) {
        this.itemEndTime = itemEndTime;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("judicialId", getJudicialId())
                .toString();
    }
}
