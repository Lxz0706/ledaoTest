package com.ledao.web.controller.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.ledao.common.config.Global;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysProjectRecoveredService;
import com.ledao.system.service.ISysProjectysyfService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.atp.AnalysisToolPak;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.service.ISysProjectUncollectedMoneyService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 【请填写功能名称】Controller
 *
 * @author ledao
 * @date 2020-08-31
 */
@Controller
@RequestMapping("/system/money")
public class SysProjectUncollectedMoneyController extends BaseController {
    private String prefix = "system/money";

    @Autowired
    private ISysProjectUncollectedMoneyService sysProjectUncollectedMoneyService;

    @Autowired
    private ISysProjectysyfService sysProjectysyfService;

    @Autowired
    private ISysProjectRecoveredService sysProjectRecoveredService;

    @RequiresPermissions("system:money:view")
    @GetMapping()
    public String money() {
        return prefix + "/money";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:money:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        startPage();
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:money:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        ExcelUtil<SysProjectUncollectedMoney> util = new ExcelUtil<SysProjectUncollectedMoney>(SysProjectUncollectedMoney.class);
        return util.exportExcel(list, "money");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("projectManagementId", id);
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            List<SysRole> sysRoleList = sysUser.getRoles();
            for (SysRole sysRole : sysRoleList) {
                if ("financeManager".equals(sysRole.getRoleKey()) || "SJXXB".equals(sysRole.getRoleKey()) || "admin".equals(sysRole.getRoleKey())) {
                    modelMap.put("cw", "true");
                } else {
                    modelMap.put("cw", "false");
                }
            }
        } else {
            modelMap.put("cw", "true");
        }
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:money:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectUncollectedMoney sysProjectUncollectedMoney, MultipartFile file) {
        sysProjectUncollectedMoney.setState("否");
        sysProjectUncollectedMoney.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectUncollectedMoneyService.insertSysProjectUncollectedMoney(sysProjectUncollectedMoney));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(id);
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            List<SysRole> sysRoleList = sysUser.getRoles();
            for (SysRole sysRole : sysRoleList) {
                if ("financeManager".equals(sysRole.getRoleKey()) || "SJXXB".equals(sysRole.getRoleKey()) || "admin".equals(sysRole.getRoleKey())) {
                    sysProjectUncollectedMoney.setCw("true");
                } else {
                    sysProjectUncollectedMoney.setCw("false");
                }
            }
        } else {
            sysProjectUncollectedMoney.setCw("true");
        }
        mmap.put("sysProjectUncollectedMoney", sysProjectUncollectedMoney);
        return prefix + "/edit";
    }

    /**
     * 用户状态修改
     */
    @Log(title = "财务确认", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:money:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (!ShiroUtils.getLoginName().equals("wangziyuan")) {
                    return error("没有权限不能修改！");
                }
            }
        }

        SysProjectUncollectedMoney sysProjectUncollectedMoney1 = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(sysProjectUncollectedMoney.getId());
        SysProjectysyf sysProjectysyf = new SysProjectysyf();
        SysProjectRecovered sysProjectRecovered = new SysProjectRecovered();
        if (StringUtils.isNotEmpty(sysProjectUncollectedMoney1.getFundType())) {
            if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getState())) {
                if ("是".equals(sysProjectUncollectedMoney.getState())) {
                    if ("应付金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已付金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyf.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectysyf.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectysyf.setFinance(sysProjectUncollectedMoney1.getFinance());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                    } else if ("应收金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectRecovered.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectRecovered.setAmountRecovered(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectRecovered.setRecoveredDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectRecovered.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectRecovered.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectRecovered.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectRecovered.setFinance(sysProjectUncollectedMoney1.getFinance());
                        sysProjectRecoveredService.insertSysProjectRecovered(sysProjectRecovered);
                    }
                } else if ("否".equals(sysProjectUncollectedMoney.getState())) {
                    if ("应付金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已付金额");
                    }
                    sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                    sysProjectysyf.setPaidDate(sysProjectUncollectedMoney1.getTime());
                    sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney1.getAmountMoney());
                    sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                    sysProjectysyf.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                    sysProjectysyf.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                    List<SysProjectysyf> sysProjectysyfList = sysProjectysyfService.selectSysProjectysyfList(sysProjectysyf);
                    for (SysProjectysyf sysProjectYsyf1 : sysProjectysyfList) {
                        sysProjectysyfService.deleteSysProjectysyfById(sysProjectYsyf1.getId());
                    }

                    sysProjectRecovered.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                    sysProjectRecovered.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                    sysProjectRecovered.setAmountRecovered(sysProjectUncollectedMoney1.getAmountMoney());
                    sysProjectRecovered.setCreateBy(ShiroUtils.getLoginName());
                    sysProjectRecovered.setRecoveredDate(sysProjectUncollectedMoney1.getTime());
                    sysProjectRecovered.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                    List<SysProjectRecovered> sysProjectRecoveredList = sysProjectRecoveredService.selectSysProjectRecoveredList(sysProjectRecovered);
                    for (SysProjectRecovered sysProjectRecovered1 : sysProjectRecoveredList) {
                        sysProjectRecoveredService.deleteSysProjectRecoveredById(sysProjectRecovered1.getId());
                    }
                }
            }
        }
        sysProjectUncollectedMoney.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney));
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:money:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney1 = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(sysProjectUncollectedMoney.getId());
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysProjectUncollectedMoney1.getCreateBy())) {
                    if (!ShiroUtils.getLoginName().equals(sysProjectUncollectedMoney1.getCreateBy()) && !currentUser.getLoginName().equals("wangziyuan")) {
                        return error("当前用户没有修改权限，请联系管理员或者创建人进行修改！");
                    }
                }
            }
        }
        sysProjectUncollectedMoney.setUpdateBy(ShiroUtils.getLoginName());
        sysProjectUncollectedMoney.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
        return toAjax(sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:money:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String string1 : ids.split(",")) {
            SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(string1));
            SysUser currentUser = ShiroUtils.getSysUser();
            if (currentUser != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser.isAdmin()) {
                    if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getCreateBy())) {
                        if (!currentUser.getLoginName().equals(sysProjectUncollectedMoney.getCreateBy())) {
                            return error("当前用户没有删除权限，请联系管理员或者创建人进行删除！");
                        }
                    }
                }
            }
            if ("是".equals(sysProjectUncollectedMoney.getState())) {
                return error("该金额已经收到不能删除！");
            }
        }
        return toAjax(sysProjectUncollectedMoneyService.deleteSysProjectUncollectedMoneyByIds(ids));
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/moneyList/{projectManagementId}")
    public String selectProjectProgressByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        return "system/money/money";
    }

    /**
     * 修改头像
     */
    @GetMapping("/imgUrl/{id}")
    public String avatar(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("sysProjectUncollectedMoney", sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id)));
        return prefix + "/imgUrl";
    }

    /**
     * 修改头像
     */
    @GetMapping("/imgUrl1/{id}")
    public String avatar1(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("sysProjectUncollectedMoney", sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id)));
        return prefix + "/imgUrl1";
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateImgUrl/{id}")
    @ResponseBody
    public AjaxResult updateAvatar(MultipartFile file, @PathVariable("id") String id) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getCreateBy())) {
                    if (!currentUser.getLoginName().equals(sysProjectUncollectedMoney.getCreateBy()) && !currentUser.getLoginName().equals("wangziyuan")) {
                        return error("当前用户没有修改权限，请联系管理员或者创建人进行修改！");
                    }
                }
            }
        }
        try {
            if (StringUtils.isNotNull(file)) {
                String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file, false);
                if (StringUtils.isNotNull(sysProjectUncollectedMoney.getImgUrl())) {
                    if (!sysProjectUncollectedMoney.getImgUrl().contains(file.getOriginalFilename())) {
                        sysProjectUncollectedMoney.setImgUrl(avatar + ";" + sysProjectUncollectedMoney.getImgUrl());
                    } else {
                        return error(file.getOriginalFilename() + "上传失败");
                    }
                } else {
                    sysProjectUncollectedMoney.setImgUrl(avatar);
                }
                sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney);
                return success();
            }
            return error(file.getOriginalFilename() + "上传失败");
        } catch (Exception e) {
            logger.error("上传失败！", e);
            return error(e.getMessage());
        }
    }

    @PostMapping("/removeImg/{id}/{fileName}")
    @ResponseBody
    public AjaxResult removeImg(MultipartFile file, @PathVariable("id") String id, @PathVariable("fileName") String fileName) throws IOException {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getCreateBy())) {
                    if (!currentUser.getLoginName().equals(sysProjectUncollectedMoney.getCreateBy()) && !currentUser.getLoginName().equals("wangziyuan")) {
                        return error("当前用户没有修改权限，请联系管理员或者创建人进行修改！");
                    }
                }
            }
        }
        if (StringUtils.isNotNull(fileName)) {
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getImgUrl())) {
                for (String string1 : sysProjectUncollectedMoney.getImgUrl().split(";")) {
                    if (!fileName.equals(StringUtils.substringAfterLast(string1, "/"))) {
                        sb.append(string1).append(";");
                    }
                }
            }
            sysProjectUncollectedMoney.setImgUrl(sb.toString());
            sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney);
        }
        return success();
    }

    @PostMapping("/imgUrlList/{id}")
    @ResponseBody
    public AjaxResult imgUrlList(@PathVariable("id") String id) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id));
        if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getImgUrl())) {
            return AjaxResult.success(sysProjectUncollectedMoney.getImgUrl().split(";"));
        } else {
            return AjaxResult.success();
        }
    }
}
