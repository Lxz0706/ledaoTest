package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 购房人基本信息对象 sys_buyer
 *
 * @author lxz
 * @date 2023-12-27
 */
public class SysBuyer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long buyerId;

    /**
     * 房号
     */
    @Excel(name = "房号")
    private String roomNumber;

    /**
     * 购房人
     */
    @Excel(name = "购房人")
    private String buyerName;

    /**
     * 购房人身份证号
     */
    @Excel(name = "购房人身份证号")
    private String buyerCard;

    /**
     * 购房人手机号
     */
    @Excel(name = "购房人手机号")
    private String buyerPhoneNumer;

    /**
     * 购房人地址
     */
    @Excel(name = "购房人地址")
    private String buyerAddress;

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerCard(String buyerCard) {
        this.buyerCard = buyerCard;
    }

    public String getBuyerCard() {
        return buyerCard;
    }

    public void setBuyerPhoneNumer(String buyerPhoneNumer) {
        this.buyerPhoneNumer = buyerPhoneNumer;
    }

    public String getBuyerPhoneNumer() {
        return buyerPhoneNumer;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("buyerId", getBuyerId())
                .append("roomNumber", getRoomNumber())
                .append("buyerName", getBuyerName())
                .append("buyerCard", getBuyerCard())
                .append("buyerPhoneNumer", getBuyerPhoneNumer())
                .append("buyerAddress", getBuyerAddress())
                .toString();
    }
}
