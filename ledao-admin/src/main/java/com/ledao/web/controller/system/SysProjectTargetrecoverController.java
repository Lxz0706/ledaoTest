package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.service.ISysProjectmanagentService;
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
import com.ledao.system.dao.SysProjectTargetrecover;
import com.ledao.system.service.ISysProjectTargetrecoverService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 目标回收Controller
 *
 * @author ledao
 * @date 2020-09-03
 */
@Controller
@RequestMapping("/system/targetrecover")
public class SysProjectTargetrecoverController extends BaseController {
    private String prefix = "system/targetrecover";

    @Autowired
    private ISysProjectTargetrecoverService sysProjectTargetrecoverService;

    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @RequiresPermissions("system:targetrecover:view")
    @GetMapping()
    public String targetrecover() {
        return prefix + "/targetrecover";
    }

    /**
     * 查询目标回收列表
     */
    @RequiresPermissions("system:targetrecover:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectTargetrecover sysProjectTargetrecover) {
        startPage();
        List<SysProjectTargetrecover> list = sysProjectTargetrecoverService.selectSysProjectTargetrecoverList(sysProjectTargetrecover);
        return getDataTable(list);
    }

    /**
     * 导出目标回收列表
     */
    @RequiresPermissions("system:targetrecover:export")
    @Log(title = "目标回收", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectTargetrecover sysProjectTargetrecover) {
        List<SysProjectTargetrecover> list = sysProjectTargetrecoverService.selectSysProjectTargetrecoverList(sysProjectTargetrecover);
        ExcelUtil<SysProjectTargetrecover> util = new ExcelUtil<SysProjectTargetrecover>(SysProjectTargetrecover.class);
        return util.exportExcel(list, "targetrecover");
    }

    /**
     * 新增目标回收
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("projectManagementId", id);
        return prefix + "/add";
    }

    /**
     * 新增保存目标回收
     */
    @RequiresPermissions("system:targetrecover:add")
    @Log(title = "目标回收", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectTargetrecover sysProjectTargetrecover) {
        sysProjectTargetrecover.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectTargetrecoverService.insertSysProjectTargetrecover(sysProjectTargetrecover));
    }

    /**
     * 修改目标回收
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysProjectTargetrecover sysProjectTargetrecover = sysProjectTargetrecoverService.selectSysProjectTargetrecoverById(id);
        mmap.put("sysProjectTargetrecover", sysProjectTargetrecover);
        return prefix + "/edit";
    }

    /**
     * 修改保存目标回收
     */
    @RequiresPermissions("system:targetrecover:edit")
    @Log(title = "目标回收", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectTargetrecover sysProjectTargetrecover) {
        sysProjectTargetrecover.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectTargetrecoverService.updateSysProjectTargetrecover(sysProjectTargetrecover));
    }

    /**
     * 删除目标回收
     */
    @RequiresPermissions("system:targetrecover:remove")
    @Log(title = "目标回收", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectTargetrecoverService.deleteSysProjectTargetrecoverByIds(ids));
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/projectTargetRecoverList/{projectManagementId}")
    public String selectProjectTargetRecoverListByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        modelMap.put("type", sysProjectmanagentService.selectSysProjectmanagentById(Long.valueOf(projectManagementId)).getProjectType());
        return "system/targetrecover/targetrecover";
    }
}
