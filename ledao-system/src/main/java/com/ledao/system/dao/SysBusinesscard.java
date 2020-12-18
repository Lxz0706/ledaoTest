package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_businesscard
 *
 * @author lxz
 * @date 2020-12-07
 */
public class SysBusinesscard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * card_id
     */
    private Long id;

    /**
     * card_json数据
     */
    @Excel(name = "card_json数据")
    private String cardData;

    /**
     * card_base64
     */
    @Excel(name = "card_base64")
    private String cardBase64;

    /**
     * card_imageid
     */
    @Excel(name = "card_imageid")
    private String cardImageid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCardData(String cardData) {
        this.cardData = cardData;
    }

    public String getCardData() {
        return cardData;
    }

    public void setCardBase64(String cardBase64) {
        this.cardBase64 = cardBase64;
    }

    public String getCardBase64() {
        return cardBase64;
    }

    public void setCardImageid(String cardImageid) {
        this.cardImageid = cardImageid;
    }

    public String getCardImageid() {
        return cardImageid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("cardData", getCardData())
                .append("cardBase64", getCardBase64())
                .append("cardImageid", getCardImageid())
                .toString();
    }
}
