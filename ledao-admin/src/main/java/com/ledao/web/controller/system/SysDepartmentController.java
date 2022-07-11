package com.ledao.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ledao.common.core.dao.DepartmentTree;
import com.ledao.common.core.dao.Ztree;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysDepartment;
import com.ledao.system.service.ISysDepartmentService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;

/**
 * 公司部门Controller
 *
 * @author lxz
 * @date 2021-06-21
 */
@Controller
@RequestMapping("/system/department")
public class SysDepartmentController extends BaseController {
    private String prefix = "system/department";

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @RequiresPermissions("system:department:view")
    @GetMapping()
    public String department() {
        return prefix + "/department";
    }

    /**
     * 查询公司部门列表
     */
    @RequiresPermissions("system:department:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDepartment> list(SysDepartment sysDepartment) {
        List<SysDepartment> list = sysDepartmentService.selectSysDepartmentList(sysDepartment);
        return list;
    }

    /**
     * 导出公司部门列表
     */
    @RequiresPermissions("system:department:export")
    @Log(title = "公司部门", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDepartment sysDepartment) {
        List<SysDepartment> list = sysDepartmentService.selectSysDepartmentList(sysDepartment);
        ExcelUtil<SysDepartment> util = new ExcelUtil<SysDepartment>(SysDepartment.class);
        return util.exportExcel(list, "公司架构");
    }

    /**
     * 新增公司部门
     */
    @GetMapping(value = {"/add/{pId}", "/add"})
    public String add(@PathVariable(value = "pId", required = false) Long pId, ModelMap mmap) {
        if (StringUtils.isNotNull(pId)) {
            mmap.put("departmentId", pId);
            mmap.put("departmentName", sysDepartmentService.selectSysDepartmentById(pId).getDepartmentName());
        }
        return prefix + "/add";
    }

    /**
     * 新增保存公司部门
     */
    @RequiresPermissions("system:department:add")
    @Log(title = "公司部门", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDepartment sysDepartment) {
        if (StringUtils.isNull(sysDepartment.getpId())) {
            sysDepartment.setpId(0L);
        }
        sysDepartment.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysDepartmentService.insertSysDepartment(sysDepartment));
    }

    /**
     * 修改公司部门
     */
    @GetMapping("/edit/{departmentId}")
    public String edit(@PathVariable("departmentId") Long departmentId, ModelMap mmap) {
        SysDepartment sysDepartment = sysDepartmentService.selectSysDepartmentById(departmentId);
        if (StringUtils.isEmpty(sysDepartment.getParentName())) {
            sysDepartment.setParentName("无");
        }
        mmap.put("sysDepartment", sysDepartment);
        return prefix + "/edit";
    }

    /**
     * 修改保存公司部门
     */
    @RequiresPermissions("system:department:edit")
    @Log(title = "公司部门", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDepartment sysDepartment) {
        return toAjax(sysDepartmentService.updateSysDepartment(sysDepartment));
    }

    /**
     * 删除公司部门
     */
    @RequiresPermissions("system:department:remove")
    @Log(title = "公司部门", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{departmentId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("departmentId") Long departmentId) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setpId(departmentId);
        List<SysDepartment> list = sysDepartmentService.selectSysDepartmentList(sysDepartment);
        if (list.size() > 0) {
            return AjaxResult.warn("存在下级部门,不允许删除");
        }
        return toAjax(sysDepartmentService.deleteSysDepartmentById(departmentId));
    }

    /**
     * 选择部门树
     *
     * @param deptId 部门ID
     */
    @GetMapping(value = {"/selectDeptTree/{deptId}", "/selectDeptTree", "/selectDeptTree/{deptId}/{excludeId}"})
    public String selectDeptTree(@PathVariable(value = "deptId", required = false) Long deptId,
                                 @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap) {
        if (StringUtils.isNotNull(deptId)) {
            mmap.put("departmentId", deptId);
            mmap.put("departmentName", sysDepartmentService.selectSysDepartmentById(deptId).getDepartmentName());
        }
        //mmap.put("dept", sysDepartmentService.selectSysDepartmentById(deptId));
        // mmap.put("excludeId", excludeId);
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = sysDepartmentService.selectDepartmentTree(new SysDepartment());
        return ztrees;
    }

    /**
     * 加载部门列表树（排除下级）
     */
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setDepartmentId(excludeId);
        List<Ztree> ztrees = sysDepartmentService.selectDepartmentTreeExcludeChild(sysDepartment);
        return ztrees;
    }

    @GetMapping("/analysis")
    public String toAnalysis() {
        return prefix + "/analysis";
    }

    private List<SysDepartment> treeList;

    @PostMapping("/departmentAnalysis")
    @ResponseBody
    public String analysis(SysDepartment sysDepartmsent) {
        List<SysDepartment> list = sysDepartmentService.selectSysDepartmentList(sysDepartmsent);
        List<DepartmentTree> menuList = new ArrayList<DepartmentTree>();
        treeList = list;
        for (SysDepartment sysDepartment : list) {
            //遍历所有一级节点,并找出所有一级节点下的所有子节点
            if (sysDepartment.getpId() == 0L) {
                DepartmentTree grid = getDepartmentTree(sysDepartment);
                menuList.add(grid);
            }
        }
        return JSONArray.parseArray(JSON.toJSONString(menuList)).toString();

    }

    public DepartmentTree getDepartmentTree(SysDepartment sysDepartment) {
        DepartmentTree departmentTree = new DepartmentTree();
        departmentTree.setId(sysDepartment.getDepartmentId());
        departmentTree.setName(sysDepartment.getDepartmentName());
        departmentTree.setTitle(sysDepartment.getDepartmentName());
        departmentTree.setPid(sysDepartment.getpId());
        //递增遍历所有子节点（无限层级）
        departmentTree.setChildren(getChild(sysDepartment.getDepartmentId()));
        return departmentTree;
    }

    public List<DepartmentTree> getChild(Long id) {
        List<DepartmentTree> childList = new ArrayList<>();
        for (SysDepartment root : treeList) {
            // 遍历所有节点，将父菜单编码与传过来的编码进行比较、若相同则继续查看该节点下是否还有子节点
            if (StringUtils.isNotNull(root.getpId())) {
                if (id.equals(root.getpId())) {
                    DepartmentTree departmentTree = new DepartmentTree();
                    departmentTree.setId(root.getDepartmentId());
                    departmentTree.setName(root.getDepartmentName());
                    departmentTree.setTitle(root.getDepartmentName());
                    departmentTree.setPid(root.getpId());
                    //递增遍历所有子节点（无限层级）
                    departmentTree.setChildren(getChild(root.getDepartmentId()));
                    childList.add(departmentTree);
                }
            }
        }
        return childList;
    }
}
