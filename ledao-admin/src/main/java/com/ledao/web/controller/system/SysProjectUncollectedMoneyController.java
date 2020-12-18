package com.ledao.web.controller.system;

import java.io.File;
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
import com.ledao.system.dao.SysProjectRecovered;
import com.ledao.system.dao.SysProjectysyf;
import com.ledao.system.dao.SysUser;
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
import com.ledao.system.dao.SysProjectUncollectedMoney;
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
                    if ("应付金额".equals(sysProjectUncollectedMoney1.getFundType()) || "未付金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已付金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyf.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectysyf.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                        //sysProjectUncollectedMoneyService.deleteSysProjectUncollectedMoneyById(sysProjectUncollectedMoney.getId());
                    } else if ("应（未）收金额".equals(sysProjectUncollectedMoney1.getFundType()) || "未收金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已收金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyf.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectysyf.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                        //sysProjectUncollectedMoneyService.deleteSysProjectUncollectedMoneyById(sysProjectUncollectedMoney.getId());
                    } else if ("未收服务费金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已收服务费金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyf.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectysyf.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                        //sysProjectUncollectedMoneyService.deleteSysProjectUncollectedMoneyById(sysProjectUncollectedMoney.getId());
                    } else if ("已回收金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectRecovered.setProjectManagementId(sysProjectUncollectedMoney1.getProjectManagementId());
                        sysProjectRecovered.setAmountRecovered(sysProjectUncollectedMoney1.getAmountMoney());
                        sysProjectRecovered.setRecoveredDate(sysProjectUncollectedMoney1.getTime());
                        sysProjectRecovered.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectRecovered.setRemarks(sysProjectUncollectedMoney1.getRemarks());
                        sysProjectRecovered.setImgUrl(sysProjectUncollectedMoney1.getImgUrl());
                        sysProjectRecoveredService.insertSysProjectRecovered(sysProjectRecovered);
                    }
                } else if ("否".equals(sysProjectUncollectedMoney.getState())) {
                    if ("应付金额".equals(sysProjectUncollectedMoney1.getFundType()) || "未付金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已付金额");
                    } else if ("应收金额".equals(sysProjectUncollectedMoney1.getFundType()) || "未收金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已收金额");
                    } else if ("未收服务费金额".equals(sysProjectUncollectedMoney1.getFundType())) {
                        sysProjectysyf.setFundType("已收服务费金额");
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
        sysProjectUncollectedMoney.setUpdateBy(ShiroUtils.getLoginName());
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
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateImgUrl/{id}")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("imgUrl") MultipartFile file, @PathVariable("id") String id) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(Long.valueOf(id));
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
                sysProjectUncollectedMoney.setImgUrl(avatar);
                sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney);
                return success();
            }
            return error();
        } catch (Exception e) {
            logger.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
