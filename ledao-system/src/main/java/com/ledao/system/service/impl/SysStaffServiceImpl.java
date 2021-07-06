package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysStaffMapper;
import com.ledao.system.dao.SysStaff;
import com.ledao.system.service.ISysStaffService;
import com.ledao.common.core.text.Convert;

/**
 * 员工信息Service业务层处理
 *
 * @author lxz
 * @date 2021-06-23
 */
@Service
public class SysStaffServiceImpl implements ISysStaffService {
    @Autowired
    private SysStaffMapper sysStaffMapper;

    /**
     * 查询员工信息
     *
     * @param staffId 员工信息ID
     * @return 员工信息
     */
    @Override
    public SysStaff selectSysStaffById(Long staffId) {
        return sysStaffMapper.selectSysStaffById(staffId);
    }

    /**
     * 查询员工信息列表
     *
     * @param sysStaff 员工信息
     * @return 员工信息
     */
    @Override
    public List<SysStaff> selectSysStaffList(SysStaff sysStaff) {
        return sysStaffMapper.selectSysStaffList(sysStaff);
    }

    /**
     * 新增员工信息
     *
     * @param sysStaff 员工信息
     * @return 结果
     */
    @Override
    public int insertSysStaff(SysStaff sysStaff) {
        sysStaff.setCreateTime(DateUtils.getNowDate());
        return sysStaffMapper.insertSysStaff(sysStaff);
    }

    /**
     * 修改员工信息
     *
     * @param sysStaff 员工信息
     * @return 结果
     */
    @Override
    public int updateSysStaff(SysStaff sysStaff) {
        sysStaff.setUpdateTime(DateUtils.getNowDate());
        return sysStaffMapper.updateSysStaff(sysStaff);
    }

    /**
     * 删除员工信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysStaffByIds(String ids) {
        return sysStaffMapper.deleteSysStaffByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除员工信息信息
     *
     * @param staffId 员工信息ID
     * @return 结果
     */
    @Override
    public int deleteSysStaffById(Long staffId) {
        return sysStaffMapper.deleteSysStaffById(staffId);
    }

    /**
     * 用户状态修改
     *
     * @param sysStaff 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysStaff sysStaff) {
        return sysStaffMapper.updateSysStaff(sysStaff);
    }

    /**
     * 根据学历分组
     *
     * @param sysStaff
     * @return
     */
    @Override
    public List<SysStaff> selectStaffByEducation(SysStaff sysStaff) {
        return sysStaffMapper.selectStaffByEducation(sysStaff);
    }

    /**
     * 根据性别分组
     *
     * @param sysStaff
     * @return
     */
    @Override
    public List<SysStaff> selectStaffBySex(SysStaff sysStaff) {
        return sysStaffMapper.selectStaffBySex(sysStaff);
    }

    /**
     * 根据司龄分组
     *
     * @param staff
     * @return
     */
    @Override
    public List<SysStaff> selectStaffBySecretaryLing(SysStaff staff) {
        return sysStaffMapper.selectStaffBySecretaryLing(staff);
    }
}
