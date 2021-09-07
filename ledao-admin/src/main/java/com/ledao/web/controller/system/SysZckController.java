package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.sql.SqlUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysItemService;
import com.ledao.system.service.ISysZcbService;
import com.ledao.system.service.ISysZckService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 资产信息库Controller
 *
 * @author lxz
 * @date 2020-06-09
 */
@Controller
@RequestMapping("/system/zcb/zck")
public class SysZckController extends BaseController {
    private String prefix = "system/zcb/zck";

    @Autowired
    private ISysZckService sysZckService;

    @Autowired
    private ISysZcbService sysZcbService;

    @Autowired
    private ISysItemService sysItemService;

    @RequiresPermissions("system:zck:view")
    @GetMapping()
    public String zck() {
        return prefix + "/zck";
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysZck sysZck) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"investmentManager".equals(sysRole.getRoleKey()) && !"tzbzz".equals(sysRole.getRoleKey())) {
                        sysZck.setCreateBy(currentUser.getLoginName());
                    }
                }
            }
        }
        List<SysZck> list = sysZckService.selectSysZck(sysZck);
        for (SysZck sysZck1 : list) {
            SysZck sysZck3 = new SysZck();
            sysZck3.setId(sysZck1.getId());
            List<SysZck> list1 = sysZckService.selectSysZckByParentId(sysZck1);
            for (SysZck sysZck2 : list1) {
                SysZck sysZck4 = sysZckService.selectSysZckById(sysZck2.getId());
                if (StringUtils.isEmpty(sysZck4.getCapValue()) || StringUtils.isNull(sysZck4.getCapValue())) {
                    sysZck4.setCapValue(new BigDecimal(0).toString());
                } else {
                    sysZck4.setCapValue(new BigDecimal(sysZck4.getCapValue()).toString());
                }
                if (sysZck1.getCapValues() == null) {
                    sysZck1.setCapValues(new BigDecimal(0));
                }
                sysZck1.setCapValues(new BigDecimal(sysZck4.getCapValue()).add(sysZck1.getCapValues()));
                if (sysZck4.getTotalPrice() == null) {
                    sysZck4.setTotalPrice(new BigDecimal(0));
                }
                if (sysZck1.getTotalPrice1() == null) {
                    sysZck1.setTotalPrice1(new BigDecimal(0));
                }
                sysZck1.setTotalPrice1(sysZck1.getTotalPrice1().add(sysZck4.getTotalPrice()));
            }
        }
        return getDataTable(list);
    }

//    @RequiresPermissions("system:zck:list")
    @PostMapping("/listDoc")
    @ResponseBody
    public TableDataInfo listDoc(SysZck sysZck) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        /*if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"investmentManager".equals(sysRole.getRoleKey()) && !"tzbzz".equals(sysRole.getRoleKey())) {
                        sysZck.setCreateBy(currentUser.getLoginName());
                    }
                }
            }
        }*/
        List<SysZck> list = sysZckService.selectSysZck(sysZck);
        for (SysZck sysZck1 : list) {
            SysZck sysZck3 = new SysZck();
            sysZck3.setId(sysZck1.getId());
            List<SysZck> list1 = sysZckService.selectSysZckByParentId(sysZck1);
            for (SysZck sysZck2 : list1) {
                SysZck sysZck4 = sysZckService.selectSysZckById(sysZck2.getId());
                if (StringUtils.isEmpty(sysZck4.getCapValue()) || StringUtils.isNull(sysZck4.getCapValue())) {
                    sysZck4.setCapValue(new BigDecimal(0).toString());
                } else {
                    sysZck4.setCapValue(new BigDecimal(sysZck4.getCapValue()).toString());
                }
                if (sysZck1.getCapValues() == null) {
                    sysZck1.setCapValues(new BigDecimal(0));
                }
                sysZck1.setCapValues(new BigDecimal(sysZck4.getCapValue()).add(sysZck1.getCapValues()));
                if (sysZck4.getTotalPrice() == null) {
                    sysZck4.setTotalPrice(new BigDecimal(0));
                }
                if (sysZck1.getTotalPrice1() == null) {
                    sysZck1.setTotalPrice1(new BigDecimal(0));
                }
                sysZck1.setTotalPrice1(sysZck1.getTotalPrice1().add(sysZck4.getTotalPrice()));
            }
        }
        return getDataTable(list);
    }

    @RequiresPermissions("system:zck:list")
    @GetMapping({"/zckList/{id}/{zcbId}"})
    public String selectZcbByAssetStatus(@PathVariable("id") Long id, @PathVariable("zcbId") Long zcbId, ModelMap modelMap) {
        modelMap.put("id", id);
        modelMap.put("zcbId", zcbId);
        modelMap.put("projectName", sysZckService.selectSysZckById(id).getProjectName());
        return "system/zcb/zck/zckList";
    }

    @RequiresPermissions("system:zck:list")
    @PostMapping("/zckList")
    @ResponseBody
    public TableDataInfo zckList(SysZck sysZck) {
        StringBuffer sb = new StringBuffer();
        List<SysZck> list = sysZckService.selectSysZckByParentId(sysZck);
        for (SysZck syszck : list) {
            sb.append(syszck.getId()).append(",");
        }
        startPage();
        String ids = sb.deleteCharAt(sb.length() - 1).toString();
        List<SysZck> zckList = sysZckService.selectSysZckByZckId(ids);
        return getDataTable(zckList);
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zck:list")
    @GetMapping("/lists")
    @ResponseBody
    public TableDataInfo lists(SysZck sysZck) {
        startPage();
        List<SysZck> list = sysZckService.selectSysZckList(sysZck);
        return getDataTable(list);
    }

    /**
     * 导出资产信息库列表
     */
    @RequiresPermissions("system:zck:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysZck sysZck) {
        String id = getRequest().getParameter("id");
        String zcbId = getRequest().getParameter("zcbId");
        List<SysZck> zckList = new ArrayList<>();
        if (StringUtils.isEmpty(getRequest().getParameter("ids"))) {
            if (StringUtils.isEmpty(id)) {
                StringBuffer sb = new StringBuffer();
                List<SysZck> zckList1 = sysZckService.selectSysZckByZcbId(Long.valueOf(zcbId));
                for (SysZck syszck : zckList1) {
                    sb.append(syszck.getId()).append(",");
                }
                String ids = sb.deleteCharAt(sb.length() - 1).toString();
                zckList = sysZckService.selectSysZckByZckId(ids);
            } else {
                StringBuffer sb = new StringBuffer();
                sysZck.setParentId(Long.valueOf(id));
                List<SysZck> zckList1 = sysZckService.selectSysZckByParentId(sysZck);
                for (SysZck syszck : zckList1) {
                    sb.append(syszck.getId()).append(",");
                }
                String ids = sb.deleteCharAt(sb.length() - 1).toString();
                zckList = sysZckService.selectSysZckByZckId(ids);
            }
        } else {
            zckList = sysZckService.selectSysZckByZckId(getRequest().getParameter("ids"));
        }

        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.exportExcel(zckList, "资产库");
    }


    /**
     * 导出资产信息库列表
     */
    @RequiresPermissions("system:zck:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export2")
    @ResponseBody
    public AjaxResult export2(SysZck sysZck) {
        String id = getRequest().getParameter("ids");
        String zcbId = getRequest().getParameter("zcbId");
        List<SysZck> zckList = new ArrayList<>();
        if (StringUtils.isEmpty(id)) {
            StringBuffer sb = new StringBuffer();
            List<SysZck> zckList1 = sysZckService.selectSysZckByZcbId(Long.valueOf(zcbId));
            for (SysZck syszck : zckList1) {
                sb.append(syszck.getId()).append(",");
            }
            String ids = sb.deleteCharAt(sb.length() - 1).toString();
            zckList = sysZckService.selectSysZckByZckId(ids);
        } else {
            StringBuffer sb = new StringBuffer();
            for (String string1 : id.split(",")) {
                sysZck.setId(Long.valueOf(string1));
                List<SysZck> zckList1 = sysZckService.selectSysZckByParentId(sysZck);
                for (SysZck syszck : zckList1) {
                    sb.append(syszck.getId()).append(",");
                }
            }
            String ids = sb.deleteCharAt(sb.length() - 1).toString();
            zckList = sysZckService.selectSysZckByZckId(ids);

        }

        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.exportExcel(zckList, "资产库");
    }


    /**
     * 导出资产信息库列表
     */
    @RequiresPermissions("system:zck:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    @ResponseBody
    public AjaxResult export1(SysZck sysZck) {
        String projectName = getRequest().getParameter("projectName");
        String city = getRequest().getParameter("city");
        String guarantor = getRequest().getParameter("guarantor");
        String mortgageRank = getRequest().getParameter("mortgageRank");
        String natureLand = getRequest().getParameter("natureLand");
        String collateType = getRequest().getParameter("collateType");
        List<SysZck> zckList = new ArrayList<>();
        if (StringUtils.isNull(getRequest().getParameter("ids"))) {
            sysZck.setProjectName(projectName);
            sysZck.setCity(city);
            sysZck.setGuarantor(guarantor);
            sysZck.setMortgageRank(mortgageRank);
            sysZck.setNatureLand(natureLand);
            sysZck.setCollateType(collateType);
            zckList = sysZckService.queryAll(sysZck);
        } else {
            zckList = sysZckService.selectSysZckByZckId(getRequest().getParameter("ids"));
        }

        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.exportExcel(zckList, "资产库");
    }

    /**
     * 新增资产信息库
     */
    @GetMapping("/add/{zcbId}")
    public String add(@PathVariable("zcbId") String zcbId, ModelMap mmap) {
        mmap.put("zcbId", zcbId);
        return prefix + "/add";
    }

    /**
     * 新增资产信息库
     */
    @GetMapping("/adds/{zcbId}/{parentId}")
    public String adds(@PathVariable("zcbId") String zcbId, @PathVariable("parentId") String parentId, ModelMap mmap) {
        mmap.put("zcbId", zcbId);
        mmap.put("parentId", parentId);
        return prefix + "/add";
    }

    /**
     * 新增保存资产信息库
     */
    @RequiresPermissions("system:zck:add")
    @Log(title = "资产信息库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysZck sysZck) {
        List<SysZck> sysZckList = sysZckService.selectSysZckList(sysZck);
        Map<String, Long> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        for (SysZck sysZck1 : sysZckList) {
            map.put(sysZck1.getProjectName(), sysZck1.getId());
            map1.put(sysZck1.getProjectName(), sysZck1.getProjectName());
        }
        if (StringUtils.isNotNull(map1.get(sysZck.getProjectName()))) {
            if (map1.get(sysZck.getProjectName()).equals(sysZck.getProjectName())) {
                sysZck.setParentId(map.get(sysZck.getProjectName()));
            }
        }
        SysZcb sysZcb = sysZcbService.selectSysZcbById(sysZck.getZcbId());
        sysZck.setAssetType(sysZcb.getAssetStatus());
        sysZck.setCreateBy(ShiroUtils.getLoginName());
        sysZckService.insertSysZck(sysZck);

        //关联项目
        if (StringUtils.isNotNull(sysZck.getCustomerId()) && StringUtils.isNotNull(sysZck.getCustomer())) {
            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(sysZck.getCustomerId());
            sysItem.setProjectName(sysZck.getCustomer());
            sysItem.setProjectId(sysZck.getId().toString());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            if (sysItemList.size() > 0) {
                return error("该客户已关联此项目");
            } else {
                sysItemService.insertSysItem(sysItem);
            }
        }

        return toAjax(Integer.parseInt(String.valueOf(sysZck.getId())));
    }

    /**
     * 修改资产信息库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysZck sysZck = sysZckService.selectSysZckById(id);
        mmap.put("sysZck", sysZck);
        return prefix + "/edit";
    }

    /**
     * 修改保存资产信息库
     */
    @RequiresPermissions("system:zck:edit")
    @Log(title = "资产信息库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysZck sysZck) {
        //关联项目
        if (StringUtils.isNotNull(sysZck.getCustomerId()) && StringUtils.isNotNull(sysZck.getCustomer())) {
            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(sysZck.getCustomerId());
            sysItem.setProjectName(sysZck.getCustomer());
            sysItem.setProjectId(sysZck.getId().toString());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            if (sysItemList.size() > 0) {
                return error("该客户已关联此项目");
            } else {
                sysItemService.insertSysItem(sysItem);
            }
        }
        sysZck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysZckService.updateSysZck(sysZck));
    }

    /**
     * 删除资产信息库
     */
    @RequiresPermissions("system:zck:remove")
    @Log(title = "资产信息库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        StringBuffer sb = new StringBuffer();
        for (String string : ids.split(",")) {
            SysZck sysZck = new SysZck();
            sysZck.setId(Long.valueOf(string));
            List<SysZck> list = sysZckService.selectSysZckByParentId(sysZck);
            for (SysZck syszck : list) {
                sb.append(syszck.getId()).append(",");
            }
        }

        String id = sb.deleteCharAt(sb.length() - 1).toString();
        return toAjax(sysZckService.deleteSysZckByIds(id));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:zck:detail")
    @Log(title = "资产信息库", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        SysZck sysZck = sysZckService.selectSysZckById(id);
        if (StringUtils.isNotNull(sysZck.getContractPrincipal())) {
            sysZck.setContractPrincipals(decimalFormat.format(sysZck.getContractPrincipal()));
        }
        if (StringUtils.isNotNull(sysZck.getPrincipalBalance())) {
            sysZck.setPrincipalBalances(decimalFormat.format(sysZck.getPrincipalBalance()));
        }
        if (StringUtils.isNotNull(sysZck.getInterestBalance())) {
            sysZck.setInterestBalances(decimalFormat.format(sysZck.getInterestBalance()));
        }
        if (StringUtils.isNotNull(sysZck.getPrincipalInterestBalance())) {
            sysZck.setPrincipalInterestBalances(decimalFormat.format(sysZck.getPrincipalInterestBalance()));
        }
        if (StringUtils.isNotNull(sysZck.getGuaranteeAmount())) {
            sysZck.setGuaranteeAmounts(decimalFormat.format(sysZck.getGuaranteeAmount()));
        }
        if (StringUtils.isNotNull(sysZck.getMaximumGuaranteeAmount())) {
            sysZck.setMaximumGuaranteeAmounts(decimalFormat.format(sysZck.getMaximumGuaranteeAmount()));
        }
        if (StringUtils.isNotNull(sysZck.getMortgageAmount())) {
            sysZck.setMortgageAmounts(decimalFormat.format(sysZck.getMortgageAmount()));
        }
        if (StringUtils.isNotNull(sysZck.getQxswMortgeageAmount())) {
            sysZck.setQxswMortgeageAmounts(decimalFormat.format(sysZck.getQxswMortgeageAmount()));
        }
        if (StringUtils.isNotNull(sysZck.getLandUnitPrice())) {
            sysZck.setLandUnitPrices(decimalFormat.format(sysZck.getLandUnitPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getLandTotalPrice())) {
            sysZck.setLandTotalPrices(decimalFormat.format(sysZck.getLandTotalPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getOtherCollateralUnitPrice())) {
            sysZck.setOtherCollateralUnitPrices(decimalFormat.format(sysZck.getOtherCollateralUnitPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getOtherCollateralTotalPrice())) {
            sysZck.setOtherCollateralTotalPrices(decimalFormat.format(sysZck.getOtherCollateralTotalPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getTotalPrice())) {
            sysZck.setTotalPrices(decimalFormat.format(sysZck.getTotalPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getMaximumMortgageAmount())) {
            sysZck.setMaximumMortgageAmounts(decimalFormat.format(sysZck.getMaximumMortgageAmount()));
        }
        if (StringUtils.isNotNull(sysZck.getPrice())) {
            sysZck.setPrices(decimalFormat.format(sysZck.getPrice()));
        }
        if (StringUtils.isNotNull(sysZck.getDesposalPrice())) {
            sysZck.setDesposalPrices(decimalFormat.format(sysZck.getDesposalPrice()));
        }

        if (StringUtils.isNotNull(sysZck.getCapValue())) {
            sysZck.setCapValue(decimalFormat.format(new BigDecimal(sysZck.getCapValue())));
        }
        mmap.put("sysZck", sysZck);
        return prefix + "/detail";
    }

    @Log(title = "资产信息库", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:zck:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport, Long zcbId) throws Exception {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        List<SysZck> sysZckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysZckService.importZck(sysZckList, updateSupport, operName, zcbId);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:zck:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.importTemplateExcel("资产库");
    }

    @RequiresPermissions("system:zck:list")
    @PostMapping("/listes")
    @ResponseBody
    public TableDataInfo listes(SysZck sysZck) {
        startPage();
        List<SysZck> list = sysZckService.queryAll(sysZck);
        for (SysZck syszck : list) {
            SysZcb sysZcb = sysZcbService.selectSysZcbById(syszck.getZcbId());
            syszck.setZcbStatus(sysZcb.getAssetStatus());
            syszck.setZcbName(sysZcb.getAssetPackageName());
        }
        return getDataTable(list);
    }

//    @RequiresPermissions("system:zck:list")
    @PostMapping("/listesDoc")
    @ResponseBody
    public TableDataInfo listesDoc(SysZcb sysZcb) {
        startPage();
        List<SysZcb> list = sysZcbService.selectZcbListUseful(sysZcb);
        return getDataTable(list);
    }


    //判断序号是否唯一
    @PostMapping("/checkNoUnique")
    @ResponseBody
    public String checkNoUnique(SysZck sysZck) {
        List<SysZck> sysZckList = sysZckService.selectSysZck(sysZck);
        if (sysZckList.size() > 0) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 选择项目树
     */
    @GetMapping("/selectProjectTree")
    public String selectCustomerTree(String selectedProjectIds, String selectedProjectNames, ModelMap mmap) {
        mmap.put("selectedProjectIds", selectedProjectIds);
        mmap.put("selectedProjectNames", selectedProjectNames);
        return prefix + "/tree";
    }

}
