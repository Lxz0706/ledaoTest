package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysValuationModelMapper;
import com.ledao.system.dao.SysValuationModel;
import com.ledao.system.service.ISysValuationModelService;
import com.ledao.common.core.text.Convert;

/**
 * 估值计算Service业务层处理
 *
 * @author lxz
 * @date 2022-11-08
 */
@Service
public class SysValuationModelServiceImpl implements ISysValuationModelService {
    @Autowired
    private SysValuationModelMapper sysValuationModelMapper;

    /**
     * 查询估值计算
     *
     * @param valuationId 估值计算ID
     * @return 估值计算
     */
    @Override
    public SysValuationModel selectSysValuationModelById(Long valuationId) {
        return sysValuationModelMapper.selectSysValuationModelById(valuationId);
    }

    /**
     * 查询估值计算列表
     *
     * @param sysValuationModel 估值计算
     * @return 估值计算
     */
    @Override
    public List<SysValuationModel> selectSysValuationModelList(SysValuationModel sysValuationModel) {
        return sysValuationModelMapper.selectSysValuationModelList(sysValuationModel);
    }

    /**
     * 新增估值计算
     *
     * @param sysValuationModel 估值计算
     * @return 结果
     */
    @Override
    public int insertSysValuationModel(SysValuationModel sysValuationModel) {
        sysValuationModel.setCreateTime(DateUtils.getNowDate());
        return sysValuationModelMapper.insertSysValuationModel(sysValuationModel);
    }

    /**
     * 修改估值计算
     *
     * @param sysValuationModel 估值计算
     * @return 结果
     */
    @Override
    public int updateSysValuationModel(SysValuationModel sysValuationModel) {
        sysValuationModel.setUpdateTime(DateUtils.getNowDate());
        return sysValuationModelMapper.updateSysValuationModel(sysValuationModel);
    }

    /**
     * 删除估值计算对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationModelByIds(String ids) {
        return sysValuationModelMapper.deleteSysValuationModelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除估值计算信息
     *
     * @param valuationId 估值计算ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationModelById(Long valuationId) {
        return sysValuationModelMapper.deleteSysValuationModelById(valuationId);
    }
}
