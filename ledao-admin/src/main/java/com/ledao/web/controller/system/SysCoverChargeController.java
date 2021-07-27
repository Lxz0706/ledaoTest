package com.ledao.web.controller.system;

import java.io.IOException;
import java.util.*;

import com.ledao.common.config.Global;
import com.ledao.common.utils.Base64Util;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysProjectmanagentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.service.ISysCoverChargeService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 流转服务费Controller
 *
 * @author lxz
 * @date 2020-12-18
 */
@Controller
@RequestMapping("/system/charge")
public class SysCoverChargeController extends BaseController {
    private String prefix = "system/charge";

    @Autowired
    private ISysCoverChargeService sysCoverChargeService;

    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @RequiresPermissions("system:charge:view")
    @GetMapping()
    public String charge() {
        return prefix + "/charge";
    }

    /**
     * 查询流转服务费列表
     */
    @RequiresPermissions("system:charge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCoverCharge sysCoverCharge) {
        startPage();
        List<SysCoverCharge> list = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge);
        for (SysCoverCharge sysCoverCharge1 : list) {
            if (StringUtils.isNotEmpty(sysCoverCharge1.getImgUrl())) {
                sysCoverCharge1.setImgFlag(true);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出流转服务费列表
     */
    @RequiresPermissions("system:charge:export")
    @Log(title = "流转服务费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCoverCharge sysCoverCharge) {
        List<SysCoverCharge> list = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge);
        ExcelUtil<SysCoverCharge> util = new ExcelUtil<SysCoverCharge>(SysCoverCharge.class);
        return util.exportExcel(list, "charge");
    }

    /**
     * 新增流转服务费
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
     * 新增保存流转服务费
     */
    @RequiresPermissions("system:charge:add")
    @Log(title = "流转服务费", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCoverCharge sysCoverCharge) {
        sysCoverCharge.setState("否");
        sysCoverCharge.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysCoverChargeService.insertSysCoverCharge(sysCoverCharge));
    }

    /**
     * 修改流转服务费
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(id);
        SysUser sysUser = ShiroUtils.getSysUser();
        if (!sysUser.isAdmin()) {
            List<SysRole> sysRoleList = sysUser.getRoles();
            for (SysRole sysRole : sysRoleList) {
                if ("financeManager".equals(sysRole.getRoleKey()) || "SJXXB".equals(sysRole.getRoleKey()) || "admin".equals(sysRole.getRoleKey())) {
                    sysCoverCharge.setCw("true");
                } else {
                    sysCoverCharge.setCw("false");
                }
            }
        } else {
            sysCoverCharge.setCw("true");
        }
        mmap.put("sysCoverCharge", sysCoverCharge);
        return prefix + "/edit";
    }

    /**
     * 修改保存流转服务费
     */
    @RequiresPermissions("system:charge:edit")
    @Log(title = "流转服务费", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCoverCharge sysCoverCharge) {
        SysCoverCharge sysCoverCharge1 = sysCoverChargeService.selectSysCoverChargeById(sysCoverCharge.getId());
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysCoverCharge1.getCreateBy())) {
                    if (!currentUser.getLoginName().equals(sysCoverCharge1.getCreateBy()) && !"wangziyuan".equals(currentUser.getLoginName())) {
                        return error("当前用户没有修改权限，请联系管理员或者创建人进行修改！");
                    }
                }
            }
        }
        sysCoverCharge.setUpdateBy(ShiroUtils.getLoginName());
        sysCoverCharge.setImgUrl(sysCoverCharge1.getImgUrl());
        return toAjax(sysCoverChargeService.updateSysCoverCharge(sysCoverCharge));
    }

    /**
     * 删除流转服务费
     */
    @RequiresPermissions("system:charge:remove")
    @Log(title = "流转服务费", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String string1 : ids.split(",")) {
            SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(string1));
            SysUser currentUser = ShiroUtils.getSysUser();
            if (currentUser != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser.isAdmin()) {
                    if (StringUtils.isNotEmpty(sysCoverCharge.getCreateBy())) {
                        if (!currentUser.getLoginName().equals(sysCoverCharge.getCreateBy())) {
                            return error("当前用户没有删除权限，请联系管理员或者创建人进行删除！");
                        }
                    }
                }
            }
        }
        return toAjax(sysCoverChargeService.deleteSysCoverChargeByIds(ids));
    }

    @RequiresPermissions("system:charge:list")
    @GetMapping("/chargeList/{projectManagementId}/{isCharge}")
    public String selectProjectProgressByProjectId(@PathVariable("projectManagementId") String projectManagementId, @PathVariable("isCharge") String isCharge, ModelMap modelMap) {
        String url = "system/charge/charge";
        modelMap.put("projectManagementId", projectManagementId);
        modelMap.put("isCharge", isCharge);
        modelMap.put("type", sysProjectmanagentService.selectSysProjectmanagentById(Long.valueOf(projectManagementId)).getProjectType());
        if (StringUtils.isNotEmpty(isCharge) && StringUtils.isNotNull(isCharge)) {
            if ("Y".equals(isCharge)) {
                url = "system/charge/charge1";
                modelMap.put("fundType", "已结算服务费");
            } else {
                modelMap.put("fundType", "待结算服务费");
            }
        }
        return url;
    }

    /**
     * 修改头像
     */
    @GetMapping("/imgUrl/{id}/{isCharge}")
    public String avatar(@PathVariable("id") String id, @PathVariable("isCharge") String isCharge, ModelMap mmap) {
        mmap.put("sysCoverCharge", sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(id)));
        return prefix + "/imgUrl";
    }

    /**
     * 修改头像
     */
    @GetMapping("/imgUrl1/{id}/{isCharge}")
    public String avatar1(@PathVariable("id") String id, @PathVariable("isCharge") String isCharge, ModelMap mmap) {
        mmap.put("sysCoverCharge", sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(id)));
        return prefix + "/imgUrl1";
    }

    @PostMapping("/updateImgUrl")
    @ResponseBody
    public AjaxResult uploadTest(@RequestParam("file") MultipartFile[] files, String id) throws IOException {
        String msg = "";
        //返回前端的json
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNull(files)) {
            msg = "附件为空";
            return new AjaxResult(AjaxResult.Type.ERROR, msg);
        }
        SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(id));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysCoverCharge.getCreateBy())) {
                    if (!currentUser.getLoginName().equals(sysCoverCharge.getCreateBy()) && !"wangziyuan".equals(currentUser.getLoginName())) {
                        msg = "当前用户没有修改权限，请联系管理员或者创建人进行修改！";
                        return new AjaxResult(AjaxResult.Type.ERROR, msg);
                    }
                }
            }
        }
        for (MultipartFile file : files) {
            boolean flag = fileNameForurl(Long.valueOf(id), file);
            if (flag) {
                msg = "上传成功";
            }
        }

        return new AjaxResult(AjaxResult.Type.SUCCESS, msg);
    }

    public boolean fileNameForurl(Long id, MultipartFile file) throws IOException {
        boolean flag = false;
        String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file, false);
        SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(id);
        if (StringUtils.isNotEmpty(sysCoverCharge.getImgUrl())) {
            if (!sysCoverCharge.getImgUrl().contains(file.getOriginalFilename())) {
                flag = true;
                sysCoverCharge.setImgUrl(avatar + ";" + sysCoverCharge.getImgUrl());
            }
        } else {
            flag = true;
            sysCoverCharge.setImgUrl(avatar);
        }
        sysCoverChargeService.updateSysCoverCharge(sysCoverCharge);
        return flag;
    }

    @PostMapping("/removeImg/{id}/{fileName}")
    @ResponseBody
    public AjaxResult removeImg(MultipartFile file, @PathVariable("id") String id, @PathVariable("fileName") String fileName) throws IOException {
        SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(id));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (StringUtils.isNotEmpty(sysCoverCharge.getCreateBy())) {
                    if (!currentUser.getLoginName().equals(sysCoverCharge.getCreateBy()) && !"wangziyuan".equals(currentUser.getLoginName())) {
                        return error("当前用户没有修改权限，请联系管理员或者创建人进行修改！");
                    }
                }
            }
        }
        //logger.info("删除文件的名称：=======" + file.getOriginalFilename());
        if (StringUtils.isNotNull(fileName)) {
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isNotEmpty(sysCoverCharge.getImgUrl())) {
                for (String string1 : sysCoverCharge.getImgUrl().split(";")) {
                    if (!fileName.equals(StringUtils.substringAfterLast(string1, "/"))) {
                        sb.append(string1).append(";");
                    }
                }
            }
            logger.info(sb.toString());
            //if (StringUtils.isNotEmpty(sb)) {
            sysCoverCharge.setImgUrl(sb.toString());
            sysCoverChargeService.updateSysCoverCharge(sysCoverCharge);
            //}
        }
        return success();
    }

    @PostMapping("/imgUrlList/{id}")
    @ResponseBody
    public AjaxResult imgUrlList(@PathVariable("id") String id) {
        SysCoverCharge sysCoverCharge = sysCoverChargeService.selectSysCoverChargeById(Long.valueOf(id));
        if (StringUtils.isNotNull(sysCoverCharge.getImgUrl())) {
            return AjaxResult.success(sysCoverCharge.getImgUrl().split(";"));
        } else {
            return AjaxResult.success();
        }
    }


    /**
     * 用户状态修改
     */
    @Log(title = "财务确认", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:charge:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysCoverCharge sysCoverCharge) {
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                if (!"wangziyuan".equals(ShiroUtils.getLoginName())) {
                    return error("没有权限不能修改！");
                }
            }
        }
        SysCoverCharge sysCoverCharge1 = new SysCoverCharge();
        SysCoverCharge sysCoverCharge2 = sysCoverChargeService.selectSysCoverChargeById(sysCoverCharge.getId());
        if (StringUtils.isNotEmpty(sysCoverCharge2.getFundType())) {
            if (StringUtils.isNotEmpty(sysCoverCharge.getState())) {
                if ("是".equals(sysCoverCharge.getState())) {
                    if ("待结算服务费".equals(sysCoverCharge2.getFundType())) {
                        sysCoverCharge1.setFundType("已结算服务费");
                        sysCoverCharge1.setProjectManagementId(sysCoverCharge2.getProjectManagementId());
                        sysCoverCharge1.setPaidDate(sysCoverCharge2.getPaidDate());
                        sysCoverCharge1.setAmountPaid(sysCoverCharge2.getAmountPaid());
                        sysCoverCharge1.setCreateBy(ShiroUtils.getLoginName());
                        sysCoverCharge1.setRemarks(sysCoverCharge2.getRemarks());
                        sysCoverCharge1.setImgUrl(sysCoverCharge2.getImgUrl());
                        sysCoverCharge1.setFinance(sysCoverCharge2.getFinance());
                        sysCoverCharge1.setState(sysCoverCharge.getState());
                        sysCoverChargeService.insertSysCoverCharge(sysCoverCharge1);
                        sysCoverChargeService.deleteSysCoverChargeById(sysCoverCharge.getId());
                    }
                } else if ("否".equals(sysCoverCharge.getState())) {
                    if ("待结算服务费".equals(sysCoverCharge2.getFundType())) {
                        sysCoverCharge1.setFundType("已结算服务费");
                    }
                    sysCoverCharge1.setProjectManagementId(sysCoverCharge2.getProjectManagementId());
                    sysCoverCharge1.setPaidDate(sysCoverCharge2.getPaidDate());
                    sysCoverCharge1.setAmountPaid(sysCoverCharge2.getAmountPaid());
                    sysCoverCharge1.setCreateBy(ShiroUtils.getLoginName());
                    sysCoverCharge1.setRemarks(sysCoverCharge2.getRemarks());
                    sysCoverCharge1.setImgUrl(sysCoverCharge2.getImgUrl());
                    List<SysCoverCharge> sysProjectysyfList = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge1);
                    for (SysCoverCharge sysProjectYsyf1 : sysProjectysyfList) {
                        sysCoverChargeService.deleteSysCoverChargeById(sysProjectYsyf1.getId());
                    }
                }
            }
        }
        sysCoverCharge.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysCoverChargeService.updateSysCoverCharge(sysCoverCharge));
    }


}
