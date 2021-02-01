package com.ledao.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.constant.UserConstants;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysItemService;
import com.ledao.system.service.ISysProjectService;
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
import com.ledao.system.service.ISysCustomerService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

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
        /*SysItem sysItem1 = new SysItem();
        if (StringUtils.isNotNull(sysCustomer.getCustomerLable())) {
            sysItem1.setCustomerLable(sysCustomer.getCustomerLable());
        }
        List<SysItem> sysItemList1 = sysItemService.selectSysItemList(sysItem1);
        Map<Long, Long> lableMap = new HashMap<>();
        for (SysItem sysItem : sysItemList1) {
            lableMap.put(sysItem.getCustomerId(), sysItem.getCustomerId());
        }*/
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {

                        if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())
                                || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                        } else {
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
            /*SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                            sysItem.setCreateBy(ShiroUtils.getLoginName());
                        }
                    }
                }
            }*/
            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
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
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysCustomerService.checkPhoneUnique(sysCustomer))) {
            return error("新增客户'" + sysCustomer.getContacts() + "'失败，手机号码已存在");
        }
        SysCustomer sysCustomer1 = new SysCustomer();
        /*if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
            sysCustomer1.setCustomerName(sysCustomer.getCustomerName());
        }*/
        /*if (StringUtils.isNotNull(sysCustomer.getContacts())) {
            sysCustomer1.setContacts(sysCustomer.getContacts());
        }
        if (StringUtils.isNotNull(sysCustomer.getContactNumber())) {
            sysCustomer1.setContactNumber(sysCustomer.getContactNumber());
        }
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerList(sysCustomer1);
        if (sysCustomerList.size() > 0) {
            return error("该客户已存在！");
        }*/
        if (StringUtils.isNull(sysCustomer.getDeptId()) && StringUtils.isEmpty(sysCustomer.getDeptName())) {
            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
            sysCustomer.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
        }
        sysCustomer.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysCustomerService.insertSysCustomer(sysCustomer));
    }

    /**
     * 修改客户库
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap) {
        boolean isAdmin = true;
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

        /*SysCustomer sysCustomer1 = sysCustomerService.selectSysCustomerById(sysCustomer.getCustomerId());
        if (StringUtils.isNotNull(sysCustomer.getContacts()) && StringUtils.isNotNull(sysCustomer.getContactNumber())) {
            if (sysCustomer.getContacts().equals(sysCustomer1.getContacts()) && sysCustomer.getContactNumber().equals(sysCustomer1.getContactNumber())) {

            }
        }
        SysCustomer sysCustomer2 = new SysCustomer();
        if (StringUtils.isNotNull(sysCustomer.getContacts())) {
            sysCustomer2.setContacts(sysCustomer.getContacts());
        }
        if (StringUtils.isNotNull(sysCustomer.getContactNumber())) {
            sysCustomer2.setContactNumber(sysCustomer.getContactNumber());
        }
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerList(sysCustomer2);*/

        sysCustomer.setUpdateBy(ShiroUtils.getLoginName());
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
        int row = 0;
        for (String string : ids.split(",")) {
            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(Long.valueOf(string));
            SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(Long.valueOf(string));
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            row = sysCustomerService.deleteSysCustomerById(Long.valueOf(string));
            for (SysItem sysItem1 : sysItemList) {
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();
                SysProject sysProject = sysProjectService.selectSysProjectById(sysItem1.getProjectId());
                if ("资产供应方".equals(sysItem1.getCustomerLable())) {
                    sysProject.setAssetSupplierId(repeatStr(string, sysProject.getAssetSupplierId()));
                    sysProject.setAssetSupplierName(repeatStr(sysCustomer.getContacts(), sysProject.getAssetSupplierName()));
                } else if ("资金供应方".equals(sysItem1.getCustomerLable())) {
                    sysProject.setFundingProviderId(repeatStr(string, sysProject.getFundingProviderId()));
                    sysProject.setFundingProviderName(repeatStr(sysCustomer.getContacts(), sysProject.getFundingProviderName()));
                } else if ("律师".equals(sysItem1.getCustomerLable())) {
                    sysProject.setLawyerId(repeatStr(string, sysProject.getLawyerId()));
                    sysProject.setLawyerName(repeatStr(sysCustomer.getContacts(), sysProject.getLawyerName()));
                } else if ("中介方".equals(sysItem1.getCustomerLable())) {
                    sysProject.setIntermediaryId(repeatStr(string, sysProject.getIntermediaryId()));
                    sysProject.setIntermediaryName(repeatStr(sysCustomer.getContacts(), sysProject.getIntermediaryName()));
                } else if ("债权意向客户".equals(sysItem1.getCustomerLable())) {
                    sysProject.setZqyxCustomerId(repeatStr(string, sysProject.getZqyxCustomerId()));
                    sysProject.setZqyxCustomerName(repeatStr(sysCustomer.getContacts(), sysProject.getZqyxCustomerName()));
                } else if ("债权成交客户".equals(sysItem1.getCustomerLable())) {
                    sysProject.setZqcjCustomerId(repeatStr(string, sysProject.getZqcjCustomerId()));
                    sysProject.setZqcjCustomerName(repeatStr(sysCustomer.getContacts(), sysProject.getZqcjCustomerName()));
                } else if ("物权意向客户".equals(sysItem1.getCustomerLable())) {
                    sysProject.setWqyxCustomerId(repeatStr(string, sysProject.getWqyxCustomerId()));
                    sysProject.setWqyxCustomerName(repeatStr(sysCustomer.getContacts(), sysProject.getWqyxCustomerName()));
                } else if ("物权成交客户".equals(sysItem1.getCustomerLable())) {
                    sysProject.setWqcjCustomerId(repeatStr(string, sysProject.getWqcjCustomerId()));
                    sysProject.setWqcjCustomerName(repeatStr(sysCustomer.getContacts(), sysProject.getWqcjCustomerName()));
                } else if ("其他".equals(sysItem1.getCustomerLable())) {
                    sysProject.setOtherId(repeatStr(string, sysProject.getOtherId()));
                    sysProject.setOtherName(repeatStr(sysCustomer.getContacts(), sysProject.getOtherName()));
                }
                sysProjectService.updateSysProject(sysProject);
            }
        }
        return toAjax(row);
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
        return sysCustomerService.checkPhoneUnique(sysCustomer);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkWeChatNumberUnique")
    @ResponseBody
    public String checkWeChatNumberUnique(SysCustomer sysCustomer) {
        return sysCustomerService.checkWeChatNumberUnique(sysCustomer);

    }
}
