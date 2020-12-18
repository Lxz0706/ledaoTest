package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 名片对象 sys_namecard
 *
 * @author lxz
 * @date 2020-12-07
 */
public class SysNamecard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long cardId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String cardName;

    /**
     * 职位
     */
    @Excel(name = "职位")
    private String position;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phoneNumber;

    /**
     * 微信
     */
    @Excel(name = "微信")
    private String wechat;

    /**
     * 公司
     */
    @Excel(name = "公司")
    private String company;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;


    /**
     * 电话
     */
    @Excel(name = "电话")
    private String telephone;


    /**
     * 名片路径
     */
   // @Excel(name = "名片路径")
    private String avatar;

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWechat() {
        return wechat;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cardId", getCardId())
                .append("cardName", getCardName())
                .append("phoneNumber", getPhoneNumber())
                .append("company", getCompany())
                .append("address", getAddress())
                .append("position", getPosition())
                .append("telephone", getTelephone())
                .append("wechat", getWechat())
                .append("avatar", getAvatar())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
