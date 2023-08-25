package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.dao.entity.SysDept;
import com.ledao.common.core.dao.entity.SysRole;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysAsset;
import com.ledao.system.dao.SysHoliday;
import com.ledao.system.dao.SysJournal;
import com.ledao.system.dao.SysJournalCreator;
import com.ledao.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 日志Controller
 *
 * @author lxz
 * @date 2021-09-05
 */
@Controller
@RequestMapping("/system/journal")
public class SysJournalController extends BaseController {
    private String prefix = "system/journal";

    @Autowired
    private ISysJournalService sysJournalService;
    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private ISysHolidayService sysHolidayService;

    @Autowired
    private ISysAssetService sysAssetService;

    //    @RequiresPermissions("system:journal:view")
    @GetMapping()
    public String journal() {
        return prefix + "/deptList";
    }


    @GetMapping("/jouralListByLoginName")
    public String jouralListByLoginName(String loginName, Long parentId, ModelMap mmap) {
        mmap.put("loginName", loginName);
        mmap.put("parentId", parentId);
        return prefix + "/journal";
    }

    @GetMapping("/userListByDepId")
    public String userListByDepId(Long parentId, ModelMap mmap) {
        mmap.put("parentId", parentId);
        return prefix + "/userListByDept";
    }

    @PostMapping("/userListByDepParentId")
    @ResponseBody
    public TableDataInfo userListByDepParentId(SysDept dept) {

        //获取投后部经理2
        List<SysUser> sysUserList = getUserList("thbManager2");
        StringBuffer sb = new StringBuffer();
        for (SysUser sysUser : sysUserList) {
            if (StringUtils.isNotNull(sysUser) && StringUtils.isNotEmpty(sysUser.getLoginName())) {
                sb.append(sysUser.getLoginName()).append(",");
            }
        }

        SysUser sysUser = new SysUser();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                sysUser.setFormalFlag("0");
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"documentAdmin".equals(sysRole.getRoleKey())) {
                        if (!"thbManager".equals(sysRole.getRoleKey()) && !"thbManager2".equals(sysRole.getRoleKey())
                                && !"seniorRoles".equals(sysRole.getRoleKey()) && !"zjl".equals(sysRole.getRoleKey())
                                && !"documentAdmin".equals(sysRole.getRoleKey()) && !"Cc".equals(sysRole.getRoleKey())) {
                            if (StringUtils.isNotEmpty(sb.toString()) && sb.toString().length() > 0) {
                                sysUser.setLogName(sb.toString().substring(0, sb.toString().length() - 1));
                            }
                        }
                    }
                }
            }
        }
        startPage();
        List<SysUser> deps = deptService.selectUserListByDepId(dept, sysUser);
        return getDataTable(deps);
    }

    /**
     * 查询日志列表
     */
//    @RequiresPermissions("system:journal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJournal sysJournal) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                sysJournal.setFormalFlag("0");
            }
        }
        List<SysJournal> list = sysJournalService.selectSysJournalList(sysJournal);
        for (SysJournal sysJournal1 : list) {
            if (StringUtils.isEmpty(sysJournal1.getProjectName())) {
                sysJournal1.setProjectName("无项目");
            }
            if ("2".equals(sysJournal1.getProjectType())) {
                SysAsset sysAsset = sysAssetService.selectSysAssetById(Long.valueOf(sysJournal1.getProId()));
                sysJournal1.setProjectName(sysAsset.getAssetName());
            }
        }
        return getDataTable(list);
    }

    @PostMapping("/deptList")
    @ResponseBody
    public TableDataInfo deptList() {
        SysDept dept = new SysDept();
        dept.setParentId(Long.valueOf("100"));
        List<SysDept> deps = deptService.selectDeptOneLevelList(dept);
        SysJournal sysJournal = new SysJournal();
//        sysJournal.setCreateBy("qianguojun");
//        List<SysJournal> sysJournalList = sysJournalService.selectSysJournalList(sysJournal);
//        for (SysJournal sysJournal1 : sysJournalList) {
//            SysJournal sysJournal2 = new SysJournal();
//            sysJournal2.setProId(sysJournal1.getProId());
//            sysJournal2.setWorkDetail(sysJournal1.getWorkDetail());
//            sysJournal2.setCreateBy("law-" + sysJournal1.getCreateBy());
//            sysJournal2.setCreateTime(sysJournal1.getCreateTime());
//            sysJournalService.insertSysJournal(sysJournal2);
//        }
        return getDataTable(deps);
    }

    public boolean isFlag(String loginName, String loginNames) {
        boolean flag = false;
        for (String string : loginNames.split(",")) {
            if (loginName.equals(string)) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 导出日志列表
     */
//    @RequiresPermissions("system:journal:export")
    @Log(title = "日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJournal sysJournal) {
        List<SysJournal> list = sysJournalService.selectSysJournalList(sysJournal);
        ExcelUtil<SysJournal> util = new ExcelUtil<SysJournal>(SysJournal.class);
        return util.exportExcel(list, "journal");
    }

    /**
     * 新增日志
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存日志
     */
//    @RequiresPermissions("system:journal:add")
    @Log(title = "日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJournal sysJournal) {
        return toAjax(sysJournalService.insertSysJournal(sysJournal));
    }

    /**
     * 修改日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysJournal sysJournal = sysJournalService.selectSysJournalById(id);
        mmap.put("sysJournal", sysJournal);
        return prefix + "/edit";
    }

    /**
     * 修改保存日志
     */
//    @RequiresPermissions("system:journal:edit")
    @Log(title = "日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJournal sysJournal) {
        return toAjax(sysJournalService.updateSysJournal(sysJournal));
    }

    /**
     * 删除日志
     */
//    @RequiresPermissions("system:journal:remove")
    @Log(title = "日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysJournalService.deleteSysJournalByIds(ids));
    }

    public List<SysUser> getUserList(String roleKey) {
        StringBuffer sb = new StringBuffer();
        SysUser sysUser = new SysUser();
        sysUser.setRoleKey(roleKey);
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"zjl".equals(sysRole.getRoleKey()) && !"documentAdmin".equals(sysRole.getRoleKey())) {
                        if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                            sysUser.setFormalFlag("0");
                        }
                    }
                }
            }
        }
        List<SysUser> sysUserList = sysUserService.selectUserByRoleKey(sysUser);
        return sysUserList;
    }

    /**
     * 日志详情
     *
     * @param id
     * @param mmap
     * @return
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysJournal", sysJournalService.selectSysJournalById(id));
        return prefix + "/detail";
    }

    public List<String> getUserByKey() {
        String[] users = sysConfigService.selectConfigByKey("RZTB").split(",");
        List<String> list = new ArrayList<>();
        for (String stringUser : users) {
            SysUser sysUser = sysUserService.selectUserByLoginName(stringUser);
            list.add(sysUser.getLoginName());
        }
        return list;
    }

    @PostMapping("/selectJournalForCreate")
    @ResponseBody
    public AjaxResult selectJournalForCreate(SysJournal sysJournal) {
        String begintTime = (String) sysJournal.getParams().get("startTime");
        String endTime = (String) sysJournal.getParams().get("endTime");
        List<SysJournalCreator> list = new ArrayList<>();
        SysHoliday sysHoliday = new SysHoliday();
        List<SysHoliday> sysHolidayList = sysHolidayService.selectSysHolidayList(sysHoliday);
        Set<String> set = new HashSet<>();
        for (SysHoliday sysHoliday1 : sysHolidayList) {
            set.add(sysHoliday1.getHoliday());
        }
        for (String days : DateUtils.findDaysStr(begintTime, endTime)) {
            //Set<String> set = DateUtils.JJR(DateUtils.parseDateToStr("yyyy", DateUtils.parseDate(days)), DateUtils.parseDateToStr("MM", DateUtils.parseDate(days)));
            if (!set.contains(days)) {
                SysUser sysUser = new SysUser();
                sysUser.setSelectTime(days);
                List<SysUser> userList = sysUserService.selectCreatorForUser(sysUser);
                for (SysUser sysUser1 : userList) {
                    SysJournalCreator sysJournalCreator = new SysJournalCreator();
                    sysJournalCreator.setUserName(sysUser1.getUserName());
                    sysJournalCreator.setBeginTime(days);
                    sysJournalCreator.setDeptName(sysDeptService.selectDeptById(sysUser1.getDeptId()).getDeptName());
                    list.add(sysJournalCreator);
                }
            }
        }
        ExcelUtil<SysJournalCreator> util = new ExcelUtil<SysJournalCreator>(SysJournalCreator.class);
        return util.exportExcel(list, "未填写日志人员");
    }

    @GetMapping("/queryAll")
    public String queryAll(ModelMap modelMap, SysJournal sysJournal) {
        modelMap.put("sysJournal", sysJournal);
        return prefix + "/queryAll";
    }

}
