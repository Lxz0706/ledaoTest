package com.ledao.web.controller.system;

import java.util.*;

import com.ledao.common.core.dao.entity.SysDept;
import com.ledao.common.core.dao.entity.SysRole;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysConfigService;
import com.ledao.system.service.ISysDeptService;
import com.ledao.system.service.ISysUserService;
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
import com.ledao.system.service.ISysJournalService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

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
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"documentAdmin".equals(sysRole.getRoleKey())) {
                        if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                            sysUser.setFormalFlag("0");
                        }
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
        /*SysUser user = ShiroUtils.getSysUser();
        List<SysRole> getRoles = user.getRoles();
        boolean isZjl = false;
        for (SysRole sysRole : getRoles) {
            if ("zjl".equals(sysRole.getRoleKey()) || "admin".equals(user.getLoginName())) {
                isZjl = true;
                continue;
            }
        }
        if (!isZjl){
            SysDept dept = sysDeptService.selectDeptById(user.getDeptId());
            sysJournal.setDeptId(dept.getDeptId());
        }*/
        List<SysJournal> list = sysJournalService.selectSysJournalList(sysJournal);
        return getDataTable(list);
    }

    @PostMapping("/deptList")
    @ResponseBody
    public TableDataInfo deptList() {
        SysUser user = ShiroUtils.getSysUser();
        List<SysRole> getRoles = user.getRoles();
        boolean isZjl = false;
        StringBuffer sb = new StringBuffer();
        sb.append(user.getDeptId());
        String seeJournal = sysConfigService.selectConfigByKey("seeJournal");
        if (isFlag(user.getLoginName(), seeJournal)) {
            //允许查看所有日志的账号
            isZjl = true;
        }/* else {
            for (SysRole sysRole : getRoles) {
                if ("zjl".equals(sysRole.getRoleKey()) || "seniorRoles".equals(sysRole.getRoleKey())) {
                    //允许查看所有日志的角色
                    isZjl = true;
                    continue;
                }
//                if ("fkbjl".equals(sysRole.getRoleKey()) || "flgw".equals(sysRole.getRoleKey())) {
//                    sb.append(",").append("201,202");
//                }
            }
        }*/
        SysDept dept = new SysDept();
        Long parendId = 100L;
        dept.setParentId(parendId);
        List<SysDept> deps = deptService.selectDeptOneLevelList(dept);
        if (isZjl) {
            return getDataTable(deps);
        } else {
            List<SysDept> dts = new ArrayList<>();
            SysDept deptNew = deptService.selectDeptById(user.getDeptId());
            //for (SysDept deptNew : list) {
            for (SysDept d : deps) {
                if (d.getDeptId().toString().equals(deptNew.getDeptId().toString())) {
                    dts.add(d);
                    //return getDataTable(dts);
                }
                String[] depstra = deptNew.getAncestors().split(",");
                for (String ds : depstra) {
                    if (ds.equals(d.getDeptId().toString())) {
                        dts.add(d);
                        //return getDataTable(dts);
                    }
                }
            }
            //}
            return getDataTable(dts);
        }
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
        String[] users = sysConfigService.selectConfigByKey("lawUser").split(",");
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
        for (String days : DateUtils.findDaysStr(begintTime, endTime)) {
            Set<String> set = DateUtils.JJR(Integer.valueOf(DateUtils.parseDateToStr("yyyy", DateUtils.parseDate(days))), Integer.valueOf(DateUtils.parseDateToStr("MM", DateUtils.parseDate(days))));
            if (!set.contains(days)) {
                SysUser sysUser = new SysUser();
                sysUser.setSelectTime(days);
                List<SysUser> userList = sysUserService.selectCreatorForUser(sysUser);
                for (SysUser sysUser1 : userList) {
                    SysJournalCreator sysJournalCreator = new SysJournalCreator();
                    sysJournalCreator.setUserName(sysUser1.getUserName());
                    sysJournalCreator.setBeginTime(days);
                    sysJournalCreator.setDeptName(sysDeptService.selectDeptById(sysUser1.getDeptId()).getDeptName());
                    //if (list.size() > 0) {
                    //    for (SysJournalCreator sysJournalCreator1 : list) {
                     //       if (sysJournalCreator1.getUserName().equals(sysUser1.getUserName()) && !days.equals(sysJournalCreator1.getBeginTime())) {
                     //           sysJournalCreator.setBeginTime(sysJournalCreator.getBeginTime() + "," + days);
                    //        }
                    //    }
                   // } else {
                        list.add(sysJournalCreator);
                    //}

                }
            }
        }
        ExcelUtil<SysJournalCreator> util = new ExcelUtil<SysJournalCreator>(SysJournalCreator.class);
        return util.exportExcel(list, "未填写日志人员");
    }

}
