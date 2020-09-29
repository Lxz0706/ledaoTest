package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectTargetrecoverMapper;
import com.ledao.system.dao.SysProjectTargetrecover;
import com.ledao.system.service.ISysProjectTargetrecoverService;
import com.ledao.common.core.text.Convert;

/**
 * 目标回收Service业务层处理
 *
 * @author ledao
 * @date 2020-09-03
 */
@Service
public class SysProjectTargetrecoverServiceImpl implements ISysProjectTargetrecoverService {
    @Autowired
    private SysProjectTargetrecoverMapper sysProjectTargetrecoverMapper;

    /**
     * 查询目标回收
     *
     * @param id 目标回收ID
     * @return 目标回收
     */
    @Override
    public SysProjectTargetrecover selectSysProjectTargetrecoverById(Long id) {
        return sysProjectTargetrecoverMapper.selectSysProjectTargetrecoverById(id);
    }

    /**
     * 查询目标回收列表
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 目标回收
     */
    @Override
    public List<SysProjectTargetrecover> selectSysProjectTargetrecoverList(SysProjectTargetrecover sysProjectTargetrecover) {
        return sysProjectTargetrecoverMapper.selectSysProjectTargetrecoverList(sysProjectTargetrecover);
    }

    /**
     * 新增目标回收
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 结果
     */
    @Override
    public int insertSysProjectTargetrecover(SysProjectTargetrecover sysProjectTargetrecover) {
        sysProjectTargetrecover.setCreateTime(DateUtils.getNowDate());
        return sysProjectTargetrecoverMapper.insertSysProjectTargetrecover(sysProjectTargetrecover);
    }

    /**
     * 修改目标回收
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 结果
     */
    @Override
    public int updateSysProjectTargetrecover(SysProjectTargetrecover sysProjectTargetrecover) {
        sysProjectTargetrecover.setUpdateTime(DateUtils.getNowDate());
        return sysProjectTargetrecoverMapper.updateSysProjectTargetrecover(sysProjectTargetrecover);
    }

    /**
     * 删除目标回收对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectTargetrecoverByIds(String ids) {
        return sysProjectTargetrecoverMapper.deleteSysProjectTargetrecoverByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除目标回收信息
     *
     * @param id 目标回收ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectTargetrecoverById(Long id) {
        return sysProjectTargetrecoverMapper.deleteSysProjectTargetrecoverById(id);
    }
}
