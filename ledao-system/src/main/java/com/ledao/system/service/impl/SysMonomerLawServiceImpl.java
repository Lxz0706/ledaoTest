package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.system.mapper.SysMonomerLawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.dao.SysMonomerLaw;
import com.ledao.system.service.ISysMonomerLawService;
import com.ledao.common.core.text.Convert;

/**
 * 项目法律信息Service业务层处理
 *
 * @author lxz
 * @date 2021-08-23
 */
@Service
public class SysMonomerLawServiceImpl implements ISysMonomerLawService {
    @Autowired
    private SysMonomerLawMapper sysMonomerLawMapper;

    /**
     * 查询项目法律信息
     *
     * @param monomerLawId 项目法律信息ID
     * @return 项目法律信息
     */
    @Override
    public SysMonomerLaw selectSysMonomerLawById(Long monomerLawId) {
        return sysMonomerLawMapper.selectSysMonomerLawById(monomerLawId);
    }

    /**
     * 查询项目法律信息列表
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 项目法律信息
     */
    @Override
    public List<SysMonomerLaw> selectSysMonomerLawList(SysMonomerLaw sysMonomerLaw) {
        return sysMonomerLawMapper.selectSysMonomerLawList(sysMonomerLaw);
    }

    /**
     * 新增项目法律信息
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 结果
     */
    @Override
    public int insertSysMonomerLaw(SysMonomerLaw sysMonomerLaw) {
        sysMonomerLaw.setCreateTime(DateUtils.getNowDate());
        return sysMonomerLawMapper.insertSysMonomerLaw(sysMonomerLaw);
    }

    /**
     * 修改项目法律信息
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 结果
     */
    @Override
    public int updateSysMonomerLaw(SysMonomerLaw sysMonomerLaw) {
        sysMonomerLaw.setUpdateTime(DateUtils.getNowDate());
        return sysMonomerLawMapper.updateSysMonomerLaw(sysMonomerLaw);
    }

    /**
     * 删除项目法律信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysMonomerLawByIds(String ids) {
        return sysMonomerLawMapper.deleteSysMonomerLawByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目法律信息信息
     *
     * @param monomerLawId 项目法律信息ID
     * @return 结果
     */
    @Override
    public int deleteSysMonomerLawById(Long monomerLawId) {
        return sysMonomerLawMapper.deleteSysMonomerLawById(monomerLawId);
    }
}
