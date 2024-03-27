package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysReshuffle;
import com.ledao.system.dao.SysStaff;
import com.ledao.system.service.ISysReshuffleService;
import com.ledao.system.service.ISysStaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工岗位异动Controller
 *
 * @author lxz
 * @date 2024-03-19
 */
@Controller
@RequestMapping("/system/reshuffle")
public class SysReshuffleController extends BaseController {
    private String prefix = "system/reshuffle";

    @Autowired
    private ISysReshuffleService sysReshuffleService;

    @Autowired
    private ISysStaffService sysStaffService;

    @RequiresPermissions("system:reshuffle:view")
    @GetMapping()
    public String reshuffle() {
        return prefix + "/reshuffle";
    }

    @GetMapping("/reshuffleList")
    public String reshuffleList(String staffId, ModelMap modelMap) {
        modelMap.put("staffId", staffId);
        return prefix + "/reshuffle";
    }

    /**
     * 查询员工岗位异动列表
     */
    @RequiresPermissions("system:reshuffle:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysReshuffle sysReshuffle) {
        startPage();
        List<SysReshuffle> list = sysReshuffleService.selectSysReshuffleList(sysReshuffle);
        for (SysReshuffle sysReshuffle1 : list) {
            if (StringUtils.isNotNull(sysReshuffle.getStaffId())) {
                SysStaff sysStaff = sysStaffService.selectSysStaffById(sysReshuffle.getStaffId());
                if (StringUtils.isNotNull(sysStaff)) {
                    if (StringUtils.isNotEmpty(sysStaff.getStaffName())) {
                        sysReshuffle1.setStaffName(sysStaff.getStaffName());
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出员工岗位异动列表
     */
    @RequiresPermissions("system:reshuffle:export")
    @Log(title = "员工岗位异动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysReshuffle sysReshuffle) {
        List<SysReshuffle> list = sysReshuffleService.selectSysReshuffleList(sysReshuffle);
        ExcelUtil<SysReshuffle> util = new ExcelUtil<SysReshuffle>(SysReshuffle.class);
        return util.exportExcel(list, "reshuffle");
    }

    /**
     * 新增员工岗位异动
     */
    @GetMapping("/add/{staffId}")
    public String add(@PathVariable("staffId") String staffId, ModelMap modelMap) {
        modelMap.put("staffId", staffId);
        return prefix + "/add";
    }

    /**
     * 新增保存员工岗位异动
     */
    @RequiresPermissions("system:reshuffle:add")
    @Log(title = "员工岗位异动", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysReshuffle sysReshuffle) {
        sysReshuffle.setCreateBy(ShiroUtils.getLoginName());
        sysReshuffle.setCreateTime(DateUtils.getNowDate());
        return toAjax(sysReshuffleService.insertSysReshuffle(sysReshuffle));
    }

    /**
     * 修改员工岗位异动
     */
    @GetMapping("/edit/{reshuffleId}")
    public String edit(@PathVariable("reshuffleId") Long reshuffleId, ModelMap mmap) {
        SysReshuffle sysReshuffle = sysReshuffleService.selectSysReshuffleById(reshuffleId);
        mmap.put("sysReshuffle", sysReshuffle);
        return prefix + "/edit";
    }

    /**
     * 修改保存员工岗位异动
     */
    @RequiresPermissions("system:reshuffle:edit")
    @Log(title = "员工岗位异动", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysReshuffle sysReshuffle) {
        sysReshuffle.setUpdateBy(ShiroUtils.getLoginName());
        sysReshuffle.setUpdateTime(DateUtils.getNowDate());
        return toAjax(sysReshuffleService.updateSysReshuffle(sysReshuffle));
    }

    /**
     * 删除员工岗位异动
     */
    @RequiresPermissions("system:reshuffle:remove")
    @Log(title = "员工岗位异动", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysReshuffleService.deleteSysReshuffleByIds(ids));
    }
}
