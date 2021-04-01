package com.ledao.web.controller.system;

import java.text.ParseException;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.utils.DateUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysRole;
import com.ledao.system.dao.SysUser;
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
import com.ledao.system.dao.SysWorkLog;
import com.ledao.system.service.ISysWorkLogService;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 工作日志Controller
 *
 * @author lxz
 * @date 2021-03-17
 */
@Controller
@RequestMapping("/system/workLog")
public class SysWorkLogController extends BaseController {
    private String prefix = "system/workLog";

    @Autowired
    private ISysWorkLogService sysWorkLogService;

    @RequiresPermissions("system:workLog:view")
    @GetMapping()
    public String workLog() {
        return prefix + "/workLog";
    }

    /**
     * 查询工作日志列表
     */
    @RequiresPermissions("system:workLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysWorkLog sysWorkLog) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        /*sysWorkLog.setCreateBy(ShiroUtils.getLoginName());
                        sysWorkLog.setShareUserName(ShiroUtils.getSysUser().getUserName());*/
                        sysWorkLog.setDeptId(ShiroUtils.getSysUser().getDeptId());
                    }
                }
            }
        }
        startPage();
        List<SysWorkLog> list = sysWorkLogService.selectSysWorkLogList(sysWorkLog);
        for (SysWorkLog sysWorkLog1 : list) {
            sysWorkLog1.setWorkLogTitle(sysWorkLog1.getCreator() + DateUtils.dateTime(sysWorkLog1.getCreateTime()) + "工作日志");
        }
        return getDataTable(list);
    }

    /**
     * 导出工作日志列表
     */
    @RequiresPermissions("system:workLog:export")
    @Log(title = "工作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysWorkLog sysWorkLog) {
        List<SysWorkLog> list = sysWorkLogService.selectSysWorkLogList(sysWorkLog);
        ExcelUtil<SysWorkLog> util = new ExcelUtil<SysWorkLog>(SysWorkLog.class);
        return util.exportExcel(list, "workLog");
    }

    /**
     * 新增工作日志
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存工作日志
     */
    @RequiresPermissions("system:workLog:add")
    @Log(title = "工作日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysWorkLog sysWorkLog) throws ParseException {
        sysWorkLog.setCreateBy(ShiroUtils.getLoginName());
        sysWorkLog.setCreator(ShiroUtils.getSysUser().getUserName());
        sysWorkLog.setDeptId(ShiroUtils.getSysUser().getDeptId());
        sysWorkLog.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
        if (!DateUtils.isInTime(DateUtils.getDate(), "18:00", "24:00")) {
            return error("请在下午6点至夜里12点之间填写");
        }
        return toAjax(sysWorkLogService.insertSysWorkLog(sysWorkLog));
    }

    /**
     * 修改工作日志
     */
    @GetMapping("/edit/{workLogId}")
    public String edit(@PathVariable("workLogId") Long workLogId, ModelMap mmap) {
        SysWorkLog sysWorkLog = sysWorkLogService.selectSysWorkLogById(workLogId);
        mmap.put("sysWorkLog", sysWorkLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存工作日志
     */
    @RequiresPermissions("system:workLog:edit")
    @Log(title = "工作日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysWorkLog sysWorkLog) {
        return toAjax(sysWorkLogService.updateSysWorkLog(sysWorkLog));
    }

    /**
     * 删除工作日志
     */
    @RequiresPermissions("system:workLog:remove")
    @Log(title = "工作日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysWorkLogService.deleteSysWorkLogByIds(ids));
    }

    /**
     * 主页显示数据
     */
    @PostMapping("/mainWorkLogList")
    @ResponseBody
    public AjaxResult mainWorkLogList() {
        PageHelper.startPage(0, 10);
        SysWorkLog sysWorkLog = new SysWorkLog();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        /*sysWorkLog.setCreateBy(ShiroUtils.getLoginName());
                        sysWorkLog.setShareUserName(ShiroUtils.getSysUser().getUserName());*/
                        sysWorkLog.setDeptId(ShiroUtils.getSysUser().getDeptId());
                    }
                }
            }
        }
        List<SysWorkLog> sysWorkLogList = sysWorkLogService.selectSysWorkLogList(sysWorkLog);
        for (SysWorkLog sysWorkLog1 : sysWorkLogList) {
            sysWorkLog1.setWorkLogTitle(sysWorkLog1.getCreator() + DateUtils.dateTime(sysWorkLog1.getCreateTime()) + "工作日志");
        }
        return AjaxResult.success(sysWorkLogList);
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.put("sysWorkLog", sysWorkLogService.selectSysWorkLogById(id));
        return prefix + "/detail";
    }

    @GetMapping("/toList")
    public String toList() {
        return prefix + "/workLog";
    }
}
