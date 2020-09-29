package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
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
import com.ledao.system.dao.SysProjectZck;
import com.ledao.system.service.ISysProjectZckService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目管理资产库Controller
 *
 * @author ledao
 * @date 2020-08-12
 */
@Controller
@RequestMapping("/system/projectZck")
public class SysProjectZckController extends BaseController {
    private String prefix = "system/projectZck";

    @Autowired
    private ISysProjectZckService sysProjectZckService;

    @RequiresPermissions("system:projectZck:view")
    @GetMapping()
    public String zck() {
        return prefix + "/projectZck";
    }

    /**
     * 查询项目管理资产库列表
     */
    @RequiresPermissions("system:projectZck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectZck sysProjectZck) {
        startPage();
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        return getDataTable(list);
    }

    /**
     * 根据资产库ID查询项目
     */
    @GetMapping("/projectList/{projectZckId}")
    public String projectList(@PathVariable("projectZckId") String projectZckId, ModelMap modelMap) {
        modelMap.put("projectZckId", projectZckId);
        return "system/project/project";
    }

    /**
     * 导出项目管理资产库列表
     */
    @RequiresPermissions("system:projectZck:export")
    @Log(title = "项目管理资产库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectZck sysProjectZck) {
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        ExcelUtil<SysProjectZck> util = new ExcelUtil<SysProjectZck>(SysProjectZck.class);
        return util.exportExcel(list, "zck");
    }

    /**
     * 新增项目管理资产库
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存项目管理资产库
     */
    @RequiresPermissions("system:projectZck:add")
    @Log(title = "项目管理资产库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectZck sysProjectZck) {
        logger.info("资产库名称：===="+sysProjectZck.getZckName());
        sysProjectZck.setCreateBy(ShiroUtils.getLoginName());
        sysProjectZck.setDelFlag("0");
        return toAjax(sysProjectZckService.insertSysProjectZck(sysProjectZck));
    }

    /**
     * 修改项目管理资产库
     */
    @GetMapping("/edit/{projectZckId}")
    public String edit(@PathVariable("projectZckId") Long projectZckId, ModelMap mmap) {
        SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(projectZckId);
        mmap.put("sysProjectZck", sysProjectZck);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目管理资产库
     */
    @RequiresPermissions("system:projectZck:edit")
    @Log(title = "项目管理资产库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectZck sysProjectZck) {
        sysProjectZck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectZckService.updateSysProjectZck(sysProjectZck));
    }

    /**
     * 删除项目管理资产库
     */
    @RequiresPermissions("system:projectZck:remove")
    @Log(title = "项目管理资产库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectZckService.deleteSysProjectZckByIds(ids));
    }
}
