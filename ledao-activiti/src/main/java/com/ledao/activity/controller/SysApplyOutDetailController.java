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
import com.ledao.activity.dao.SysApplyOutDetail;
import com.ledao.activity.service.ISysApplyOutDetailService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 档案出库详情记录Controller
 * 
 * @author lxz
 * @date 2021-08-10
 */
@Controller
@RequestMapping("/activity/outFiledetail")
public class SysApplyOutDetailController extends BaseController
{
    private String prefix = "activity/outFiledetail";

    @Autowired
    private ISysApplyOutDetailService sysApplyOutDetailService;

    @RequiresPermissions("activity:outFiledetail:view")
    @GetMapping()
    public String outFiledetail()
    {
        return prefix + "/outFiledetail";
    }

    /**
     * 查询档案出库详情记录列表
     */
//    @RequiresPermissions("activity:outFiledetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplyOutDetail sysApplyOutDetail)
    {
        startPage();
        List<SysApplyOutDetail> list = sysApplyOutDetailService.selectSysApplyOutDetailList(sysApplyOutDetail);
        return getDataTable(list);
    }

    /**
     * 查询档案出库详情记录列表
     */
//    @RequiresPermissions("activity:outFiledetail:list")
    @PostMapping("/listDocumentAndDetail")
    @ResponseBody
    public TableDataInfo listDocumentAndDetail(SysApplyOutDetail sysApplyOutDetail)
    {
        startPage();
        List<SysApplyOutDetail> list = sysApplyOutDetailService.listDocumentAndDetail(sysApplyOutDetail);
        return getDataTable(list);
    }

    @PostMapping("/listDocumentAndDetail/{applyId}")
    @ResponseBody
    public TableDataInfo listDocumentAndDetail(SysApplyOutDetail sysApplyOutDetail, @PathVariable("applyId") Long applyId)
    {
        startPage();
        sysApplyOutDetail.setApplyId(applyId);
        List<SysApplyOutDetail> list = sysApplyOutDetailService.listDocumentAndDetail(sysApplyOutDetail);
        return getDataTable(list);
    }

    /**
     * 导出档案出库详情记录列表
     */
    @RequiresPermissions("activity:outFiledetail:export")
    @Log(title = "档案出库详情记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApplyOutDetail sysApplyOutDetail)
    {
        List<SysApplyOutDetail> list = sysApplyOutDetailService.selectSysApplyOutDetailList(sysApplyOutDetail);
        ExcelUtil<SysApplyOutDetail> util = new ExcelUtil<SysApplyOutDetail>(SysApplyOutDetail.class);
        return util.exportExcel(list, "outFiledetail");
    }

    /**
     * 新增档案出库详情记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存档案出库详情记录
     */
//    @RequiresPermissions("activity:outFiledetail:add")
    @Log(title = "档案出库详情记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApplyOutDetail sysApplyOutDetail)
    {
        return toAjax(sysApplyOutDetailService.insertSysApplyOutDetail(sysApplyOutDetail));
    }

    /**
     * 新增保存档案出库详情记录
     */
//    @RequiresPermissions("activity:outFiledetail:add")
    @Log(title = "档案出库添加要出库的档案id", businessType = BusinessType.INSERT)
    @PostMapping("/addDocDetailIds")
    @ResponseBody
    public AjaxResult addDocDetailIds(String ids,long applyId)
    {
        return sysApplyOutDetailService.addDocDetailIds(ids,applyId);
    }

    /**
     * 修改档案出库详情记录
     */
    @GetMapping("/edit/{outDetailId}")
    public String edit(@PathVariable("outDetailId") Long outDetailId, ModelMap mmap)
    {
        SysApplyOutDetail sysApplyOutDetail = sysApplyOutDetailService.selectSysApplyOutDetailById(outDetailId);
        mmap.put("sysApplyOutDetail", sysApplyOutDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案出库详情记录
     */
//    @RequiresPermissions("activity:outFiledetail:edit")
    @Log(title = "档案出库详情记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApplyOutDetail sysApplyOutDetail)
    {
        return toAjax(sysApplyOutDetailService.updateSysApplyOutDetail(sysApplyOutDetail));
    }

    @PostMapping("/saveApplyOutDetails")
    @ResponseBody
    public AjaxResult saveApplyOutDetails(List<SysApplyOutDetail> sysApplyOutDetails,String applyId)
    {
        return toAjax(sysApplyOutDetailService.saveApplyOutDetails(sysApplyOutDetails,applyId));
    }

    /**
     * 删除档案出库详情记录
     */
//    @RequiresPermissions("activity:outFiledetail:remove")
    @Log(title = "档案出库详情记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysApplyOutDetailService.deleteSysApplyOutDetailByIds(ids));
    }
}
