package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysPcustomerService;
import com.ledao.system.service.ISysZckService;
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
import com.ledao.system.service.ISysZcbService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 资产包Controller
 *
 * @author lxz
 * @date 2020-06-11
 */
@Controller
@RequestMapping("/system/zcb")
public class SysZcbController extends BaseController {
    private String prefix = "system/zcb";

    @Autowired
    private ISysZcbService sysZcbService;

    @Autowired
    private ISysZckService sysZckService;

    @Autowired
    private ISysPcustomerService sysPcustomerService;

    @RequiresPermissions("system:zcb:view")
    @GetMapping()
    public String zcb() {
        return prefix + "/zcb";
    }

    @RequiresPermissions("system:zcb:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        startPage();
        List<SysZcb> list = this.sysZcbService.selectZcbByAssetStatus();
        return this.getDataTable(list);
    }

    @RequiresPermissions("system:zcb:list")
    @GetMapping({"/selectZcbByAssetStatus/{assetStatus}"})
    public String selectZcbByAssetStatus(@PathVariable("assetStatus") String assetStatus, ModelMap modelMap) {
        modelMap.put("assetStatus", assetStatus);
        return "system/zcb/zcbList";
    }

    @RequiresPermissions("system:zcb:list")
    @PostMapping("/lists")
    @ResponseBody
    public TableDataInfo lists(SysZcb sysZcb) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        List<SysRole> getRoles = currentUser.getRoles();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"investmentManager".equals(sysRole.getRoleKey()) && !"tzbzz".equals(sysRole.getRoleKey())) {
                        sysZcb.setTeamMembersId(currentUser.getUserId().toString());
                    }
                }
            }
        }
        List<SysZcb> list = this.sysZcbService.selectSysZcbList(sysZcb);
        for (SysZcb sysZcb1 : list) {
            StringBuffer customerSb = new StringBuffer();
            if (currentUser != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser.isAdmin()) {
                    for (SysRole sysRole : getRoles) {
                        //投资部经理，大型单体经理，高层角色
                        if (!"investmentManager".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                                || !"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) &&
                                !"investmentManager2".equals(sysRole.getRoleKey()) && !"tzbzz".equals(sysRole.getRoleKey())) {
                            sysZcb1.setCollateralTotal(new BigDecimal(0));
                            sysZcb1.setCapValue(new BigDecimal(0));
                        }
                    }
                }
            }
            SysPcustomer sysPcustomer1 = new SysPcustomer();
            sysPcustomer1.setDeptType("tzb");
            sysPcustomer1.setProjectId(Long.valueOf(sysZcb1.getId()));
            if (!currentUser.isAdmin()) {
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"investmentManager".equals(sysRole.getRoleKey())) {
                        sysPcustomer1.setShareUserId(ShiroUtils.getUserId().toString());
                    }
                }
            }
            List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectPCustomerByProjectId(sysPcustomer1);
            for (SysPcustomer sysPcustomer : sysPcustomerList) {
                customerSb.append(sysPcustomer.getCustomerName()).append(",");
            }
            sysZcb1.setCustomer(StringUtils.strValue(customerSb.toString()));
        }
        return this.getDataTable(list);
    }

    /**
     * 导出资产包列表
     */
    @RequiresPermissions("system:zcb:export")
    @Log(title = "资产包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysZcb sysZcb) {
        List<SysZcb> list = sysZcbService.selectSysZcbList(sysZcb);
        ExcelUtil<SysZcb> util = new ExcelUtil<SysZcb>(SysZcb.class);
        return util.exportExcel(list, "资产包");
    }

    /**
     * 新增资产包
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存资产包
     */
    @RequiresPermissions("system:zcb:add")
    @Log(title = "资产包", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysZcb sysZcb) {
        sysZcb.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysZcbService.insertSysZcb(sysZcb));
    }

    /**
     * 修改资产包
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysZcb sysZcb = sysZcbService.selectSysZcbById(id);
        mmap.put("sysZcb", sysZcb);
        return prefix + "/edit";
    }

    /**
     * 修改保存资产包
     */
    @RequiresPermissions("system:zcb:edit")
    @Log(title = "资产包", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysZcb sysZcb) {
        sysZcb.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysZcbService.updateSysZcb(sysZcb));
    }

    /**
     * 删除资产包
     */
    @RequiresPermissions("system:zcb:remove")
    @Log(title = "资产包", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String id : ids.split(",")) {
            if (sysZckService.selectSysZckByZcbId(Long.valueOf(id)).size() > 0) {
                return AjaxResult.warn("存在子项目,不允许删除");
            }
        }
        return toAjax(sysZcbService.deleteSysZcbByIds(ids));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:zcb:detail")
    @Log(title = "资产包", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysZcb", sysZcbService.selectSysZcbById(id));
        return "system/zcb/zck/zck";
    }

    @RequiresPermissions("system:zcb:list")
    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysZck sysZck) {
        modelMap.put("sysZck", sysZck);
        return "system/zcb/queryAll";
    }

    @RequiresPermissions("system:zcb:list")
    @PostMapping("/treeList")
    @ResponseBody
    public TableDataInfo selectSysZcbList(SysZcb sysZcb) {
        startPage();
        List<SysZcb> sysZcbList = sysZcbService.selectSysZcbList(sysZcb);
        return getDataTable(sysZcbList);
    }

}
