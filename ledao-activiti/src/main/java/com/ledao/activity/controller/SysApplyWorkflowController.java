package com.ledao.activity.controller;

import java.util.List;
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
import com.ledao.activity.dao.SysApplyWorkflow;
import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 档案出入库审批流程Controller
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/activity/workflow")
public class SysApplyWorkflowController extends BaseController
{
    private String prefix = "activity/workflow";

    @Autowired
    private ISysApplyWorkflowService sysApplyWorkflowService;

    @RequiresPermissions("activity:workflow:view")
    @GetMapping()
    public String workflow()
    {
        return prefix + "/workflow";
    }

    /**
     * 查询档案出入库审批流程列表
     */
    @RequiresPermissions("activity:workflow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplyWorkflow sysApplyWorkflow)
    {
        startPage();
        List<SysApplyWorkflow> list = sysApplyWorkflowService.selectSysApplyWorkflowList(sysApplyWorkflow);
        return getDataTable(list);
    }

    /**
     * 导出档案出入库审批流程列表
     */
    @RequiresPermissions("activity:workflow:export")
    @Log(title = "档案出入库审批流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApplyWorkflow sysApplyWorkflow)
    {
        List<SysApplyWorkflow> list = sysApplyWorkflowService.selectSysApplyWorkflowList(sysApplyWorkflow);
        ExcelUtil<SysApplyWorkflow> util = new ExcelUtil<SysApplyWorkflow>(SysApplyWorkflow.class);
        return util.exportExcel(list, "workflow");
    }

    /**
     * 新增档案出入库审批流程
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存档案出入库审批流程
     */
    @RequiresPermissions("activity:workflow:add")
    @Log(title = "档案出入库审批流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApplyWorkflow sysApplyWorkflow)
    {
        return toAjax(sysApplyWorkflowService.insertSysApplyWorkflow(sysApplyWorkflow));
    }

    /**
     * 修改档案出入库审批流程
     */
    @GetMapping("/edit/{workflowId}")
    public String edit(@PathVariable("workflowId") Long workflowId, ModelMap mmap)
    {
        SysApplyWorkflow sysApplyWorkflow = sysApplyWorkflowService.selectSysApplyWorkflowById(workflowId);
        mmap.put("sysApplyWorkflow", sysApplyWorkflow);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案出入库审批流程
     */
    @RequiresPermissions("activity:workflow:edit")
    @Log(title = "档案出入库审批流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApplyWorkflow sysApplyWorkflow)
    {
        return toAjax(sysApplyWorkflowService.updateSysApplyWorkflow(sysApplyWorkflow));
    }

    /**
     * 删除档案出入库审批流程
     */
    @RequiresPermissions("activity:workflow:remove")
    @Log(title = "档案出入库审批流程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysApplyWorkflowService.deleteSysApplyWorkflowByIds(ids));
    }
}
