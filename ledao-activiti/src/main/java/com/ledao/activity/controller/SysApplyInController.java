package com.ledao.activity.controller;

import java.util.Date;
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

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysApplyWorkflow;
import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.activity.service.ISysDocumentFileService;
import com.ledao.activity.service.ISysFileDetailService;
import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;

/**
 * 档案入库申请Controller
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/applyIn")
public class SysApplyInController extends BaseController
{
    private String prefix = "applyIn";

    @Autowired
    private ISysApplyInService sysApplyInService;
    
    @Autowired
    private ISysApplyWorkflowService sysApplyWorkflowService;
    
    @Autowired
    private ISysDocumentFileService sysDocumentFileService;
    
    @Autowired
    private ISysFileDetailService sysFileDetailService;

    @RequiresPermissions("applyIn:view")
    @GetMapping("/applyIn")
    public String applyIn()
    {
        return prefix + "/applyIn";
    }
    
    @GetMapping("/applyOut")
    public String applyOut(ModelMap modelMap) {
        return prefix + "/applyOut";
    }
    @GetMapping("/applyList")
    public String applyList(ModelMap modelMap) {
        return prefix + "/applyList";
    }

    /**
     * 查询档案入库申请列表
     */
//    @RequiresPermissions("applyIn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplyIn sysApplyIn)
    {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        return getDataTable(list);
    }
    
    @PostMapping("/listByMe")
    @ResponseBody
    public TableDataInfo listByMe(SysApplyIn sysApplyIn)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setReviser(user.getLoginName());
        List<SysApplyIn> list = sysApplyInService.listByMe(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 导出档案入库申请列表
     */
    @RequiresPermissions("applyIn:export")
    @Log(title = "档案入库申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApplyIn sysApplyIn)
    {
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        ExcelUtil<SysApplyIn> util = new ExcelUtil<SysApplyIn>(SysApplyIn.class);
        return util.exportExcel(list, "applyIn");
    }

    /**
     * 新增档案入库申请
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存档案入库申请
     */
    @RequiresPermissions("applyIn:add")
    @Log(title = "档案入库申请", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApplyIn sysApplyIn)
    {
    	
        return toAjax(sysApplyInService.insertSysApplyIn(sysApplyIn));
    }
    
    
    /**
     * 新增保存档案入库申请
     */
    @RequiresPermissions("applyIn:addSysApplyIn")
    @Log(title = "档案入库申请发起", businessType = BusinessType.INSERT)
    @PostMapping("/addSysApplyIn")
    @ResponseBody
    public AjaxResult addSysApplyIn(SysApplyIn sysApplyIn)
    {
    	sysApplyInService.insertSysApplyIn(sysApplyIn);
        return toAjax(true);
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/edit/{applyId}")
    public String edit(@PathVariable("applyId") Long applyId, ModelMap mmap)
    {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/edit";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/applyBack/{applyId}")
    public String applyBack(@PathVariable("applyId") Long applyId, ModelMap mmap)
    {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案入库申请
     */
//    @RequiresPermissions("applyIn:edit")
    @Log(title = "档案入库申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApplyIn sysApplyIn)
    {
        return toAjax(sysApplyInService.editSave(sysApplyIn));
    }

    @Log(title = "档案状态修改", businessType = BusinessType.UPDATE)
    @PostMapping("/applyEditSave")
    @ResponseBody
    public AjaxResult applyEditSave(SysApplyIn sysApplyIn)
    {
        AjaxResult res = sysApplyInService.applyEditSave(sysApplyIn);

        return res;
    }
    
    @PostMapping("/selectSysApplyWorkflowList")
    @ResponseBody
    public TableDataInfo selectSysApplyWorkflowList(SysApplyIn sysApplyIn)
    {
        List<SysApplyWorkflow> list = sysApplyWorkflowService.selectSysApplyWorkflowList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 删除档案入库申请
     */
    @RequiresPermissions("applyIn:remove")
    @Log(title = "档案入库申请", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysApplyInService.deleteSysApplyInByIds(ids));
    }
}
