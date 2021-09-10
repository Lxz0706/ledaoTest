package com.ledao.web.controller.system;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ledao.common.config.Global;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.service.ISysUnderlyingDataService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 底层资料Controller
 * 
 * @author lxz
 * @date 2021-09-07
 */
@Controller
@RequestMapping("/system/underlyingdata")
public class SysUnderlyingDataController extends BaseController
{
    private String prefix = "system/underlyingdata";

    @Autowired
    private ISysUnderlyingDataService sysUnderlyingDataService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequiresPermissions("system:underlyingdata:view")
    @GetMapping()
    public String underlyingdata()
    {
        return prefix + "/underlyingdata";
    }

    /**
     * 查询底层资料列表
     */
    @RequiresPermissions("system:underlyingdata:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUnderlyingData sysUnderlyingData)
    {
        startPage();
        List<SysUnderlyingData> list = sysUnderlyingDataService.selectSysUnderlyingDataList(sysUnderlyingData);
        return getDataTable(list);
    }

    @PostMapping("/listLying")
    @ResponseBody
    public TableDataInfo listLying(SysUnderlyingData sysUnderlyingData)
    {
        startPage();
        List<SysUnderlyingData> list = sysUnderlyingDataService.selectSysUnderlyingProDataList(sysUnderlyingData);
        return getDataTable(list);
    }

    /**
     * 导出底层资料列表
     */
    @RequiresPermissions("system:underlyingdata:export")
    @Log(title = "底层资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUnderlyingData sysUnderlyingData)
    {
        List<SysUnderlyingData> list = sysUnderlyingDataService.selectSysUnderlyingDataList(sysUnderlyingData);
        ExcelUtil<SysUnderlyingData> util = new ExcelUtil<SysUnderlyingData>(SysUnderlyingData.class);
        return util.exportExcel(list, "underlyingdata");
    }

    /**
     * 新增底层资料
     */
    @GetMapping("/add")
    public String add(@RequestParam("projectId")String projectId, @RequestParam("projectType")String projectType, ModelMap mmap)
    {
        mmap.put("projectId",projectId);
        mmap.put("projectType",projectType);
        return prefix + "/add";
    }


    /**
     * 查询底层资料
     */
    @GetMapping("/list")
    public String list(String projectId, String projectType, ModelMap mmap)
    {
        mmap.put("projectId",projectId);
        mmap.put("projectType",projectType);
        return "system/trainadmin/underlyingDataLists";
    }

    @GetMapping("/muList")
    public String muList()
    {
        return prefix + "/underLyingMn";
    }

    @GetMapping("/documentDetailTypeListMu")
    public String documentDetailTypeListMu()
    {
        return prefix + "/documentDetailTypeList";
    }

    @GetMapping("/muUnderlyingDataListsMu")
    public String muUnderlyingDataListsMu(String dataType, String projectType, ModelMap mmap)
    {
        mmap.put("dataType",dataType);
        mmap.put("projectType",projectType);
        return prefix + "/muUnderlyingDataLists";
    }

    @PostMapping("/documentDetailTypeList")
    @ResponseBody
    public TableDataInfo documentDetailTypeList() {
        List<SysDictData> sysDictDataList = new ArrayList<>();
//        项目类
        startPage();
        sysDictDataList = sysDictDataService.selectDictDataByType("sys_project_type");
        return getDataTable(sysDictDataList);
    }
    /**
     * 新增保存文件管理
     */
    @RequiresPermissions("system:underlyingdata:add")
    @Log(title = "底层资料", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysUnderlyingData sysUnderlyingData, @RequestParam("file") MultipartFile file) {
        StringBuffer userIds = new StringBuffer();
        StringBuffer userNames = new StringBuffer();
        if (file.isEmpty()) {
            return error("请上传文件！");
        }

        String fileName = file.getOriginalFilename();
        sysUnderlyingData.setFileName(fileName.substring(0, fileName.indexOf(".")));
        sysUnderlyingData.setFileSize((double) file.getSize());
        sysUnderlyingData.setCreateBy(ShiroUtils.getLoginName());
        sysUnderlyingData.setFileType(FileUploadUtils.getExtension(file));
        //获取各类型名称及其子集
        String baseDir = "";
        try {
            String avatar = FileUploadUtils.upload(Global.getProfile() + "/underlyingdata" + baseDir, file, false);
            sysUnderlyingData.setFileUrl(avatar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysUnderlyingData.setCreateor(ShiroUtils.getSysUser().getUserName());
        sysUnderlyingData.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysUnderlyingDataService.insertSysUnderlyingData(sysUnderlyingData));
    }

    /**
     * 修改底层资料
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap)
    {
        SysUnderlyingData sysUnderlyingData = sysUnderlyingDataService.selectSysUnderlyingDataById(fileId);
        mmap.put("sysUnderlyingData", sysUnderlyingData);
        return prefix + "/edit";
    }

    /**
     * 修改保存底层资料
     */
    @RequiresPermissions("system:underlyingdata:edit")
    @Log(title = "底层资料", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysUnderlyingData sysUnderlyingData)
    {
        return toAjax(sysUnderlyingDataService.updateSysUnderlyingData(sysUnderlyingData));
    }

    /**
     * 删除底层资料
     */
    @RequiresPermissions("system:underlyingdata:remove")
    @Log(title = "底层资料", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysUnderlyingDataService.deleteSysUnderlyingDataByIds(ids));
    }


    /**
     * 统计下载次数
     */
    @PostMapping("/updateDownLoadCount")
    @ResponseBody
    public AjaxResult updateDownLoadCount(SysUnderlyingData sysUnderlyingData) {
        SysUnderlyingData sysUnderlyingData1 = sysUnderlyingDataService.selectSysUnderlyingDataById(sysUnderlyingData.getFileId());
        if (StringUtils.isNull(sysUnderlyingData1.getDownloadsCount())) {
            sysUnderlyingData1.setDownloadsCount(1L);
        } else {
            sysUnderlyingData1.setDownloadsCount(Long.valueOf(String.valueOf(new BigDecimal(sysUnderlyingData1.getDownloadsCount()).add(new BigDecimal(1)))));
        }
        sysUnderlyingDataService.updateSysUnderlyingData(sysUnderlyingData1);
        return AjaxResult.success();
    }

    /**
     * 统计预览次数
     */
    @PostMapping("/previewCount")
    @ResponseBody
    public AjaxResult previewCount(SysUnderlyingData sysUnderlyingData) {
        SysUnderlyingData sysUnderlyingData1 = sysUnderlyingDataService.selectSysUnderlyingDataById(sysUnderlyingData.getFileId());
        if (StringUtils.isNull(sysUnderlyingData1.getPreviewCount())) {
            sysUnderlyingData1.setPreviewCount(1L);
        } else {
            sysUnderlyingData1.setPreviewCount(Long.valueOf(String.valueOf(new BigDecimal(sysUnderlyingData1.getPreviewCount()).add(new BigDecimal(1)))));
        }
        sysUnderlyingDataService.updateSysUnderlyingData(sysUnderlyingData1);
        return AjaxResult.success();
    }

}
