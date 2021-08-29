package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 签到人员对象 sys_train_user
 * 
 * @author lxz
 * @date 2021-08-29
 */
public class SysTrainUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 培训ID */
    @Excel(name = "培训ID")
    private Long trainId;

    /** 参加人 */
    @Excel(name = "参加人")
    private String userName;

    /** 参加人 */
    @Excel(name = "参加人")
    private String loginName;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String remark1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String remark2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String remark3;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTrainId(Long trainId) 
    {
        this.trainId = trainId;
    }

    public Long getTrainId() 
    {
        return trainId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setRemark1(String remark1) 
    {
        this.remark1 = remark1;
    }

    public String getRemark1() 
    {
        return remark1;
    }
    public void setRemark2(String remark2) 
    {
        this.remark2 = remark2;
    }

    public String getRemark2() 
    {
        return remark2;
    }
    public void setRemark3(String remark3) 
    {
        this.remark3 = remark3;
    }

    public String getRemark3() 
    {
        return remark3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("trainId", getTrainId())
            .append("userName", getUserName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark1", getRemark1())
            .append("remark2", getRemark2())
            .append("remark3", getRemark3())
            .toString();
    }
}
