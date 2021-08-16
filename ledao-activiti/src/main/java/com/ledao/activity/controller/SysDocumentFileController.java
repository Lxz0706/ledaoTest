package com.ledao.activity.controller;

import java.util.Arrays;
import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.common.core.text.Convert;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.service.ISysDocumentFileService;
import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;

import javax.servlet.http.HttpSession;

/**
 * 档案Controller
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/documentFile")
public class SysDocumentFileController extends BaseController
{
    private String prefix = "documentFile";

    @Autowired
    private ISysDocumentFileService sysDocumentFileService;

    @Autowired
    private ISysApplyInService sysApplyInService;

    @RequiresPermissions("activity:documentFile:view")
    @GetMapping()
    public String documentFile()
    {
        return prefix + "/documentFile";
    }

    /**
     * 查询档案列表
     */
//    @RequiresPermissions("activity:documentFile:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDocumentFile sysDocumentFile)
    {
        startPage();
        List<SysDocumentFile> list = sysDocumentFileService.selectSysDocumentFileList(sysDocumentFile);
        return getDataTable(list);
    }

    /**
     * 导出档案列表
     */
//    @RequiresPermissions("activity:documentFile:export")
    @Log(title = "档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDocumentFile sysDocumentFile)
    {
        List<SysDocumentFile> list = sysDocumentFileService.selectSysDocumentFileList(sysDocumentFile);
        ExcelUtil<SysDocumentFile> util = new ExcelUtil<SysDocumentFile>(SysDocumentFile.class);
        return util.exportExcel(list, "documentFile");
    }

    /**
     * 新增档案
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存档案
     */
//    @RequiresPermissions("activity:documentFile:add")
    @Log(title = "档案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HttpSession session, SysDocumentFile sysDocumentFile, @RequestParam("files")MultipartFile[] files )
    {
        boolean isChange = sysDocumentFileService.isInChangeStatus(sysDocumentFile.getApplyId());
        if(!isChange){
            return AjaxResult.error("当前状态不可修改");
        }
    	sysDocumentFileService.insertSysDocumentFile(sysDocumentFile,files);
        return toAjax(true);
    }

    /**
     * 修改档案
     */
    @GetMapping("/edit/{documentId}")
    public String edit(@PathVariable("documentId") Long documentId, ModelMap mmap)
    {
        SysDocumentFile sysDocumentFile = sysDocumentFileService.selectSysDocumentFileById(documentId);
        mmap.put("sysDocumentFile", sysDocumentFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案
     */
    @RequiresPermissions("activity:documentFile:edit")
    @Log(title = "档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDocumentFile sysDocumentFile)
    {
        SysApplyIn ap = sysApplyInService.selectSysApplyInById(sysDocumentFile.getApplyId());
        if (!ap.getApplyUser().equals(ShiroUtils.getLoginName())){
            SysUser user = ShiroUtils.getSysUser();
            boolean isFileAdmin = false;
            for (SysRole role:user.getRoles()){
                if (role.getRoleKey()=="documentAdmin"){
                    isFileAdmin=true;
                }
            }
            if (!isFileAdmin){
                return AjaxResult.error("当前申请不可修改");
            }
        }else{
            boolean isChange = sysDocumentFileService.isInChangeStatus(sysDocumentFile.getApplyId());
            if(!isChange){
                return AjaxResult.error("当前申请不可修改");
            }
        }
        return toAjax(sysDocumentFileService.updateSysDocumentFile(sysDocumentFile));
    }

    /**
     * 删除档案
     */
    @RequiresPermissions("activity:documentFile:remove")
    @Log(title = "档案", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        logger.info("开始进行档案删除操作");
        String[] idArray = Convert.toStrArray(ids);
        SysDocumentFile d = sysDocumentFileService.selectSysDocumentFileById(Long.parseLong(idArray[0]));
        SysApplyIn ap = sysApplyInService.selectSysApplyInById(d.getApplyId());
        if (!ap.getApplyUser().equals(ShiroUtils.getLoginName())){
            return AjaxResult.error("当前申请不可修改");
        }
        String[] applyStatusList = {"0","4","2"};
        // 可提交审批的状态     0保存；4撤回；2拒绝
        if (!Arrays.asList(applyStatusList).contains(ap.getApproveStatu())){
            return AjaxResult.error("当前申请不可修改");
        }
        return toAjax(sysDocumentFileService.deleteSysDocumentFileByIds(ids));
    }
}
