package com.ledao.system.service.impl;

import java.util.*;

import com.ledao.common.constant.UserConstants;
import com.ledao.common.core.dao.Ztree;
import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysDepartmentMapper;
import com.ledao.system.dao.SysDepartment;
import com.ledao.system.service.ISysDepartmentService;
import com.ledao.common.core.text.Convert;

/**
 * 公司部门Service业务层处理
 *
 * @author lxz
 * @date 2021-06-21
 */
@Service
public class SysDepartmentServiceImpl implements ISysDepartmentService {
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    /**
     * 查询公司部门
     *
     * @param departmentId 公司部门ID
     * @return 公司部门
     */
    @Override
    public SysDepartment selectSysDepartmentById(Long departmentId) {
        return sysDepartmentMapper.selectSysDepartmentById(departmentId);
    }

    /**
     * 查询公司部门列表
     *
     * @param sysDepartment 公司部门
     * @return 公司部门
     */
    @Override
    public List<SysDepartment> selectSysDepartmentList(SysDepartment sysDepartment) {
        return sysDepartmentMapper.selectSysDepartmentList(sysDepartment);
    }

    /**
     * 新增公司部门
     *
     * @param sysDepartment 公司部门
     * @return 结果
     */
    @Override
    public int insertSysDepartment(SysDepartment sysDepartment) {
        sysDepartment.setCreateTime(DateUtils.getNowDate());
        sysDepartment.setAncestors(sysDepartment.getpId().toString());
        SysDepartment info = sysDepartmentMapper.selectSysDepartmentById(sysDepartment.getpId());

        if (StringUtils.isNotNull(info)) {
            // 如果父节点不为"正常"状态,则不允许新增子节点
            if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
                throw new BusinessException("部门停用，不允许新增");
            }
            if (StringUtils.isNotEmpty(info.getAncestors())) {
                sysDepartment.setAncestors(info.getAncestors() + "," + sysDepartment.getAncestors());
            }
        }

        return sysDepartmentMapper.insertSysDepartment(sysDepartment);
    }

    /**
     * 修改公司部门
     *
     * @param sysDepartment 公司部门
     * @return 结果
     */
    @Override
    public int updateSysDepartment(SysDepartment sysDepartment) {
        SysDepartment newParentDept = sysDepartmentMapper.selectSysDepartmentById(sysDepartment.getpId());
        SysDepartment oldDept = selectSysDepartmentById(sysDepartment.getDepartmentId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDepartmentId();
            String oldAncestors = oldDept.getAncestors();
            sysDepartment.setAncestors(newAncestors);
            updateDeptChildren(sysDepartment.getDepartmentId(), newAncestors, oldAncestors);
        }
        int result = sysDepartmentMapper.updateSysDepartment(sysDepartment);
        if (UserConstants.DEPT_NORMAL.equals(sysDepartment.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(sysDepartment);
        }
        return result;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDepartment> children = sysDepartmentMapper.selectChildrenDepartmentById(deptId);
        for (SysDepartment child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            sysDepartmentMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param sysDepartment 当前部门
     */
    private void updateParentDeptStatus(SysDepartment sysDepartment) {
        String updateBy = sysDepartment.getUpdateBy();
        sysDepartment = sysDepartmentMapper.selectSysDepartmentById(sysDepartment.getDepartmentId());
        sysDepartment.setUpdateBy(updateBy);
        sysDepartmentMapper.updateDeptStatus(sysDepartment);
    }

    /**
     * 删除公司部门对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDepartmentByIds(String ids) {
        return sysDepartmentMapper.deleteSysDepartmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除公司部门信息
     *
     * @param departmentId 公司部门ID
     * @return 结果
     */
    @Override
    public int deleteSysDepartmentById(Long departmentId) {
        return sysDepartmentMapper.deleteSysDepartmentById(departmentId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param sysDepartment 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDepartment sysDepartment) {
        Long deptId = StringUtils.isNull(sysDepartment.getDepartmentId()) ? -1L : sysDepartment.getDepartmentId();
        SysDepartment info = sysDepartmentMapper.checkDeptNameUnique(sysDepartment.getDepartmentName(), sysDepartment.getpId());
        if (StringUtils.isNotNull(info) && info.getDepartmentId().longValue() != deptId.longValue()) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 查询部门管理树
     *
     * @param sysDepartment 部门信息
     * @return 所有部门信息
     */
    @Override
    public List<Ztree> selectDepartmentTree(SysDepartment sysDepartment) {
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentList(sysDepartment);
        List<Ztree> ztrees = initZtree(sysDepartmentList);
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param sysDepartmentList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDepartment> sysDepartmentList) {
        return initZtree(sysDepartmentList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDepartment> deptList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysDepartment sysDepartment : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(sysDepartment.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(sysDepartment.getDepartmentId());
                ztree.setpId(sysDepartment.getpId());
                ztree.setName(sysDepartment.getDepartmentName());
                ztree.setTitle(sysDepartment.getDepartmentName());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门管理树（排除下级）
     *
     * @param sysDepartment 部门ID
     * @return 所有部门信息
     */
    @Override
    public List<Ztree> selectDepartmentTreeExcludeChild(SysDepartment sysDepartment) {
        Long departmentId = sysDepartment.getDepartmentId();
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentList(sysDepartment);
        Iterator<SysDepartment> it = sysDepartmentList.iterator();
        while (it.hasNext()) {
            SysDepartment d = (SysDepartment) it.next();
            if (d.getDepartmentId().intValue() == departmentId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), departmentId + "")) {
                it.remove();
            }
        }
        List<Ztree> ztrees = initZtree(sysDepartmentList);
        return ztrees;
    }

    @Override
    public List<SysDepartment> selectDepartmentByPId(Long pId) {
        return sysDepartmentMapper.selectDepartmentByPId(pId);
    }
}
