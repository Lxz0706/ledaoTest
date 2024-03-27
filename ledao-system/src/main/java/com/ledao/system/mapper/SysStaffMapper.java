package com.ledao.system.mapper;

import com.ledao.system.dao.SysStaff;

import java.util.List;

/**
 * 员工信息Mapper接口
 *
 * @author lxz
 * @date 2021-06-23
 */
public interface SysStaffMapper {
    /**
     * 查询员工信息
     *
     * @param staffId 员工信息ID
     * @return 员工信息
     */
    public SysStaff selectSysStaffById(Long staffId);

    /**
     * 查询员工信息列表
     *
     * @param sysStaff 员工信息
     * @return 员工信息集合
     */
    public List<SysStaff> selectSysStaffList(SysStaff sysStaff);

    /**
     * 新增员工信息
     *
     * @param sysStaff 员工信息
     * @return 结果
     */
    public int insertSysStaff(SysStaff sysStaff);

    /**
     * 修改员工信息
     *
     * @param sysStaff 员工信息
     * @return 结果
     */
    public int updateSysStaff(SysStaff sysStaff);

    /**
     * 删除员工信息
     *
     * @param staffId 员工信息ID
     * @return 结果
     */
    public int deleteSysStaffById(Long staffId);

    /**
     * 批量删除员工信息
     *
     * @param staffIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysStaffByIds(String[] staffIds);

    /**
     * 根据学历分组
     *
     * @param sysStaff
     * @return
     */
    public List<SysStaff> selectStaffByEducation(SysStaff sysStaff);

    /**
     * 根据性别分组
     *
     * @param sysStaff
     * @return
     */
    public List<SysStaff> selectStaffBySex(SysStaff sysStaff);

    /**
     * 根据司龄分组
     *
     * @param staff
     * @return
     */
    public List<SysStaff> selectStaffBySecretaryLing(SysStaff staff);


    /**
     * 根据id查询
     */
    public List<SysStaff> selectByIds(String[] ids);

    /**
     * 根据员工名称查询员工信息
     *
     * @param staffName
     * @return
     */
    public SysStaff selectStaffByStaffName(String staffName);
}
