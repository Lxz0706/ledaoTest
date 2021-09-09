package com.ledao.activity.controller;

import java.util.Arrays;
import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.common.core.text.Convert;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysRole;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SysDocumentFileController.class);

    @Autowired
    private ISysDocumentFileService sysDocumentFileService;

    @Autowired
    private ISysApplyInService sysApplyInService;

    @Autowired
    private com.ledao.system.service.ISysUserService ISysUserService;

//    @RequiresPermissions("activity:documentFile:view")
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
        List<SysDocumentFile> list = sysDocumentFileService.selectSysDocumentFileDetailList(sysDocumentFile);
        return getDataTable(list);
    }

    @PostMapping("/listDocApp")
    @ResponseBody
    public TableDataInfo listDocApp(SysDocumentFile sysDocumentFile)
    {
        startPage();
        List<SysDocumentFile> list = sysDocumentFileService.selectSysDocumentFileList(sysDocumentFile);
        return getDataTable(list);
    }

    @PostMapping("/listDocApp/{applyId}")
    @ResponseBody
    public TableDataInfo listDocApp(SysDocumentFile sysDocumentFile,@PathVariable("applyId") Long applyId)
    {
        startPage();
        sysDocumentFile.setApplyId(applyId);
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
        String loginName = ShiroUtils.getLoginName();
        SysDocumentFile f = new SysDocumentFile();
        f.setAssetPag(sysDocumentFile.getAssetPag());
        f.setAssetNumber(sysDocumentFile.getAssetNumber());
        f.setBagNo(sysDocumentFile.getBagNo());
        f.setDocumentType(sysDocumentFile.getDocumentType());
        f.setDailyDocumentType(sysDocumentFile.getDailyDocumentType());
        f.setFileName(sysDocumentFile.getFileName());
        f.setFileType(sysDocumentFile.getFileType());
        f.setCreateBy(loginName);
        f.setFileScanType(sysDocumentFile.getFileScanType());
        f.setApplyId(sysDocumentFile.getApplyId());
        f.setDailyDocumentTypeContract(sysDocumentFile.getDailyDocumentTypeContract());
        List<SysDocumentFile> ss = sysDocumentFileService.selectSysDocumentFileTotalList(f);
        if (ss !=null && ss.size()>0){
            return AjaxResult.error("存在重复记录，请检查");
        }
        if (StringUtils.isNotEmpty(sysDocumentFile.getDocumentTypeVal())){
            sysDocumentFile.setDocumentType(sysDocumentFile.getDocumentTypeVal());
        }
        sysDocumentFile.setReviser(loginName);
        sysDocumentFile.setCreateBy(loginName);
    	sysDocumentFileService.insertSysDocumentFile(sysDocumentFile,files);
        SysApplyIn ap =  sysApplyInService.selectSysApplyInById(sysDocumentFile.getApplyId());
        ap.setReviser(ShiroUtils.getLoginName());
        sysApplyInService.updateSysApplyIn(ap);
        return toAjax(true);
    }

    /**
     * 修改档案
     */
    @GetMapping("/edit/{documentId}/{applyTypeUnDone}")
    public String edit(@PathVariable("documentId") Long documentId,
                       @PathVariable("applyTypeUnDone") String applyTypeUnDone,
                       @PathVariable("dailyDocumentTypeContract") String dailyDocumentTypeContract,ModelMap mmap)
    {
        SysDocumentFile sysDocumentFile = sysDocumentFileService.selectSysDocumentFileById(documentId);
//        sysDocumentFile.setCreatorName(ISysUserService.selectUserByLoginName(sysDocumentFile.getCreator()).getUserName());
//        sysDocumentFile.setReviserName(ISysUserService.selectUserByLoginName(sysDocumentFile.getReviser()).getUserName());
        mmap.put("sysDocumentFile", sysDocumentFile);
        mmap.put("applyTypeUnDone", applyTypeUnDone);
        mmap.put("dailyDocumentTypeContract", dailyDocumentTypeContract);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{documentId}")
    public String edit(@PathVariable("documentId") Long documentId, ModelMap mmap)
    {
        SysDocumentFile sysDocumentFile = sysDocumentFileService.selectSysDocumentFileById(documentId);
//        sysDocumentFile.setCreatorName(ISysUserService.selectUserByLoginName(sysDocumentFile.getCreator()).getUserName());
//        sysDocumentFile.setReviserName(ISysUserService.selectUserByLoginName(sysDocumentFile.getReviser()).getUserName());
        mmap.put("sysDocumentFile", sysDocumentFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案
     */
//    @RequiresPermissions("activity:documentFile:edit")
    @Log(title = "档案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDocumentFile sysDocumentFile)
    {
        String loginName = ShiroUtils.getLoginName();
        SysDocumentFile f = new SysDocumentFile();
        f.setAssetPag(sysDocumentFile.getAssetPag());
        f.setAssetNumber(sysDocumentFile.getAssetNumber());
        f.setBagNo(sysDocumentFile.getBagNo());
        f.setDocumentType(sysDocumentFile.getDocumentType());
        f.setDailyDocumentType(sysDocumentFile.getDailyDocumentType());
        f.setFileName(sysDocumentFile.getFileName());
        f.setFileType(sysDocumentFile.getFileType());
        f.setCreateBy(loginName);
        f.setFileScanType(sysDocumentFile.getFileScanType());
        f.setApplyId(sysDocumentFile.getApplyId());
        List<SysDocumentFile> ss = sysDocumentFileService.selectSysDocumentFileTotalList(f);
        if (ss !=null && ss.size()>0){
            for (SysDocumentFile df:ss) {
                if (df.getDocumentId().longValue()!=sysDocumentFile.getDocumentId().longValue()){
                    return AjaxResult.error("存在重复记录，请检查");
                }
            }

        }
        AjaxResult res = toAjax(sysDocumentFileService.updateSysDocumentFile(sysDocumentFile));
        SysApplyIn ap =  sysApplyInService.selectSysApplyInById(sysDocumentFile.getApplyId());
        ap.setReviser(ShiroUtils.getLoginName());
        sysApplyInService.updateSysApplyIn(ap);
        return res;
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
        return toAjax(sysDocumentFileService.deleteSysDocumentFileByIds(ids));
    }
}
