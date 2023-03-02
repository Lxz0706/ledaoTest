package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文书网对象 wenshu
 *
 * @author lxz
 * @date 2022-12-14
 */
public class wenShu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文书id
     */
    private String courtId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String courtTitle;

    /**
     * 案由
     */
    @Excel(name = "案由")
    private String courtCause;

    /**
     * 日期
     */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date courtDate;

    /**
     * 法院名称
     */
    @Excel(name = "法院名称")
    private String courtName;

    /**
     * 案号
     */
    @Excel(name = "案号")
    private String courtNo;

    private String itemCourtData;

    private String pdfPath;

    private String courtData;

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtTitle(String courtTitle) {
        this.courtTitle = courtTitle;
    }

    public String getCourtTitle() {
        return courtTitle;
    }

    public void setCourtCause(String courtCause) {
        this.courtCause = courtCause;
    }

    public String getCourtCause() {
        return courtCause;
    }

    public void setCourtDate(Date courtDate) {
        this.courtDate = courtDate;
    }

    public Date getCourtDate() {
        return courtDate;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtNo(String courtNo) {
        this.courtNo = courtNo;
    }

    public String getCourtNo() {
        return courtNo;
    }

    public String getItemCourtData() {
        return itemCourtData;
    }

    public void setItemCourtData(String itemCourtData) {
        this.itemCourtData = itemCourtData;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getCourtData() {
        return courtData;
    }

    public void setCourtData(String courtData) {
        this.courtData = courtData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("courtId", getCourtId())
                .append("courtTitle", getCourtTitle())
                .append("courtCause", getCourtCause())
                .append("courtDate", getCourtDate())
                .append("courtName", getCourtName())
                .append("courtNo", getCourtNo())
                .append("itemCourtData", getItemCourtData())
                .append("pdfPath", getPdfPath())
                .append("courtData", getCourtData())
                .toString();
    }
}
