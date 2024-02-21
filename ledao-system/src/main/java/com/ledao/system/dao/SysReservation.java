package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 预约对象 sys_reservation
 *
 * @author lxz
 * @date 2023-11-17
 */
public class SysReservation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long reservationId;

    /**
     * 购买人名称
     */
    @Excel(name = "购买人名称")
    private String buyerName;

    /**
     * 预约时间
     */
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reservationTime;

    /**
     * 二维码创建人id
     */
    @Excel(name = "二维码创建人id")
    private Long userId;

    /**
     * 购买人身份证号
     */
    @Excel(name = "购买人身份证号")
    private String buyerIdCard;

    /**
     * 购买人手机号
     */
    @Excel(name = "购买人手机号")
    private String buyerPhone;

    /**
     * 房号
     */
    @Excel(name = "房号")
    private String roomNumber;

    private Long createUserId;

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setbuyerIdCard(String buyerIdCard) {
        this.buyerIdCard = buyerIdCard;
    }

    public String getbuyerIdCard() {
        return buyerIdCard;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("reservationId", getReservationId())
                .append("buyerName", getBuyerName())
                .append("reservationTime", getReservationTime())
                .append("userId", getUserId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("buyerIdCard", getbuyerIdCard())
                .append("buyerPhone", getBuyerPhone())
                .append("roomNumber", getRoomNumber())
                .append("createUserId", getCreateUserId())
                .toString();
    }
}
