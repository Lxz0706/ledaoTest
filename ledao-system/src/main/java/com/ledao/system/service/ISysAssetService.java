package com.ledao.system.service;

import java.util.List;
import com.ledao.system.dao.SysAsset;

/**
 * 资产管理Service接口
 * 
 * @author lxz
 * @date 2023-06-16
 */
public interface ISysAssetService 
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
     * 批量删除资产管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAssetByIds(String ids);

    /**
     * 删除资产管理信息
     * 
     * @param assetId 资产管理ID
     * @return 结果
     */
    public int deleteSysAssetById(Long assetId);
}
