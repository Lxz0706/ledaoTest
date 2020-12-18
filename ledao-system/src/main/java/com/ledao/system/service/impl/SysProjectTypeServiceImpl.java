package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectTypeMapper;
import com.ledao.system.dao.SysProjectType;
import com.ledao.system.service.ISysProjectTypeService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author lxz
 * @date 2020-12-15
 */
@Service
public class SysProjectTypeServiceImpl implements ISysProjectTypeService {
    @Autowired
    private SysProjectTypeMapper sysProjectTypeMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param projectType 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProjectType selectSysProjectTypeById(String projectType) {
        return sysProjectTypeMapper.selectSysProjectTypeById(projectType);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectType> selectSysProjectTypeList(SysProjectType sysProjectType) {
        return sysProjectTypeMapper.selectSysProjectTypeList(sysProjectType);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProjectType(SysProjectType sysProjectType) {
        return sysProjectTypeMapper.insertSysProjectType(sysProjectType);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProjectType(SysProjectType sysProjectType) {
        return sysProjectTypeMapper.updateSysProjectType(sysProjectType);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectTypeByIds(String ids) {
        return sysProjectTypeMapper.deleteSysProjectTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param projectType 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectTypeById(String projectType) {
        return sysProjectTypeMapper.deleteSysProjectTypeById(projectType);
    }
}
