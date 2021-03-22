package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectZckMapper;
import com.ledao.system.dao.SysProjectZck;
import com.ledao.system.service.ISysProjectZckService;
import com.ledao.common.core.text.Convert;

/**
 * 项目管理资产库Service业务层处理
 *
 * @author ledao
 * @date 2020-08-12
 */
@Service
public class SysProjectZckServiceImpl implements ISysProjectZckService {
    @Autowired
    private SysProjectZckMapper sysProjectZckMapper;

    /**
     * 查询项目管理资产库
     *
     * @param projectZckId 项目管理资产库ID
     * @return 项目管理资产库
     */
    @Override
    public SysProjectZck selectSysProjectZckById(Long projectZckId) {
        return sysProjectZckMapper.selectSysProjectZckById(projectZckId);
    }

    /**
     * 查询项目管理资产库列表
     *
     * @param sysProjectZck 项目管理资产库
     * @return 项目管理资产库
     */
    @Override
    public List<SysProjectZck> selectSysProjectZckList(SysProjectZck sysProjectZck) {
        return sysProjectZckMapper.selectSysProjectZckList(sysProjectZck);
    }

    /**
     * 新增项目管理资产库
     *
     * @param sysProjectZck 项目管理资产库
     * @return 结果
     */
    @Override
    public int insertSysProjectZck(SysProjectZck sysProjectZck) {
        sysProjectZck.setCreateTime(DateUtils.getNowDate());
        return sysProjectZckMapper.insertSysProjectZck(sysProjectZck);
    }

    /**
     * 修改项目管理资产库
     *
     * @param sysProjectZck 项目管理资产库
     * @return 结果
     */
    @Override
    public int updateSysProjectZck(SysProjectZck sysProjectZck) {
        sysProjectZck.setUpdateTime(DateUtils.getNowDate());
        return sysProjectZckMapper.updateSysProjectZck(sysProjectZck);
    }

    /**
     * 删除项目管理资产库对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectZckByIds(String ids) {
        return sysProjectZckMapper.deleteSysProjectZckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目管理资产库信息
     *
     * @param projectZckId 项目管理资产库ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectZckById(Long projectZckId) {
        return sysProjectZckMapper.deleteSysProjectZckById(projectZckId);
    }

    /**
     * 根据id数组查询
     *
     * @param ids 数组
     * @return 结果
     */
    public List<SysProjectZck> selectSysProjectZckByIds(String ids) {
        return sysProjectZckMapper.selectSysProjectZckByIds(Convert.toStrArray(ids));
    }
}
