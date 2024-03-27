package com.ledao.system.service.impl;

import com.ledao.common.core.text.Convert;
import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysDepartment;
import com.ledao.system.dao.SysStaff;
import com.ledao.system.mapper.SysDepartmentMapper;
import com.ledao.system.mapper.SysDictDataMapper;
import com.ledao.system.mapper.SysStaffMapper;
import com.ledao.system.service.ISysStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工信息Service业务层处理
 *
 * @author lxz
 * @date 2021-06-23
 */
@Service
public class SysStaffServiceImpl implements ISysStaffService {

    private static final Logger log = LoggerFactory.getLogger(SysStaffServiceImpl.class);
    @Autowired
    private SysStaffMapper sysStaffMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

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

    /**
     * 根据id查询
     */
    @Override
    public List<SysStaff> selectByIds(String ids) {
        return sysStaffMapper.selectByIds(Convert.toStrArray(ids));
    }

    /**
     * 导入用户数据
     *
     * @param sysStaffList    用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importStaff(List<SysStaff> sysStaffList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(sysStaffList) || sysStaffList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysStaff sysStaff : sysStaffList) {
            try {
                SysStaff sysStaff2 = selectStaffByStaffName(sysStaff.getStaffName());
                if (StringUtils.isNull(sysStaff2)) {
                    sysStaff.setCreateBy(operName);
                    this.insertSysStaff(sysStaff);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + sysStaff.getStaffName() + " 导入成功");
                } else if (isUpdateSupport) {
                    if (StringUtils.isNotEmpty(sysStaff.getDepartmentName())) {
                        SysDepartment sysDepartment = sysDepartmentMapper.selectSysDeparmentByDeparmentName(sysStaff.getDepartmentName());
                        if (StringUtils.isNotNull(sysDepartment)) {
                            if (StringUtils.isNotNull(sysDepartment.getDepartmentId())) {
                                sysStaff.setDepartmentId(sysDepartment.getDepartmentId());
                            }
                        }
                    }
                    //获取性别
                    if (StringUtils.isNotEmpty(sysStaff.getSex())) {
                        if (StringUtils.isNotEmpty(sysDictDataMapper.selectDictLabel("sys_user_sex", sysStaff.getSex()))) {
                            sysStaff.setSex(sysDictDataMapper.selectDictLabel("sys_user_sex", sysStaff.getSex()));
                        }
                    }
                    //获取政治面貌
                    if (StringUtils.isNotEmpty(sysStaff.getPoliticalOutlook())) {
                        if (StringUtils.isNotEmpty(sysDictDataMapper.selectDictLabel("sys_staff_politicalOutlook", sysStaff.getPoliticalOutlook()))) {
                            sysStaff.setPoliticalOutlook(sysDictDataMapper.selectDictLabel("sys_staff_politicalOutlook", sysStaff.getPoliticalOutlook()));
                        }
                    }
                    //获取专业
                    if (StringUtils.isNotEmpty(sysStaff.getSpeciality())) {
                        if (StringUtils.isNotEmpty(sysDictDataMapper.selectDictLabel("sys_staff_education", sysStaff.getSpeciality()))) {
                            sysStaff.setSpeciality(sysDictDataMapper.selectDictLabel("sys_staff_education", sysStaff.getSpeciality()));
                        }
                    }
                    //获取婚育
                    if (StringUtils.isNotEmpty(sysStaff.getMarriage())) {
                        if (StringUtils.isNotEmpty(sysDictDataMapper.selectDictLabel("sys_staff_marriage", sysStaff.getMarriage()))) {
                            sysStaff.setMarriage(sysDictDataMapper.selectDictLabel("sys_staff_marriage", sysStaff.getMarriage()));
                        }
                    }
                    sysStaff.setDepartmentName("");

                    sysStaff.setUpdateBy(operName);
                    this.updateSysStaff(sysStaff);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + sysStaff.getStaffName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + sysStaff.getStaffName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + sysStaff.getStaffName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 根据员工名称查询员工信息
     *
     * @param staffName
     * @return
     */
    @Override
    public SysStaff selectStaffByStaffName(String staffName) {
        return sysStaffMapper.selectStaffByStaffName(staffName);
    }
}
