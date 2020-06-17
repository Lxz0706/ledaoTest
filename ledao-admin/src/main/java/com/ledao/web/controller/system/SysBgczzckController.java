package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.domain.SysZck;
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
import com.ledao.system.domain.SysBgczzck;
import com.ledao.system.service.ISysBgczzckService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 重组并购项目信息库Controller
 *
 * @author lxz
 * @date 2020-06-16
 */
@Controller
@RequestMapping("/system/bgczzck")
public class SysBgczzckController extends BaseController {
    private String prefix = "system/bgczzck";

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    @RequiresPermissions("system:bgczzck:view")
    @GetMapping()
    public String bgczzck() {
        return prefix + "/bgczzck";
    }

    /**
     * 查询重组并购项目信息库列表
     */
    @RequiresPermissions("system:bgczzck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysBgczzck sysBgczzck) {
        startPage();
        List<SysBgczzck> list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        return getDataTable(list);
    }

    /**
     * 导出重组并购项目信息库列表
     */
    @RequiresPermissions("system:bgczzck:export")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBgczzck sysBgczzck) {
        List<SysBgczzck> list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        return util.exportExcel(list, "bgczzck");
    }

    /**
     * 新增重组并购项目信息库
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:add")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysBgczzck sysBgczzck) {
        sysBgczzck.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysBgczzckService.insertSysBgczzck(sysBgczzck));
    }

    /**
     * 修改重组并购项目信息库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(id);
        mmap.put("sysBgczzck", sysBgczzck);
        return prefix + "/edit";
    }

    /**
     * 修改保存重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:edit")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysBgczzck sysBgczzck) {
        sysBgczzck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysBgczzckService.updateSysBgczzck(sysBgczzck));
    }

    /**
     * 删除重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:remove")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysBgczzckService.deleteSysBgczzckByIds(ids));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:bgczzck:detail")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysBgczzck", sysBgczzckService.selectSysBgczzckById(id));
        return prefix + "/detail";
    }

    @Log(title = "重组并购项目信息库", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:bgczzck:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        List<SysBgczzck> sysBgczzckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysBgczzckService.importBgczzk(sysBgczzckList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:bgczzck:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        return util.importTemplateExcel("重组并购项目信息库");
    }

}
