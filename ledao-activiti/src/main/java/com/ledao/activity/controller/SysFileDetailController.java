package com.ledao.activity.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.service.ISysFileDetailService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 档案详情Controller
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/activity/fileDetail")
public class SysFileDetailController extends BaseController
{
    private String prefix = "activity/fileDetail";

    @Autowired
    private ISysFileDetailService sysFileDetailService;

//    @RequiresPermissions("activity:fileDetail:view")
    @GetMapping()
    public String fileDetail()
    {
        return prefix + "/fileDetail";
    }

    /**
     * 查询档案详情列表
     */
//    @RequiresPermissions("activity:fileDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysFileDetail sysFileDetail)
    {
        startPage();
        List<SysFileDetail> list = sysFileDetailService.selectSysFileDetailList(sysFileDetail);
        return getDataTable(list);
    }

    /**
     * 导出档案详情列表
     */
//    @RequiresPermissions("activity:fileDetail:export")
    @Log(title = "档案详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysFileDetail sysFileDetail)
    {
        List<SysFileDetail> list = sysFileDetailService.selectSysFileDetailList(sysFileDetail);
        ExcelUtil<SysFileDetail> util = new ExcelUtil<SysFileDetail>(SysFileDetail.class);
        return util.exportExcel(list, "fileDetail");
    }

    /**
     * 新增档案详情
     */
    @GetMapping("/add/{documentFileId}")
    public String add(@PathVariable("documentFileId") Long documentFileId,ModelMap modelMap)
    {
        modelMap.put("documentFileId",documentFileId);
        return "fileDetail/add";
    }

    @GetMapping("/detail/{documentId}")
    public String fileDetail(@PathVariable("documentId") Long documentId,ModelMap modelMap) {
//        查看档案附件
        SysFileDetail sysFileDetail = new SysFileDetail();
        sysFileDetail.setDocumentFileId(documentId);
        List<SysFileDetail> des = sysFileDetailService.selectSysFileDetailList(sysFileDetail);
        modelMap.put("sysFileDetail",sysFileDetail);
        modelMap.put("documentFileId",documentId);
        return "fileDetail/detail";
    }

    /**
     * 新增保存档案详情
     */
//    @RequiresPermissions("activity:fileDetail:add")
    @Log(title = "档案详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysFileDetail sysFileDetail,@RequestParam("file") MultipartFile file)
    {
        return toAjax(sysFileDetailService.insertSysFileDetail(sysFileDetail,file));
    }

    /**
     * 修改档案详情
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap)
    {
        SysFileDetail sysFileDetail = sysFileDetailService.selectSysFileDetailById(fileId);
        mmap.put("sysFileDetail", sysFileDetail);
        return "fileDetail/edit";
    }

    /**
     * 修改保存档案详情
     */
//    @RequiresPermissions("activity:fileDetail:edit")
    @Log(title = "档案详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysFileDetail sysFileDetail)
    {
        return toAjax(sysFileDetailService.updateSysFileDetail(sysFileDetail));
    }

    /**
     * 删除档案详情
     */
//    @RequiresPermissions("activity:fileDetail:remove")
    @Log(title = "档案详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysFileDetailService.deleteSysFileDetailByIds(ids));
    }
}
