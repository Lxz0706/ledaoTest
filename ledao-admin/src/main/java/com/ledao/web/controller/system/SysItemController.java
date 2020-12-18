package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysItem;
import com.ledao.system.service.ISysItemService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目选择Controller
 *
 * @author lxz
 * @date 2020-12-02
 */
@Controller
@RequestMapping("/system/item")
public class SysItemController extends BaseController {
    private String prefix = "system/item";

    @Autowired
    private ISysItemService sysItemService;

    @RequiresPermissions("system:item:view")
    @GetMapping()
    public String item() {
        return prefix + "/item";
    }

    /**
     * 查询项目选择列表
     */
    @RequiresPermissions("system:item:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysItem sysItem) {
        startPage();
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                sysItem.setCreateBy(ShiroUtils.getLoginName());
            }
        }
        List<SysItem> list = sysItemService.selectSysItemList(sysItem);
        return getDataTable(list);
    }

    /**
     * 导出项目选择列表
     */
    @RequiresPermissions("system:item:export")
    @Log(title = "项目选择", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysItem sysItem) {
        List<SysItem> list = sysItemService.selectSysItemList(sysItem);
        ExcelUtil<SysItem> util = new ExcelUtil<SysItem>(SysItem.class);
        return util.exportExcel(list, "item");
    }

    /**
     * 新增项目选择
     */
    @GetMapping("/add/{customerId}")
    public String add(@PathVariable("customerId") String customerId, ModelMap modelMap) {
        modelMap.put("customerId", customerId);
        return prefix + "/add";
    }

    /**
     * 新增保存项目选择
     */
    @RequiresPermissions("system:item:add")
    @Log(title = "项目选择", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysItem sysItem) {
        return toAjax(sysItemService.insertSysItem(sysItem));
    }

    /**
     * 修改项目选择
     */
    @GetMapping("/edit/{itemId}")
    public String edit(@PathVariable("itemId") Long itemId, ModelMap mmap) {
        SysItem sysItem = sysItemService.selectSysItemById(itemId);
        mmap.put("sysItem", sysItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目选择
     */
    @RequiresPermissions("system:item:edit")
    @Log(title = "项目选择", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysItem sysItem) {
        return toAjax(sysItemService.updateSysItem(sysItem));
    }

    /**
     * 删除项目选择
     */
    @RequiresPermissions("system:item:remove")
    @Log(title = "项目选择", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysItemService.deleteSysItemByIds(ids));
    }

    @RequiresPermissions("system:item:list")
    @GetMapping("/itemList/{customerId}")
    public String itemList(@PathVariable("customerId") String customerId, ModelMap modelMap) {
        modelMap.put("customerId", customerId);
        return "system/item/item";
    }
}
