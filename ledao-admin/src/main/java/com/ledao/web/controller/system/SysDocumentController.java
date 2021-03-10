package com.ledao.web.controller.system;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.config.Global;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysDocument;
import com.ledao.system.service.ISysDocumentService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理Controller
 *
 * @author lxz
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/system/document")
public class SysDocumentController extends BaseController {
    private String prefix = "system/document";

    @Autowired
    private ISysDocumentService sysDocumentService;

    @RequiresPermissions("system:document:view")
    @GetMapping()
    public String document() {
        return prefix + "/document";
    }

    /**
     * 查询文件管理列表
     */
    @RequiresPermissions("system:document:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDocument> list(SysDocument sysDocument) {
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        return list;
    }

    /**
     * 导出文件管理列表
     */
    @RequiresPermissions("system:document:export")
    @Log(title = "文件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDocument sysDocument) {
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        ExcelUtil<SysDocument> util = new ExcelUtil<SysDocument>(SysDocument.class);
        return util.exportExcel(list, "document");
    }

    /**
     * 新增文件管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件管理
     */
    @RequiresPermissions("system:document:add")
    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDocument sysDocument, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return error("请上传文件！");
        }
        String fileName = file.getOriginalFilename();
        sysDocument.setFileName(fileName.substring(0, fileName.indexOf(".")));
        sysDocument.setFileSize((double) file.getSize());
        sysDocument.setFileType(FileUploadUtils.getExtension(file));
        sysDocument.setFileVersion("A");
        try {
            String avatar = FileUploadUtils.upload(Global.getProfile() + "/document", file, false);
            sysDocument.setFileUrl(avatar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysDocument.setCreateor(ShiroUtils.getSysUser().getUserName());
        sysDocument.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysDocumentService.insertSysDocument(sysDocument));
    }

    /**
     * 修改文件管理
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap) {
        SysDocument sysDocument = sysDocumentService.selectSysDocumentById(fileId);
        mmap.put("sysDocument", sysDocument);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件管理
     */
    @RequiresPermissions("system:document:edit")
    @Log(title = "文件管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDocument sysDocument) {
        return toAjax(sysDocumentService.updateSysDocument(sysDocument));
    }

    /**
     * 删除文件管理
     */
    @RequiresPermissions("system:document:remove")
    @Log(title = "文件管理", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{fileId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("fileId") Long fileId) {
        return toAjax(sysDocumentService.deleteSysDocumentById(fileId));
    }

    @PostMapping("/selectDocumentByType")
    @ResponseBody
    public List<SysDocument> selectDocumentByType() {
        PageHelper.startPage(0, 10);
        SysDocument sysDocument = new SysDocument();
        sysDocument.setDocumentType(getRequest().getParameter("type"));
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        return list;
    }
}
