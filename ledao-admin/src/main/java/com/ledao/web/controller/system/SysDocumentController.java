package com.ledao.web.controller.system;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.config.Global;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.sql.SqlUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysDeptService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
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

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequiresPermissions("system:document:view")
    @GetMapping()
    public String document() {
        return prefix + "/document";
    }

    /**
     * 查询文件类型进行分类
     */
    @RequiresPermissions("system:document:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType("sys_document_type");
        return getDataTable(sysDictDataList);
    }

    @GetMapping("/toDocumentByType/{type}")
    public String toDocumentByType(@PathVariable("type") String type, ModelMap modelMap) {
        modelMap.put("type", type);
        return prefix + "/documentByType";
    }

    /**
     * 查询文件管理列表
     */
    @RequiresPermissions("system:document:list")
    @PostMapping("/listByType")
    @ResponseBody
    public TableDataInfo listByType(SysDocument sysDocument) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"admin".equals(sysRole.getRoleKey())) {
                        sysDocument.setShareUserId(ShiroUtils.getUserId().toString());
                    }
                }
            }
        }
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        for (SysDocument sysDocument1 : list) {
            SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                                && !"admin".equals(sysRole.getRoleKey())) {
                            sysDocument1.setIsAdmin("N");
                        } else {
                            sysDocument1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysDocument1.setIsAdmin("Y");
                }
            }
            sysDocument1.setShareDeptAndUser(sysDocument1.getShareDeptName() + "," + sysDocument1.getShareUserName());
        }
        return getDataTable(list);
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
    @GetMapping(value = {"/add", "/add/{type}"})
    public String add(@PathVariable(value = "type", required = false) String type, ModelMap modelMap) {
        modelMap.put("documentType", type);
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
        StringBuffer userIds = new StringBuffer();
        StringBuffer userNames = new StringBuffer();
        if (file.isEmpty()) {
            return error("请上传文件！");
        }
        String fileName = file.getOriginalFilename();
        sysDocument.setFileName(fileName.substring(0, fileName.indexOf(".")));
        sysDocument.setFileSize((double) file.getSize());
        sysDocument.setFileType(FileUploadUtils.getExtension(file));
        sysDocument.setFileVersion("A");
        if (StringUtils.isNotEmpty(sysDocument.getShareUserId())) {
            userIds.append(sysDocument.getShareUserId());
        }
        if (StringUtils.isNotEmpty(sysDocument.getShareUserName())) {
            userNames.append(sysDocument.getShareUserName());
        }
        if (StringUtils.isNotEmpty(sysDocument.getShareDeptId())) {
            for (String string : sysDocument.getShareDeptId().split(",")) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(Long.valueOf(string));
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
        }
        sysDocument.setShareUserId(userIds.toString());
        sysDocument.setShareUserName(userNames.toString());
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
    @PostMapping("/remove/{fileId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("fileId") Long fileId) {
        return toAjax(sysDocumentService.deleteSysDocumentById(fileId));
    }

    @PostMapping("/selectDocumentByType")
    @ResponseBody
    public List<SysDocument> selectDocumentByType() {
        String orderBy = SqlUtil.escapeOrderBySql(StringUtils.toUnderScoreCase("createTime") + " desc");
        PageHelper.startPage(0, 10, orderBy);
        SysDocument sysDocument = new SysDocument();
        sysDocument.setDocumentType(getRequest().getParameter("type"));
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        return list;
    }

    @GetMapping("/toList")
    public String toList() {
        return prefix + "/document";
    }
}
