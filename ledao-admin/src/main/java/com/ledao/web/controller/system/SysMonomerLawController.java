package com.ledao.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
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
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import oshi.util.StringUtil;

/**
 * 项目法律信息Controller
 *
 * @author lxz
 * @date 2021-08-23
 */
@Controller
@RequestMapping("/system/monomerLaw")
public class SysMonomerLawController extends BaseController {
    private String prefix = "system/monomerLaw";

    @Autowired
    private ISysMonomerLawService sysMonomerLawService;

    @Autowired
    private ISysZckService sysZckService;

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysApplyWorkflowService iSysApplyWorkflowService;

    @GetMapping("/toMonomerLaw")
    public String toMonomerLaw(Long projectId, String projectType, String projectStatus, String fwProjectType, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        modelMap.put("projectStatus", projectStatus);
        modelMap.put("fwProjectType", fwProjectType);
        return prefix + "/monomerLaw";
    }

    //@RequiresPermissions("system:monomerLaw:view")
    @GetMapping()
    public String monomerLaw() {
        return prefix + "/monomerLaw";
    }

    /**
     * 查询项目法律信息列表
     */
    //@RequiresPermissions("system:monomerLaw:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMonomerLaw sysMonomerLaw) {
        startPage();
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        for (SysMonomerLaw sysMonomerLaw1 : list) {
            String projectName = "";
            if (StringUtils.isNotNull(sysMonomerLaw1.getProjectId()) && StringUtils.isNotNull(sysMonomerLaw1.getProjectType())) {
                if ("1".equals(sysMonomerLaw1.getProjectType())) {
                    SysZck sysZck = sysZckService.selectSysZckById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysZck) && StringUtils.isNotEmpty(sysZck.getProjectName())) {
                        projectName = sysZck.getProjectName();
                    }
                } else if ("2".equals(sysMonomerLaw1.getProjectType())) {
                    SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysBgczzck) && StringUtils.isNotEmpty(sysBgczzck.getProjectName())) {
                        projectName = sysBgczzck.getProjectName();
                    }
                } else if ("3".equals(sysMonomerLaw1.getProjectType())) {
                    SysProject sysProject = sysProjectService.selectSysProjectById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysProject) && StringUtils.isNotEmpty(sysProject.getProjectName())) {
                        projectName = sysProject.getProjectName();
                    }
                }
            }
            sysMonomerLaw1.setProjectName(projectName);
        }
        return getDataTable(list);
    }

    /**
     * 导出项目法律信息列表
     */
    //@RequiresPermissions("system:monomerLaw:export")
    @Log(title = "项目法律信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMonomerLaw sysMonomerLaw) {
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        ExcelUtil<SysMonomerLaw> util = new ExcelUtil<SysMonomerLaw>(SysMonomerLaw.class);
        return util.exportExcel(list, "monomerLaw");
    }

    /**
     * 新增项目法律信息
     */
    @GetMapping("/add")
    public String add(Long projectId, String projectType, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        return prefix + "/add";
    }

    /**
     * 新增保存项目法律信息
     */
    //@RequiresPermissions("system:monomerLaw:add")
    @Log(title = "项目法律信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysMonomerLaw sysMonomerLaw) {
        sysMonomerLaw.setCreateBy(ShiroUtils.getLoginName());
        sysMonomerLaw.setCreator(ShiroUtils.getSysUser().getUserName());
        SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw.getProjectId());
        if (StringUtils.isNotEmpty(sysMonomerLaw.getJudicialStatus())) {
            StringBuffer idSb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            //获取风控部经理
            List<SysUser> sysUserList = getUserList("fkbjl");
            for (SysUser sysUser1 : sysUserList) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }

            //获取风控部普通员工
            List<SysUser> fkbptList = getUserList("fkbCommon");
            for (SysUser sysUser1 : fkbptList) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }

            //获取并购重组经理
            List<SysUser> sysUserList1 = getUserList("bgczManager");
            for (SysUser sysUser1 : sysUserList1) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }
            SysNotice sysNotice = new SysNotice();
            sysNotice.setNoticeTitle(sysBgczzck.getProjectName() + "司法状态更改为" + sysMonomerLaw.getJudicialStatus());
            sysNotice.setStatus("0");
            sysNotice.setNoticeType("3");
            sysNotice.setReceiverId(idSb.toString());
            sysNotice.setReceiver(nameSb.toString());
            sysNotice.setCreateBy(ShiroUtils.getLoginName());
            sysNotice.setShareDeptAndUser(nameSb.toString());
            sysNoticeService.insertNotice(sysNotice);

            //小程序消息推送
            try {
                List<SysUser> us = new ArrayList<>();
                us.addAll(sysUserList);
                us.addAll(fkbptList);
                us.addAll(sysUserList1);
                Map<String, String> parmStr = new HashMap<>();
                parmStr.put("first", "您有一个法务工作提醒");
                parmStr.put("word1", sysNotice.getNoticeTitle());
                iSysApplyWorkflowService.sendTaskMsg(us, parmStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return toAjax(sysMonomerLawService.insertSysMonomerLaw(sysMonomerLaw));
    }

    /**
     * 修改项目法律信息
     */
    @GetMapping("/edit/{monomerLawId}")
    public String edit(@PathVariable("monomerLawId") Long monomerLawId, ModelMap mmap) {
        SysMonomerLaw sysMonomerLaw = sysMonomerLawService.selectSysMonomerLawById(monomerLawId);
        mmap.put("sysMonomerLaw", sysMonomerLaw);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目法律信息
     */
    //@RequiresPermissions("system:monomerLaw:edit")
    @Log(title = "项目法律信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysMonomerLaw sysMonomerLaw) {
        sysMonomerLaw.setUpdateBy(ShiroUtils.getLoginName());
        sysMonomerLaw.setReviser(ShiroUtils.getSysUser().getUserName());
        SysMonomerLaw sysMonomerLaw1 = sysMonomerLawService.selectSysMonomerLawById(sysMonomerLaw.getMonomerLawId());
        SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw1.getProjectId());
        //司法状态提醒
        if (!sysMonomerLaw.getJudicialStatus().equals(sysMonomerLaw1.getJudicialStatus())) {
            StringBuffer idSb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            //获取风控部经理
            List<SysUser> sysUserList = getUserList("fkbjl");
            for (SysUser sysUser1 : sysUserList) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }

            //获取风控部普通员工
            List<SysUser> fkbptList = getUserList("fkbCommon");
            for (SysUser sysUser1 : fkbptList) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }

            //获取并购重组经理
            List<SysUser> sysUserList1 = getUserList("bgczManager");
            for (SysUser sysUser1 : sysUserList1) {
                idSb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }
            SysNotice sysNotice = new SysNotice();
            sysNotice.setNoticeTitle(sysBgczzck.getProjectName() + "司法状态更改为" + sysMonomerLaw.getJudicialStatus());
            sysNotice.setStatus("0");
            sysNotice.setNoticeType("3");
            sysNotice.setReceiverId(idSb.toString());
            sysNotice.setReceiver(nameSb.toString());
            sysNotice.setCreateBy(ShiroUtils.getLoginName());
            sysNotice.setShareDeptAndUser(nameSb.toString());
            sysNoticeService.insertNotice(sysNotice);

            try {
                List<SysUser> us = new ArrayList<>();
                us.addAll(sysUserList);
                us.addAll(fkbptList);
                us.addAll(sysUserList1);
                Map<String, String> parmStr = new HashMap<>();
                parmStr.put("first", "您有一个法务工作提醒");
                parmStr.put("word1", sysNotice.getNoticeTitle());
                iSysApplyWorkflowService.sendTaskMsg(us, parmStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return toAjax(sysMonomerLawService.updateSysMonomerLaw(sysMonomerLaw));
    }

    public List<SysUser> getUserList(String roleKey) {
        SysUser sysUser = new SysUser();
        sysUser.setRoleKey(roleKey);
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        sysUser.setFormalFlag("0");
                    }
                }
            }
        }
        List<SysUser> sysUserList = sysUserService.selectUserByRoleKey(sysUser);
        return sysUserList;
    }

    /**
     * 删除项目法律信息
     */
    //@RequiresPermissions("system:monomerLaw:remove")
    @Log(title = "项目法律信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysMonomerLawService.deleteSysMonomerLawByIds(ids));
    }

    @GetMapping("/detail")
    public String detail(Long monomerLawId, Long detailType, String fwProjectType, ModelMap modelMap) {
        String url = "";
        SysMonomerLaw sysMonomerLaw = sysMonomerLawService.selectSysMonomerLawById(monomerLawId);
        String projectName = "";
        if (StringUtils.isNotNull(sysMonomerLaw.getProjectId()) && StringUtils.isNotNull(sysMonomerLaw.getProjectType())) {
            if ("1".equals(sysMonomerLaw.getProjectType())) {
                SysZck sysZck = sysZckService.selectSysZckById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysZck) && StringUtils.isNotEmpty(sysZck.getProjectName())) {
                    projectName = sysZck.getProjectName();
                }
            } else if ("2".equals(sysMonomerLaw.getProjectType())) {
                SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysBgczzck)) {
                    if (StringUtils.isNotEmpty(sysBgczzck.getProjectName())) {
                        projectName = sysBgczzck.getProjectName();
                    }
                    if (StringUtils.isNotEmpty(sysBgczzck.getProjectStatus())) {
                        sysMonomerLaw.setProjectStatus(sysBgczzck.getProjectStatus());
                    }
                }
            } else if ("3".equals(sysMonomerLaw.getProjectType())) {
                SysProject sysProject = sysProjectService.selectSysProjectById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysProject) && StringUtils.isNotEmpty(sysProject.getProjectName())) {
                    projectName = sysProject.getProjectName();
                }
            }
        }
        sysMonomerLaw.setProjectName(projectName);
        modelMap.put("sysMonomerLaw", sysMonomerLaw);
        modelMap.put("fwProjectType", fwProjectType);
        if (1 == detailType) {
            url = "/detail1";
        } else if (2 == detailType) {
            url = "/detail2";
        } else {
            url = "/detail";
        }
        return prefix + url;
    }
}
