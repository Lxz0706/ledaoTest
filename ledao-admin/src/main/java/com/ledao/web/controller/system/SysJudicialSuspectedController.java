package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.annotation.RepeatSubmit;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysTagging;
import com.ledao.system.service.ISysTaggingService;
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
import com.ledao.system.dao.SysJudicialSuspected;
import com.ledao.system.service.ISysJudicialSuspectedService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 关键词Controller
 *
 * @author lxz
 * @date 2021-01-14
 */
@Controller
@RequestMapping("/system/suspected")
public class SysJudicialSuspectedController extends BaseController {
    private String prefix = "system/suspected";

    @Autowired
    private ISysJudicialSuspectedService sysJudicialSuspectedService;

    @Autowired
    private ISysTaggingService sysTaggingService;

    @RequiresPermissions("system:suspected:view")
    @GetMapping()
    public String suspected() {
        return prefix + "/suspected";
    }

    /**
     * 查询关键词列表
     */
    @RequiresPermissions("system:suspected:list")
    @PostMapping("/list")
    @ResponseBody
    @Log(title = "关键词", businessType = BusinessType.QUERY)
    public TableDataInfo list(SysJudicialSuspected sysJudicialSuspected) {
        startPage();
        List<SysJudicialSuspected> list = sysJudicialSuspectedService.selectSysJudicialSuspectedList(sysJudicialSuspected);
        for (SysJudicialSuspected sysJudicialSuspected1 : list) {
            SysTagging sysTagging = new SysTagging();
            sysTagging.setCreateBy(ShiroUtils.getLoginName());
            //sysTagging.setJudicialId(sysJudicialSuspected1.getId());
            sysTagging.setItemId(sysJudicialSuspected1.getItemId());
            sysTagging.setJudicial("N");
            List<SysTagging> sysTaggingList = sysTaggingService.selectSysTaggingList(sysTagging);
            if (sysTaggingList.size() > 0) {
                sysJudicialSuspected1.setTaggings("Y");
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出关键词列表
     */
    @RequiresPermissions("system:suspected:export")
    @Log(title = "关键词", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJudicialSuspected sysJudicialSuspected) {
        List<SysJudicialSuspected> list = sysJudicialSuspectedService.selectSysJudicialSuspectedList(sysJudicialSuspected);
        ExcelUtil<SysJudicialSuspected> util = new ExcelUtil<SysJudicialSuspected>(SysJudicialSuspected.class);
        return util.exportExcel(list, "suspected");
    }

    /**
     * 新增关键词
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存关键词
     */
    @RequiresPermissions("system:suspected:add")
    @Log(title = "关键词", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJudicialSuspected sysJudicialSuspected) {
        return toAjax(sysJudicialSuspectedService.insertSysJudicialSuspected(sysJudicialSuspected));
    }

    /**
     * 修改关键词
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysJudicialSuspected sysJudicialSuspected = sysJudicialSuspectedService.selectSysJudicialSuspectedById(id);
        mmap.put("sysJudicialSuspected", sysJudicialSuspected);
        return prefix + "/edit";
    }

    /**
     * 修改保存关键词
     */
    @RequiresPermissions("system:suspected:edit")
    @Log(title = "关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJudicialSuspected sysJudicialSuspected) {
        return toAjax(sysJudicialSuspectedService.updateSysJudicialSuspected(sysJudicialSuspected));
    }

    /**
     * 删除关键词
     */
    @RequiresPermissions("system:suspected:remove")
    @Log(title = "关键词", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysJudicialSuspectedService.deleteSysJudicialSuspectedByIds(ids));
    }

    /**
     * 新增保存星标库
     */
    @RequiresPermissions("system:tagging:add")
    @Log(title = "星标库", businessType = BusinessType.INSERT)
    @PostMapping("/addTagging")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult addTagging(SysTagging sysTagging) {
        sysTagging.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysTaggingService.insertSysTagging(sysTagging));
    }


    /**
     * 删除星标库
     */
    @RequiresPermissions("system:tagging:remove")
    @Log(title = "星标库", businessType = BusinessType.DELETE)
    @PostMapping("/removeTagging")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult removeTagging(SysTagging sysTagging) {
        SysTagging sysTagging1 = sysTaggingService.selectSysTaggingByItemId(sysTagging.getItemId());
        return toAjax(sysTaggingService.deleteSysTaggingById(sysTagging1.getId()));
    }
}
