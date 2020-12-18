package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysJudicialService;
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
import com.ledao.system.dao.SysTagging;
import com.ledao.system.service.ISysTaggingService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 星标库Controller
 *
 * @author lxz
 * @date 2020-11-03
 */
@Controller
@RequestMapping("/system/tagging")
public class SysTaggingController extends BaseController {
    private String prefix = "system/tagging";

    @Autowired
    private ISysTaggingService sysTaggingService;

    @Autowired
    private ISysJudicialService sysJudicialService;

    @RequiresPermissions("system:tagging:view")
    @GetMapping()
    public String tagging() {
        return prefix + "/tagging";
    }

    /**
     * 查询星标库列表
     */
    @RequiresPermissions("system:tagging:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysTagging sysTagging) {
        startPage();

        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                sysTagging.setCreateBy(ShiroUtils.getLoginName());
            }
        }
        List<SysTagging> list = sysTaggingService.selectSysTaggingList(sysTagging);
        for (SysTagging sysTagging1 : list) {
            SysJudicial sysJudicial = sysJudicialService.selectSysJudicialById(sysTagging1.getJudicialId());
            sysTagging1.setTitle(sysJudicial.getItemTitle());
            sysTagging1.setName(sysJudicial.getItemOwner());
            sysTagging1.setLink(sysJudicial.getItemLink());
            sysTagging1.setSource(sysJudicial.getItemSource());
            sysTagging1.setItemType(sysJudicial.getItemType());
            sysTagging1.setItemInitialprice(sysJudicial.getItemInitialprice());
            sysTagging1.setItemCurrentprice(sysJudicial.getItemCurrentprice());
            sysTagging1.setItemStartTime(sysJudicial.getItemStartTime());
            sysTagging1.setItemEndTime(sysJudicial.getItemEndTime());
            sysTagging1.setItemStatus(sysJudicial.getItemStatus());
        }
        return getDataTable(list);
    }

    /**
     * 导出星标库列表
     */
    @RequiresPermissions("system:tagging:export")
    @Log(title = "星标库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysTagging sysTagging) {
        List<SysTagging> list = sysTaggingService.selectSysTaggingList(sysTagging);
        ExcelUtil<SysTagging> util = new ExcelUtil<SysTagging>(SysTagging.class);
        return util.exportExcel(list, "tagging");
    }

    /**
     * 新增星标库
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存星标库
     */
    @RequiresPermissions("system:tagging:add")
    @Log(title = "星标库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysTagging sysTagging) {
        sysTagging.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysTaggingService.insertSysTagging(sysTagging));
    }

    /**
     * 修改星标库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysTagging sysTagging = sysTaggingService.selectSysTaggingById(id);
        mmap.put("sysTagging", sysTagging);
        return prefix + "/edit";
    }

    /**
     * 修改保存星标库
     */
    @RequiresPermissions("system:tagging:edit")
    @Log(title = "星标库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysTagging sysTagging) {
        return toAjax(sysTaggingService.updateSysTagging(sysTagging));
    }

    /**
     * 删除星标库
     */
    @RequiresPermissions("system:tagging:remove")
    @Log(title = "星标库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysTaggingService.deleteSysTaggingByIds(ids));
    }
}
