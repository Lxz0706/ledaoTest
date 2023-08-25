package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysAssetMapper;
import com.ledao.system.dao.SysAsset;
import com.ledao.system.service.ISysAssetService;
import com.ledao.common.core.text.Convert;

/**
 * 资产管理Service业务层处理
 * 
 * @author lxz
 * @date 2023-06-16
 */
@Service
public class SysAssetServiceImpl implements ISysAssetService 
{
    @Autowired
    private SysAssetMapper sysAssetMapper;

    /**
     * 查询资产管理
     * 
     * @param assetId 资产管理ID
     * @return 资产管理
     */
    @Override
    public SysAsset selectSysAssetById(Long assetId)
    {
        return sysAssetMapper.selectSysAssetById(assetId);
    }

    /**
     * 查询资产管理列表
     * 
     * @param sysAsset 资产管理
     * @return 资产管理
     */
    @Override
    public List<SysAsset> selectSysAssetList(SysAsset sysAsset)
    {
        return sysAssetMapper.selectSysAssetList(sysAsset);
    }

    /**
     * 新增资产管理
     * 
     * @param sysAsset 资产管理
     * @return 结果
     */
    @Override
    public int insertSysAsset(SysAsset sysAsset)
    {
        sysAsset.setCreateTime(DateUtils.getNowDate());
        return sysAssetMapper.insertSysAsset(sysAsset);
    }

    /**
     * 修改资产管理
     * 
     * @param sysAsset 资产管理
     * @return 结果
     */
    @Override
    public int updateSysAsset(SysAsset sysAsset)
    {
        return sysAssetMapper.updateSysAsset(sysAsset);
    }

    /**
     * 删除资产管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAssetByIds(String ids)
    {
        return sysAssetMapper.deleteSysAssetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资产管理信息
     * 
     * @param assetId 资产管理ID
     * @return 结果
     */
    @Override
    public int deleteSysAssetById(Long assetId)
    {
        return sysAssetMapper.deleteSysAssetById(assetId);
    }
}
