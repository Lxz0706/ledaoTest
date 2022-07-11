package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysDepartment;
import org.apache.ibatis.annotations.Param;

/**
 * 公司部门Mapper接口
 *
 * @author lxz
 * @date 2021-06-21
 */
public interface SysDepartmentMapper {
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
     * 删除公司部门
     *
     * @param departmentId 公司部门ID
     * @return 结果
     */
    public int deleteSysDepartmentById(Long departmentId);

    /**
     * 批量删除公司部门
     *
     * @param departmentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDepartmentByIds(String[] departmentIds);

    /**
     * 校验部门名称是否唯一
     *
     * @param departmentName 部门名称
     * @param pId            父部门ID
     * @return 结果
     */
    public SysDepartment checkDeptNameUnique(@Param("departmentName") String departmentName, @Param("pId") Long pId);


    /**
     * 修改所在部门的父级部门状态
     *
     * @param sysDepartment 部门
     */
    public void updateDeptStatus(SysDepartment sysDepartment);

    /**
     * 修改子元素关系
     *
     * @param sysDepartments 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("sysDepartments") List<SysDepartment> sysDepartments);

    /**
     * 根据ID查询所有子部门
     *
     * @param departmentId 部门ID
     * @return 部门列表
     */
    public List<SysDepartment> selectChildrenDepartmentById(Long departmentId);

    /**
     * 根据父级id查询
     *
     * @param pId
     * @return
     */
    public List<SysDepartment> selectDepartmentByPId(Long pId);
}
