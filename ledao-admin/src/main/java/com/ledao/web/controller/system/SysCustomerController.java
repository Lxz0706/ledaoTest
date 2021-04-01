package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.constant.UserConstants;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户库Controller
 *
 * @author lxz
 * @date 2020-11-18
 */
@Controller
@RequestMapping("/system/customer")
public class SysCustomerController extends BaseController {
    private String prefix = "system/customer";

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysItemService sysItemService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysPcustomerService sysPcustomerService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequiresPermissions("system:customer:view")
    @GetMapping()
    public String customer() {
        return prefix + "/customer";
    }

    /**
     * 查询客户库列表
     */
    @RequiresPermissions("system:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCustomer sysCustomer) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())
                                || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            String ids = "201,207,208,209";
                            sysCustomer.setDeptIds(ids.split(","));
                        } else {
                            sysCustomer.setShareUserId(ShiroUtils.getUserId().toString());
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        }
                    }
                }
            }
        }
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        for (SysCustomer sysCustomer1 : list) {
            StringBuffer sb = new StringBuffer();
            SysItem sysItem = new SysItem();
            SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                            if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                    && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                                //  sysItem.setCreateBy(ShiroUtils.getLoginName());
                                sysCustomer1.setIsAdmin("N");
                            } else {
                                sysCustomer1.setIsAdmin("N");
                            }
                        } else {
                            sysCustomer1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysCustomer1.setIsAdmin("Y");
                }
            }
            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            System.out.print("项目数量：======="+sysItemList.size());
            for (SysItem sysItem1 : sysItemList) {
                if (StringUtils.isNotNull(sysItem1.getProjectName())) {
                    sb.append(sysItem1.getProjectName()).append(";");
                }
            }
            sysCustomer1.setProjectName(sb.toString());
        }
        return getDataTable(list);
    }

    /**
     * 导出客户库列表
     */
    @RequiresPermissions("system:customer:export")
    @Log(title = "客户库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCustomer sysCustomer) {
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        ExcelUtil<SysCustomer> util = new ExcelUtil<SysCustomer>(SysCustomer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * z
     * 新增客户库
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        modelMap.put("isAdmin", isAdmin);
                    } else {
                        modelMap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                modelMap.put("isAdmin", isAdmin);
            }
        }
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        modelMap.put("types", sysDictDataService.selectDictDataList(sysDictData));
        return prefix + "/add";
    }

    /**
     * 新增保存客户库
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "客户库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCustomer sysCustomer) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey())) {
                        sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                        SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                        sysCustomer.setDeptId(sysUser.getDeptId());
                        sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                    } else {
                        sysCustomer.setAgentId(ShiroUtils.getLoginName());
                        sysCustomer.setAgent(ShiroUtils.getSysUser().getUserName());
                        if (StringUtils.isNotEmpty(sysCustomer.getCreateBy())) {
                            SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                            sysCustomer.setDeptId(sysUser.getDeptId());
                            sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                        } else {
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                            sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                            sysCustomer.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
                        }
                    }
                }
            } else {
                sysCustomer.setAgentId(ShiroUtils.getLoginName());
                sysCustomer.setAgent(ShiroUtils.getSysUser().getUserName());
                if (StringUtils.isNotEmpty(sysCustomer.getCreateBy())) {
                    SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                    if (StringUtils.isNotNull(sysUser)) {
                        sysCustomer.setDeptId(sysUser.getDeptId());
                        sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                    }
                } else {
                    sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                    sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                }
            }
        }
        SysCustomer sysCustomer1 = new SysCustomer();
        sysCustomer1.setCreator(sysCustomer.getCreator());
        sysCustomer1.setContactNumber(sysCustomer.getContactNumber());
        sysCustomer1.setWeChatNumber(sysCustomer.getWeChatNumber());
        if (StringUtils.isNull(sysCustomer.getDeptId()) && StringUtils.isEmpty(sysCustomer.getDeptName())) {
            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
            sysCustomer.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
        }
        sysCustomer1.setDeptId(ShiroUtils.getSysUser().getDeptId());
        sysCustomer1.setCreateBy(sysCustomer.getCreateBy());
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerList(sysCustomer1);
        if (sysCustomerList.size() > 0) {
            return error("该用户已存在！");
        }
        return toAjax(sysCustomerService.insertSysCustomer(sysCustomer));
    }

    /**
     * 修改客户库
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap) {
        Boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        mmap.put("isAdmin", isAdmin);
                    } else {
                        mmap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                mmap.put("isAdmin", isAdmin);
            }
        }
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(customerId);
        mmap.put("sysCustomer", sysCustomer);
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        Map<String, String> map = new HashMap<>();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        if (StringUtils.isNotEmpty(sysCustomer.getCustomerLable())) {
            for (String string1 : sysCustomer.getCustomerLable().split(",")) {
                map.put(string1, string1);
            }
        }
        for (SysDictData sysDictData1 : sysDictDataList) {
            if (StringUtils.isNotEmpty(map.get(sysDictData1.getDictValue()))) {
                if (map.get(sysDictData1.getDictValue()).equals(sysDictData1.getDictValue())) {
                    sysDictData1.setFlag(true);
                }
            }
        }
        mmap.put("types", sysDictDataList);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户库
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "客户库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCustomer sysCustomer) {
        List<SysItem> sysItemList = sysItemService.selectItemByCustomerId(sysCustomer.getCustomerId());
        for (SysItem sysItem : sysItemList) {
            sysItem.setShareUserId(sysCustomer.getShareUserId());
            sysItem.setShareUserName(sysCustomer.getShareUserName());
            sysItemService.updateSysItem(sysItem);
        }
        SysPcustomer sysPcustomer = new SysPcustomer();
        sysPcustomer.setCustomerId(sysCustomer.getCustomerId().toString());
        List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
        for (SysPcustomer sysPcustomer1 : sysPcustomerList) {
            sysPcustomer1.setShareUserId(sysCustomer.getShareUserId());
            sysPcustomer1.setShareUserName(sysCustomer.getShareUserName());
            sysPcustomerService.updateSysPcustomer(sysPcustomer1);
        }

        sysCustomer.setUpdateBy(ShiroUtils.getLoginName());
        sysCustomer.setReviser(ShiroUtils.getSysUser().getUserName());
        return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
    }

    /**
     * 删除客户库
     */
    @RequiresPermissions("system:customer:remove")
    @Log(title = "客户库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        for (String string : ids.split(",")) {
            SysPcustomer sysPcustomer = new SysPcustomer();
            sysPcustomer.setCustomerId(string);
            List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
            for (SysPcustomer sysPcustomer1 : sysPcustomerList) {
                sb.append(sysPcustomer1.getDealCustomerId()).append(",");
            }

            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(Long.valueOf(string));
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                sb1.append(sysItem1.getItemId()).append(",");
            }
        }
        sysPcustomerService.deleteSysPcustomerByIds(sb.toString());
        sysItemService.deleteSysItemByIds(sb1.toString());
        return toAjax(sysCustomerService.deleteSysCustomerByIds(ids));
    }

    public String repeatStr(String string1, String string2) {
        StringBuffer sb = new StringBuffer();
        for (String string : string2.split(",")) {
            if (!string.equals(string1)) {
                sb.append(string).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 选择客户树
     */
    @GetMapping("/selectCustomerTree")
    public String selectCustomerTree(String customerIds, String customerNames, ModelMap mmap) {
        mmap.put("customerIds", customerIds);
        mmap.put("customerNames", customerNames);
        return prefix + "/tree";
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysCustomer sysCustomer) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                    }
                }
            }
        }

        return sysCustomerService.checkPhoneUnique(sysCustomer);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkWeChatNumberUnique")
    @ResponseBody
    public String checkWeChatNumberUnique(SysCustomer sysCustomer) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if (StringUtils.isNull(sysCustomer.getDeptId())) {
                            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                        }
                    }
                }
            }
        }
        return sysCustomerService.checkWeChatNumberUnique(sysCustomer);
    }

    @RequiresPermissions("system:customer:list")
    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysCustomer sysCustomer) {
       /* StringBuffer sb = new StringBuffer();
        for (String string : sysCustomer.getCustomerLables()) {
            sb.append(string).append(",");
        }
        sysCustomer.setCustomerLable(sb.deleteCharAt(sb.length() - 1).toString());*/
        modelMap.put("sysCustomer", sysCustomer);
        return "system/customer/queryAll";
    }

    @RequiresPermissions("system:customer:list")
    @PostMapping("/queryAllList")
    @ResponseBody
    public TableDataInfo queryAllList(SysCustomer sysCustomer) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())
                                || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            String ids = "201,207,208,209";
                            sysCustomer.setDeptIds(ids.split(","));
                        } else {
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        }
                    }
                }
            }
        }
        List<SysCustomer> list = sysCustomerService.queryAll(sysCustomer);
        for (SysCustomer sysCustomer1 : list) {
            StringBuffer sb = new StringBuffer();
            SysItem sysItem = new SysItem();
            SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                            if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                    && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                                //  sysItem.setCreateBy(ShiroUtils.getLoginName());
                                sysCustomer1.setIsAdmin("N");
                            } else {
                                sysCustomer1.setIsAdmin("N");
                            }
                        } else {
                            sysCustomer1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysCustomer1.setIsAdmin("Y");
                }
            }
            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                if (StringUtils.isNotNull(sysItem1.getProjectName())) {
                    sb.append(sysItem1.getProjectName()).append(";");
                }
            }
            sysCustomer1.setProjectName(sb.toString().trim());
        }
        return getDataTable(list);
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectUser")
    public String selectUser(String loginName, String userName, ModelMap mmap) {
        mmap.put("loginName", loginName);
        mmap.put("userName", userName);
        return prefix + "/selectUser";
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectShareUser")
    public String selectShareUser(String userId, String userName, ModelMap mmap) {
        mmap.put("userId", userId);
        mmap.put("userName", userName);
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                            mmap.put("deptId", 202);
                        }
                    }
                }
            }
        }

        return prefix + "/selectShareUser";
    }

    /*
     * 详情信息
     * @Description:
     * @param: customerId
     * @param: mmap
     * @return java.lang.String
     * @author lxz
     * @date 2021/3/26 14:29
     */
    @GetMapping("/detail/{customerId}")
    public String detail(@PathVariable("customerId") Long customerId, ModelMap mmap) {
        Boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        mmap.put("isAdmin", isAdmin);
                    } else {
                        mmap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                mmap.put("isAdmin", isAdmin);
            }
        }
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(customerId);
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        Map<String, String> map = new HashMap<>();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        if (StringUtils.isNotEmpty(sysCustomer.getCustomerLable())) {
            for (String string1 : sysCustomer.getCustomerLable().split(",")) {
                map.put(string1, string1);
            }
        }
        for (SysDictData sysDictData1 : sysDictDataList) {
            if (StringUtils.isNotEmpty(map.get(sysDictData1.getDictValue()))) {
                if (map.get(sysDictData1.getDictValue()).equals(sysDictData1.getDictValue())) {
                    sysDictData1.setFlag(true);
                }
            }
        }
        mmap.put("types", sysDictDataList);
        mmap.put("sysCustomer", sysCustomer);
        return prefix + "/detail";
    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysCustomer sysCustomer) {
        sysCustomer.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
    }

}
