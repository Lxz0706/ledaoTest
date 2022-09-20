package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订阅号用户信息对象 sys_subscribe
 *
 * @author lxz
 * @date 2022-08-23
 */
public class SysSubscribe extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 是否订阅公众号(0 表示为关注该公众号)
     */
    @Excel(name = "是否订阅公众号(0 表示为关注该公众号)")
    private String subscribe;

    /**
     * 用户标识
     */
    @Excel(name = "用户标识")
    private String openId;

    /**
     * 语言（zh_CN 简体 zh_TW繁体 en 英语）
     */
    @Excel(name = "语言", readConverterExp = "z=h_CN,简=体,z=h_TW繁体,e=n,英=语")
    private String language;

    /**
     * 关注时间
     */
    @Excel(name = "关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /**
     * 公众号id
     */
    @Excel(name = "公众号id")
    private String unionId;

    /**
     * 分组ID
     */
    @Excel(name = "分组ID")
    private Integer groupId;

    /**
     * 标签ID列表
     */
    @Excel(name = "标签ID列表")
    private String tagridId;

    /**
     * 用户关注渠道
     */
    @Excel(name = "用户关注渠道")
    private String subscribeScene;

    /**
     * 二维码扫描场景
     */
    @Excel(name = "二维码扫描场景")
    private String qrScene;

    /**
     * 二维码扫描场景描述
     */
    @Excel(name = "二维码扫描场景描述")
    private String qrSceneStr;

    /**
     * 性别（男 1 女 2 0 未知）
     */
    @Excel(name = "性别", readConverterExp = "男=,1=,女=,2=,0=,未=知")
    private String sex;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * 所在国家
     */
    @Excel(name = "所在国家")
    private String country;

    /**
     * 所在省
     */
    @Excel(name = "所在省")
    private String province;

    /**
     * 所在城市
     */
    @Excel(name = "所在城市")
    private String city;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String headimgUrl;

    /**
     * 订阅号、服务号（0=订阅号，1=服务号）
     */
    private String subscribeType;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setTagridId(String tagridId) {
        this.tagridId = tagridId;
    }

    public String getTagridId() {
        return tagridId;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrScene() {
        return qrScene;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
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

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public String getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(String subscribeType) {
        this.subscribeType = subscribeType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("subscribe", getSubscribe())
                .append("openId", getOpenId())
                .append("language", getLanguage())
                .append("subscribeTime", getSubscribeTime())
                .append("unionId", getUnionId())
                .append("remark", getRemark())
                .append("groupId", getGroupId())
                .append("tagridId", getTagridId())
                .append("subscribeScene", getSubscribeScene())
                .append("qrScene", getQrScene())
                .append("qrSceneStr", getQrSceneStr())
                .append("sex", getSex())
                .append("nickName", getNickName())
                .append("country", getCountry())
                .append("province", getProvince())
                .append("city", getCity())
                .append("headimgUrl", getHeadimgUrl())
                .append("subscribeType", getSubscribeType())
                .toString();
    }
}
