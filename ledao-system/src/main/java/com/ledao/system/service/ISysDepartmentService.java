package com.ledao.system.service;

import java.util.List;

import com.ledao.common.core.dao.Ztree;
import com.ledao.system.dao.SysDepartment;

/**
 * 公司部门Service接口
 *
 * @author lxz
 * @date 2021-06-21
 */
public interface ISysDepartmentService {
    /**
     * 查询公司部门
     *
     * @param departmentId 公司部门ID
     * @return 公司部门
     */
    public SysDepartment selectSysDepartmentById(Long departmentId);

    /**
     * 查询公司部门列表
     *
     * @param sysDepartment 公司部门
     * @return 公司部门集合
     */
    public List<SysDepartment> selectSysDepartmentList(SysDepartment sysDepartment);

    /**
     * 新增公司部门
     *
     * @param sysDepartment 公司部门
     * @return 结果
     */
    public int insertSysDepartment(SysDepartment sysDepartment);

    /**
     * 修改公司部门
     *
     * @param sysDepartment 公司部门
     * @return 结果
     */
    public int updateSysDepartment(SysDepartment sysDepartment);

    /**
     * 批量删除公司部门
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDepartmentByIds(String ids);

    /**
     * 删除公司部门信息
     *
     * @param departmentId 公司部门ID
     * @return 结果
     */
    public int deleteSysDepartmentById(Long departmentId);

    /**
     * 校验部门名称是否唯一
     *
     * @param sysDepartment 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDepartment sysDepartment);

    /**
     * 查询部门管理树
     *
     * @param sysDepartment 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDepartmentTree(SysDepartment sysDepartment);

    /**
     * 查询部门管理树（排除下级）
     *
     * @param sysDepartment 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDepartmentTreeExcludeChild(SysDepartment sysDepartment);

    /**
     * 根据父级id查询子集
     *
     * @param pId
     * @return
     */
    public List<SysDepartment> selectDepartmentByPId(Long pId);
}
