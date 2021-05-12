package com.ledao.web.controller.system;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysRole;
import com.ledao.system.dao.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysSubgroup;
import com.ledao.system.service.ISysSubgroupService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 我的小组Controller
 *
 * @author lxz
 * @date 2021-03-26
 */
@Controller
@RequestMapping("/system/subgroup")
public class SysSubgroupController extends BaseController {
    private String prefix = "system/subgroup";

    @Autowired
    private ISysSubgroupService sysSubgroupService;

    @RequiresPermissions("system:subgroup:view")
    @GetMapping()
    public String subgroup() {
        return prefix + "/subgroup";
    }

    /**
     * 查询我的小组列表
     */
    @RequiresPermissions("system:subgroup:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysSubgroup sysSubgroup) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        sysSubgroup.setCreateBy(ShiroUtils.getLoginName());
                    }
                }
            }
        }
        List<SysSubgroup> list = sysSubgroupService.selectSysSubgroupList(sysSubgroup);
        for (SysSubgroup sysSubgroup1 : list) {
            if (StringUtils.isNotEmpty(sysSubgroup1.getSubgroupDeptName()) && StringUtils.isNotEmpty(sysSubgroup1.getSubgroupUserName())) {
                sysSubgroup1.setShareDeptAndUser(sysSubgroup1.getSubgroupDeptName() + "," + sysSubgroup1.getSubgroupUserName());
            } else if (StringUtils.isNotEmpty(sysSubgroup1.getSubgroupUserName())) {
                sysSubgroup1.setShareDeptAndUser(sysSubgroup1.getSubgroupUserName());
            } else if (StringUtils.isNotEmpty(sysSubgroup1.getSubgroupDeptName())) {
                sysSubgroup1.setShareDeptAndUser(sysSubgroup1.getSubgroupDeptName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出我的小组列表
     */
    @RequiresPermissions("system:subgroup:export")
    @Log(title = "我的小组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysSubgroup sysSubgroup) {
        List<SysSubgroup> list = sysSubgroupService.selectSysSubgroupList(sysSubgroup);
        ExcelUtil<SysSubgroup> util = new ExcelUtil<SysSubgroup>(SysSubgroup.class);
        return util.exportExcel(list, "subgroup");
    }

    /**
     * 新增我的小组
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存我的小组
     */
    @RequiresPermissions("system:subgroup:add")
    @Log(title = "我的小组", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysSubgroup sysSubgroup) {
        sysSubgroup.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysSubgroupService.insertSysSubgroup(sysSubgroup));
    }

    /**
     * 修改我的小组
     */
    @GetMapping("/edit/{subgroupId}")
    public String edit(@PathVariable("subgroupId") Long subgroupId, ModelMap mmap) {
        SysSubgroup sysSubgroup = sysSubgroupService.selectSysSubgroupById(subgroupId);
        sysSubgroup.setShareDeptAndUser(sysSubgroup.getSubgroupDeptName() + "," + sysSubgroup.getSubgroupUserName());
        mmap.put("sysSubgroup", sysSubgroup);
        return prefix + "/edit";
    }

    /**
     * 修改保存我的小组
     */
    @RequiresPermissions("system:subgroup:edit")
    @Log(title = "我的小组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysSubgroup sysSubgroup) {
        return toAjax(sysSubgroupService.updateSysSubgroup(sysSubgroup));
    }

    /**
     * 删除我的小组
     */
    @RequiresPermissions("system:subgroup:remove")
    @Log(title = "我的小组", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysSubgroupService.deleteSysSubgroupByIds(ids));
    }


    @GetMapping("/groupList")
    @ResponseBody
    public String groupList(SysSubgroup sysSubgroup) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                sysSubgroup.setCreateBy(ShiroUtils.getLoginName());
            }
        }
        List<SysSubgroup> list = sysSubgroupService.selectSysSubgroupList(sysSubgroup);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("subgroupList", list);
        return jsonObject.toString();
    }

    @PostMapping("/selectById")
    @ResponseBody
    public AjaxResult selectById(String id) {
        SysSubgroup sysSubgroup = sysSubgroupService.selectSysSubgroupById(Long.valueOf(id));
        return AjaxResult.success(sysSubgroup);
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectUser")
    public String selectUser(String loginName, String userName, ModelMap mmap) {
        mmap.put("loginName", loginName);
        mmap.put("userName", userName);
        return prefix + "/treeList";
    }

    @PostMapping("/selectForSubgroup")
    @ResponseBody
    public AjaxResult selectForSubgroup(SysSubgroup sysSubgroup) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                sysSubgroup.setCreateBy(ShiroUtils.getLoginName());
            }
        }
        List<SysSubgroup> list = sysSubgroupService.selectSysSubgroupList(sysSubgroup);
        return AjaxResult.success(list.size());
    }
}
