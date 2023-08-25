package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 资产管理对象 sys_asset
 * 
 * @author lxz
 * @date 2023-06-16
 */
public class SysAsset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long assetId;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String assetName;

    /** null */
    @Excel(name = "null")
    private String managerId;

    /** null */
    @Excel(name = "null")
    private String managerName;

    public void setAssetId(Long assetId) 
    {
        this.assetId = assetId;
    }

    public Long getAssetId() 
    {
        return assetId;
    }
    public void setAssetName(String assetName) 
    {
        this.assetName = assetName;
    }

    public String getAssetName() 
    {
        return assetName;
    }
    public void setManagerId(String managerId) 
    {
        this.managerId = managerId;
    }

    public String getManagerId() 
    {
        return managerId;
    }
    public void setManagerName(String managerName) 
    {
        this.managerName = managerName;
    }

    public String getManagerName() 
    {
        return managerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("assetId", getAssetId())
            .append("assetName", getAssetName())
            .append("createTime", getCreateTime())
            .append("managerId", getManagerId())
            .append("managerName", getManagerName())
            .append("createBy", getCreateBy())
            .toString();
    }
}
