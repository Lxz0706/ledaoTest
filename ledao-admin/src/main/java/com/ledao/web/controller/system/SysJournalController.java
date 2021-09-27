package com.ledao.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysDept;
import com.ledao.system.dao.SysRole;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysDeptService;
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
import com.ledao.system.dao.SysJournal;
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
public class SysJournalController extends BaseController
{
    private String prefix = "system/journal";

    @Autowired
    private ISysJournalService sysJournalService;
    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysDeptService deptService;

//    @RequiresPermissions("system:journal:view")
    @GetMapping()
    public String journal()
    {
        return prefix + "/deptList";
    }


    @GetMapping("/jouralListByLoginName")
    public String jouralListByLoginName(String loginName,Long parentId,ModelMap mmap)
    {
        mmap.put("loginName",loginName);
        mmap.put("parentId",parentId);
        return prefix + "/journal";
    }

    @GetMapping("/userListByDepId")
    public String userListByDepId(Long parentId,ModelMap mmap)
    {
        mmap.put("parentId",parentId);
        return prefix+ "/userListByDept";
    }

    @PostMapping("/userListByDepParentId")
    @ResponseBody
    public TableDataInfo userListByDepParentId(SysDept dept)
    {
        startPage();
        List<SysUser> deps = deptService.selectUserListByDepId(dept);
        return getDataTable(deps);
    }

    /**
     * 查询日志列表
     */
//    @RequiresPermissions("system:journal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJournal sysJournal)
    {
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
    public TableDataInfo deptList()
    {
        SysUser user = ShiroUtils.getSysUser();
        List<SysRole> getRoles = user.getRoles();
        boolean isZjl = false;
        if ("admin".equals(user.getLoginName())){
            isZjl=true;
        }else{
            for (SysRole sysRole : getRoles) {
                if ("zjl".equals(sysRole.getRoleKey())) {
                    isZjl = true;
                    continue;
                }
            }
        }
        SysDept dept = new SysDept();
        Long parendId = 100L;
        dept.setParentId(parendId);
        List<SysDept> deps = deptService.selectDeptOneLevelList(dept);
        if (isZjl){
            return getDataTable(deps);
        }else{
            List<SysDept> dts = new ArrayList<>();
            SysDept deptNew = deptService.selectDeptById(user.getDeptId());
            for (SysDept d:deps) {
                String[] depstra = deptNew.getAncestors().split(",");
                for (String ds:depstra) {
                    if(ds.equals(d.getDeptId().toString())){
                        dts.add(d);
                        return getDataTable(dts);
                    }
                }
            }
            return getDataTable(deps);
        }
    }


    /**
     * 导出日志列表
     */
//    @RequiresPermissions("system:journal:export")
    @Log(title = "日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJournal sysJournal)
    {
        List<SysJournal> list = sysJournalService.selectSysJournalList(sysJournal);
        ExcelUtil<SysJournal> util = new ExcelUtil<SysJournal>(SysJournal.class);
        return util.exportExcel(list, "journal");
    }

    /**
     * 新增日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存日志
     */
//    @RequiresPermissions("system:journal:add")
    @Log(title = "日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJournal sysJournal)
    {
        return toAjax(sysJournalService.insertSysJournal(sysJournal));
    }

    /**
     * 修改日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
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
    public AjaxResult editSave(SysJournal sysJournal)
    {
        return toAjax(sysJournalService.updateSysJournal(sysJournal));
    }

    /**
     * 删除日志
     */
//    @RequiresPermissions("system:journal:remove")
    @Log(title = "日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysJournalService.deleteSysJournalByIds(ids));
    }
}
