package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysAsset;

/**
 * 资产管理Mapper接口
 * 
 * @author lxz
 * @date 2023-06-16
 */
public interface SysAssetMapper 
{
    /**
     * 查询资产管理
     * 
     * @param assetId 资产管理ID
     * @return 资产管理
     */
    public SysAsset selectSysAssetById(Long assetId);

    /**
     * 查询资产管理列表
     * 
     * @param sysAsset 资产管理
     * @return 资产管理集合
     */
    public List<SysAsset> selectSysAssetList(SysAsset sysAsset);

    /**
     * 新增资产管理
     * 
     * @param sysAsset 资产管理
     * @return 结果
     */
    public int insertSysAsset(SysAsset sysAsset);

    /**
     * 修改资产管理
     * 
     * @param sysAsset 资产管理
     * @return 结果
     */
    public int updateSysAsset(SysAsset sysAsset);

    /**
     * 删除资产管理
     * 
     * @param assetId 资产管理ID
     * @return 结果
     */
    public int deleteSysAssetById(Long assetId);

    /**
     * 批量删除资产管理
     * 
     * @param assetIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAssetByIds(String[] assetIds);
}
