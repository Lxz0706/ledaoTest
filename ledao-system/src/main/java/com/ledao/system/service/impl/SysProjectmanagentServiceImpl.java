package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectmanagentMapper;
import com.ledao.system.dao.SysProjectmanagent;
import com.ledao.system.service.ISysProjectmanagentService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ledao
 * @date 2020-08-26
 */
@Service
public class SysProjectmanagentServiceImpl implements ISysProjectmanagentService {
    @Autowired
    private SysProjectmanagentMapper sysProjectmanagentMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param projectManagementId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProjectmanagent selectSysProjectmanagentById(Long projectManagementId) {
        return sysProjectmanagentMapper.selectSysProjectmanagentById(projectManagementId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectmanagent> selectSysProjectmanagentList(SysProjectmanagent sysProjectmanagent) {
        return sysProjectmanagentMapper.selectSysProjectmanagentList(sysProjectmanagent);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProjectmanagent(SysProjectmanagent sysProjectmanagent) {
        sysProjectmanagent.setCreateTime(DateUtils.getNowDate());
        return sysProjectmanagentMapper.insertSysProjectmanagent(sysProjectmanagent);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProjectmanagent(SysProjectmanagent sysProjectmanagent) {
        sysProjectmanagent.setUpdateTime(DateUtils.getNowDate());
        return sysProjectmanagentMapper.updateSysProjectmanagent(sysProjectmanagent);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectmanagentByIds(String ids) {
        return sysProjectmanagentMapper.deleteSysProjectmanagentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param projectManagementId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectmanagentById(Long projectManagementId) {
        return sysProjectmanagentMapper.deleteSysProjectmanagentById(projectManagementId);
    }

    /**
     * 根据类型查询
     * */
    public List<SysProjectmanagent> selectSysProjectmanagentListByProjectType(){
        return sysProjectmanagentMapper.selectSysProjectmanagentListByProjectType();
    }
}
