package com.ledao.web.controller.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.ledao.common.config.Global;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.MimeTypeUtils;
import com.ledao.common.utils.sql.SqlUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysDeptService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.ISysUserService;
import org.apache.bcel.generic.RET;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
    private DocumentConverter converter; // 转换器

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

    @GetMapping("/toDocumentByType")
    public String toDocumentByType(String type, ModelMap modelMap) {
        modelMap.put("type", type);
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType(type + "_type");
        if (sysDictDataList.size() > 0) {
            return prefix + "/documentType";
        } else {
            return prefix + "/documentByType";
        }
    }

    @GetMapping("/toBackByType")
    public String toBackByType(String type, ModelMap modelMap) {
        modelMap.put("type", type);
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType(type + "_type");
        if (sysDictDataList.size() > 0) {
            return prefix + "/documentType";
        } else {
            return prefix + "/document";
        }
    }

    @GetMapping("/toDocumentType")
    public String toDocumentType(String type, String documentType, ModelMap modelMap) {
        modelMap.put("type", type);
        modelMap.put("documentType", documentType);
        return prefix + "/documentByTypes";
    }

    @GetMapping("/toDocumentByTypes")
    public String toDocumentByTypes(String type, ModelMap modelMap) {
        modelMap.put("type", type);
        return prefix + "/documentByType";
    }

    @PostMapping("/listBySubsetType")
    @ResponseBody
    public TableDataInfo listBySubsetType(SysDocument sysDocument) {
        startPage();
        Map<String, String> typeMap = new HashMap<>();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType(sysDocument.getDocumentType() + "_type");
        /*for (SysDictData sysDictData : sysDictDataList) {
            typeMap.put(sysDictData.getDictValue(), sysDictData.getCssClass());
        }
        List<SysDocument> list = sysDocumentService.listBySubsetType(sysDocument);
        for (SysDocument sysDocument1 : list) {
            if (StringUtils.isNotEmpty(sysDocument1.getSubsetType())) {
                if (StringUtils.isNotEmpty(typeMap.get(sysDocument1.getSubsetType()))) {
                    sysDocument1.setCssClass(typeMap.get(sysDocument1.getSubsetType()));
                }
            }
            if (StringUtils.isEmpty(sysDocument1.getSubsetType())) {
                sysDocument1.setSubsetType("无");
            }
        }*/
        return getDataTable(sysDictDataList);
    }

    /**
     * 查询文件管理列表
     */
    @RequiresPermissions("system:document:list")
    @PostMapping("/listByType")
    @ResponseBody
    public TableDataInfo listByType(SysDocument sysDocument) {
        startPage();
        SysDept sysDept = new SysDept();
        sysDept.setStatus("0");
        //List<SysDept> sysDeptList = sysDeptService.selectDeptList(sysDept);
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"admin".equals(sysRole.getRoleKey())) {
                        sysDocument.setShareUserName(ShiroUtils.getSysUser().getUserName());
                    }
                }
            }
        }
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        for (SysDocument sysDocument1 : list) {
            if (currentUser != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser.isAdmin()) {
                    List<SysRole> getRoles = currentUser.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if ("SJXXB".equals(sysRole.getRoleKey()) || "seniorRoles".equals(sysRole.getRoleKey())
                                || "admin".equals(sysRole.getRoleKey()) || ShiroUtils.getLoginName().equals(sysDocument1.getCreateBy())) {
                            sysDocument1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysDocument1.setIsAdmin("Y");
                }
            }
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
    @GetMapping(value = {"/add"})
    public String add(String documentType, String subsetType, ModelMap modelMap) {
        modelMap.put("documentType", documentType);
        modelMap.put("subsetType", subsetType);
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
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())) {
                                sysUser.setFormalFlag("0");
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
        }
        sysDocument.setShareUserId(userIds.toString());
        sysDocument.setShareUserName(userNames.toString());


        //获取各类型名称及其子集
        String baseDir = "";
        try {
            String avatar = FileUploadUtils.upload(Global.getProfile() + "/document" + baseDir, file, false);
            logger.info("上传的路径：======" + avatar);
            sysDocument.setFileUrl(avatar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //去除子集类型中的，
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(sysDocument.getSubsetType())) {
            for (String string : sysDocument.getSubsetType().split(",")) {
                if (StringUtils.isNotEmpty(string)) {
                    sb.append(string);
                }
            }
        }
        sysDocument.setSubsetType(sb.toString());
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
        String path = sysDocumentService.selectSysDocumentById(fileId).getFileUrl();
        if (StringUtils.isNotEmpty(path)) {
            File file = new File(Global.getProfile().substring(0, Global.getProfile().indexOf("/")) + path);
            if (file.exists()) {
                file.delete();
            }
        }
        return toAjax(sysDocumentService.deleteSysDocumentById(fileId));
    }

    @PostMapping("/selectDocumentByType")
    @ResponseBody
    public List<SysDocument> selectDocumentByType() {
        String orderBy = SqlUtil.escapeOrderBySql(StringUtils.toUnderScoreCase("createTime") + " desc");
        PageHelper.startPage(0, 10, orderBy);
        SysDocument sysDocument = new SysDocument();
        sysDocument.setDocumentType(getRequest().getParameter("type"));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"admin".equals(sysRole.getRoleKey())) {
                        sysDocument.setShareUserName(ShiroUtils.getSysUser().getUserName());
                    }
                }
            }
        }
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        return list;
    }

    @GetMapping("/toList")
    public String toList() {
        return prefix + "/document";
    }

    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysDocument sysDocument) {
        modelMap.put("sysDocument", sysDocument);
        return prefix + "/queryAll";
    }

    @PostMapping("/listes")
    @ResponseBody
    public TableDataInfo listes(SysDocument sysDocument) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (null != currentUser) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"admin".equals(sysRole.getRoleKey())) {
                        sysDocument.setShareUserName(ShiroUtils.getSysUser().getUserName());
                    }
                }
            }
        }
        List<SysDocument> list = sysDocumentService.selectSysDocumentList(sysDocument);
        for (SysDocument sysDocument1 : list) {
            if (currentUser != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser.isAdmin()) {
                    List<SysRole> getRoles = currentUser.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if ("SJXXB".equals(sysRole.getRoleKey()) || "seniorRoles".equals(sysRole.getRoleKey())
                                || "admin".equals(sysRole.getRoleKey()) || ShiroUtils.getLoginName().equals(sysDocument1.getCreateBy())) {
                            sysDocument1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysDocument1.setIsAdmin("Y");
                }
            }

            if (StringUtils.isNotEmpty(sysDocument1.getShareUserName()) && StringUtils.isNotEmpty(sysDocument1.getShareDeptName())) {
                sysDocument1.setShareDeptAndUser(sysDocument1.getShareDeptName() + "," + sysDocument1.getShareUserName());
            } else if (StringUtils.isEmpty(sysDocument1.getShareDeptName())) {
                sysDocument1.setShareDeptAndUser(sysDocument1.getShareUserName());
            } else if (StringUtils.isEmpty(sysDocument1.getShareUserName())) {
                sysDocument1.setShareDeptAndUser(sysDocument1.getShareDeptName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 统计下载次数
     */
    @PostMapping("/updateDownLoadCount")
    @ResponseBody
    public AjaxResult updateDownLoadCount(SysDocument sysDocument) {
        SysDocument sysDocument1 = sysDocumentService.selectSysDocumentById(sysDocument.getFileId());
        if (StringUtils.isNull(sysDocument1.getDownloadsCount())) {
            sysDocument1.setDownloadsCount(1L);
        } else {
            sysDocument1.setDownloadsCount(Long.valueOf(String.valueOf(new BigDecimal(sysDocument1.getDownloadsCount()).add(new BigDecimal(1)))));
        }
        sysDocumentService.updateSysDocument(sysDocument1);
        return AjaxResult.success();
    }

    /**
     * 统计预览次数
     */
    @PostMapping("/previewCount")
    @ResponseBody
    public AjaxResult previewCount(SysDocument sysDocument) {
        SysDocument sysDocument1 = sysDocumentService.selectSysDocumentById(sysDocument.getFileId());
        if (StringUtils.isNull(sysDocument1.getPreviewCount())) {
            sysDocument1.setPreviewCount(1L);
        } else {
            sysDocument1.setPreviewCount(Long.valueOf(String.valueOf(new BigDecimal(sysDocument1.getPreviewCount()).add(new BigDecimal(1)))));
        }
        sysDocumentService.updateSysDocument(sysDocument1);
        return AjaxResult.success();
    }

}
