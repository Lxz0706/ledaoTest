package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.SysDepartment;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysDepartmentService;
import com.ledao.system.service.ISysDictDataService;
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
import com.ledao.system.dao.SysStaff;
import com.ledao.system.service.ISysStaffService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 员工信息Controller
 *
 * @author lxz
 * @date 2021-06-23
 */
@Controller
@RequestMapping("/system/staff")
public class SysStaffController extends BaseController {
    private String prefix = "system/staff";

    @Autowired
    private ISysStaffService sysStaffService;

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @RequiresPermissions("system:staff:view")
    @GetMapping()
    public String staff() {
        return prefix + "/staff";
    }

    /**
     * 查询员工信息列表
     */
    @RequiresPermissions("system:staff:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysStaff sysStaff) {
        startPage();
        List<SysStaff> list = sysStaffService.selectSysStaffList(sysStaff);
        return getDataTable(list);
    }

    /**
     * 导出员工信息列表
     */
    @RequiresPermissions("system:staff:export")
    @Log(title = "员工信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysStaff sysStaff) {
        List<SysStaff> list = sysStaffService.selectSysStaffList(sysStaff);
        ExcelUtil<SysStaff> util = new ExcelUtil<SysStaff>(SysStaff.class);
        return util.exportExcel(list, "staff");
    }

    /**
     * 新增员工信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存员工信息
     */
    @RequiresPermissions("system:staff:add")
    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysStaff sysStaff) {
        sysStaff.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysStaffService.insertSysStaff(sysStaff));
    }

    /**
     * 修改员工信息
     */
    @GetMapping("/edit/{staffId}")
    public String edit(@PathVariable("staffId") Long staffId, ModelMap mmap) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(staffId);
        mmap.put("sysStaff", sysStaff);
        return prefix + "/edit";
    }

    /**
     * 修改保存员工信息
     */
    @RequiresPermissions("system:staff:edit")
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysStaff sysStaff) {
        if ("1".equals(sysStaff.getStatus())) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentName("离职");
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
            if (sysDepartmentList.size() > 0) {
                sysStaff.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
                sysStaff.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
            }
        }
        sysStaff.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysStaffService.updateSysStaff(sysStaff));
    }

    /**
     * 删除员工信息
     */
    @RequiresPermissions("system:staff:remove")
    @Log(title = "员工信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysStaffService.deleteSysStaffByIds(ids));
    }

    /**
     * 用户状态修改
     */
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:staff:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysStaff sysStaff) {
        if ("1".equals(sysStaff.getStatus())) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentName("离职");
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
            if (sysDepartmentList.size() > 0) {
                sysStaff.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
                sysStaff.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
            }
        }
        return toAjax(sysStaffService.changeStatus(sysStaff));
    }

    /**
     * 选择员工
     *
     * @param staffId
     * @param staffName
     * @param mmap
     * @return
     */
    @GetMapping("/selectTree")
    public String selectTree(String staffId, String staffName, ModelMap mmap) {
        mmap.put("staffId", staffId);
        mmap.put("staffName", staffName);
        return prefix + "/tree";
    }

    /**
     * 根据学历分组
     *
     * @param sysStaff
     * @return
     */
    @PostMapping("/selectStaffByEducation")
    @ResponseBody
    public String selectStaffByEducation(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectStaffByEducation(sysStaff);
        for (SysStaff sysStaff1 : sysStaffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysStaff1.getEducation());
            jsonObject.put("value", sysStaff1.getEducationCount());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @PostMapping("/selectStaffBySex")
    @ResponseBody
    public String selectStaffBySex(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectStaffBySex(sysStaff);
        int total = 0;
        for (SysStaff sysStaff1 : sysStaffList) {
            total = total + new Long(sysStaff1.getSexCount()).intValue();
        }
        for (SysStaff sysStaff1 : sysStaffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sex", sysStaff1.getSex());
            jsonObject.put("count", sysStaff1.getSexCount());
            jsonObject.put("sold", new BigDecimal(sysStaff1.getSexCount()).divide(new BigDecimal(total), 1, BigDecimal.ROUND_HALF_UP));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @PostMapping("/selectStaffBySecretaryLing")
    @ResponseBody
    public String selectStaffBySecretaryLing(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectStaffBySecretaryLing(sysStaff);
        for (SysStaff sysStaff1 : sysStaffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysStaff1.getSecretaryLing() + "年");
            jsonObject.put("value", sysStaff1.getSecretaryLingCount());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    /**
     * 查看员工详情
     *
     * @param staffId
     * @param modelMap
     * @return
     */
    @GetMapping("/detail/{staffId}")
    public String detail(@PathVariable("staffId") Long staffId, ModelMap modelMap) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(staffId);
        if ("0".equals(sysStaff.getStatus())) {
            sysStaff.setStatus("正常");
        } else {
            sysStaff.setStatus("停用");
        }
        modelMap.put("sysStaff", sysStaff);
        return prefix + "/detail";
    }

    @PostMapping("/selectDepartment")
    @ResponseBody
    public AjaxResult selectDepartment() {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setDepartmentName("离职");
        List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
        SysDepartment sysDepartment1 = new SysDepartment();
        if (sysDepartmentList.size() > 0) {
            sysDepartment1.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
            sysDepartment1.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
        }
        return AjaxResult.success(sysDepartment1);
    }
}
