package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysManageTask;
import com.ledao.system.service.ISysManageTaskService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 处置回现任务Controller
 * 
 * @author lxz
 * @date 2021-09-03
 */
@Controller
@RequestMapping("/system/managetask")
public class SysManageTaskController extends BaseController
{
    private String prefix = "system/managetask";

    @Autowired
    private ISysManageTaskService sysManageTaskService;

//    @RequiresPermissions("system:managetask:view")
    @GetMapping("/managetask")
    public String managetask(@RequestParam("proId")String proId,@RequestParam("taskType")Long taskType, ModelMap mmap)
    {
        mmap.put("proId",proId);
        mmap.put("taskType",taskType);
        return prefix + "/managetask";
    }

    /**
     * 查询处置回现任务列表
     */
//    @RequiresPermissions("system:managetask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysManageTask sysManageTask)
    {
        startPage();
        List<SysManageTask> list = sysManageTaskService.selectSysManageTaskList(sysManageTask);
        return getDataTable(list);
    }

    /**
     * 导出处置回现任务列表
     */
//    @RequiresPermissions("system:managetask:export")
    @Log(title = "处置回现任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysManageTask sysManageTask)
    {
        List<SysManageTask> list = sysManageTaskService.selectSysManageTaskList(sysManageTask);
        ExcelUtil<SysManageTask> util = new ExcelUtil<SysManageTask>(SysManageTask.class);
        return util.exportExcel(list, "managetask");
    }

    /**
     * 新增处置回现任务
     */
    @GetMapping("/add/{proId}/{taskType}")
    public String add(@PathVariable("proId") Long proId,@PathVariable("taskType") String taskType,ModelMap mmap)
    {
        mmap.put("proId",proId);
        mmap.put("taskType",taskType);
        return prefix + "/add";
    }

    /**
     * 新增保存处置回现任务
     */
//    @RequiresPermissions("system:managetask:add")
    @Log(title = "处置回现任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysManageTask sysManageTask)
    {
        return toAjax(sysManageTaskService.insertSysManageTask(sysManageTask));
    }

    /**
     * 修改处置回现任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysManageTask sysManageTask = sysManageTaskService.selectSysManageTaskById(id);
        mmap.put("sysManageTask", sysManageTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存处置回现任务
     */
//    @RequiresPermissions("system:managetask:edit")
    @Log(title = "处置回现任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysManageTask sysManageTask)
    {
        return toAjax(sysManageTaskService.updateSysManageTask(sysManageTask));
    }

    /**
     * 删除处置回现任务
     */
//    @RequiresPermissions("system:managetask:remove")
    @Log(title = "处置回现任务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysManageTaskService.deleteSysManageTaskByIds(ids));
    }
}
