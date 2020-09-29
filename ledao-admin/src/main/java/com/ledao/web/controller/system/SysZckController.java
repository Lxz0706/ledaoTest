package com.ledao.web.controller.system;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ledao.common.json.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;
import com.ledao.system.dao.SysZcb;
import com.ledao.system.dao.SysZck;
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

    @RequiresPermissions("system:zcb:view")
    @GetMapping()
    public String zck() {
        return prefix + "/zck";
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zcb:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysZck sysZck) {
        startPage();
        List<SysZck> list = sysZckService.selectSysZck(sysZck);
        return getDataTable(list);
    }

    @RequiresPermissions("system:zcb:list")
    @GetMapping({"/zckList/{id}/{zcbId}"})
    public String selectZcbByAssetStatus(@PathVariable("id") Long id, @PathVariable("zcbId") Long zcbId, ModelMap modelMap) {
        modelMap.put("id", id);
        modelMap.put("zcbId", zcbId);
        return "system/zcb/zck/zckList";
    }

    @RequiresPermissions("system:zcb:list")
    @PostMapping("/zckList")
    @ResponseBody
    public TableDataInfo zckList(SysZck sysZck) {
        startPage();
        StringBuffer sb = new StringBuffer();
        List<SysZck> list = sysZckService.selectSysZckByParentId(sysZck);
        for (SysZck syszck : list) {
            sb.append(syszck.getId()).append(",");
        }
        String ids = sb.deleteCharAt(sb.length() - 1).toString();
        List<SysZck> zckList = sysZckService.selectSysZckByZckId(ids);
        return getDataTable(zckList);
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zcb:list")
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
    @RequiresPermissions("system:zcb:export")
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
    @RequiresPermissions("system:zcb:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export2")
    @ResponseBody
    public AjaxResult export2(SysZck sysZck) {
        String id = getRequest().getParameter("id");
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
            sysZck.setParentId(Long.valueOf(id));
            List<SysZck> zckList1 = sysZckService.selectSysZckByParentId(sysZck);
            for (SysZck syszck : zckList1) {
                sb.append(syszck.getId()).append(",");
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
    @RequiresPermissions("system:zcb:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    @ResponseBody
    public AjaxResult export1(SysZck sysZck) {
        String borrower = getRequest().getParameter("borrower");
        String city = getRequest().getParameter("city");
        String guarantor = getRequest().getParameter("guarantor");
        String mortgageRank = getRequest().getParameter("mortgageRank");
        String natureLand = getRequest().getParameter("natureLand");
        String collateType = getRequest().getParameter("collateType");
        List<SysZck> zckList = new ArrayList<>();
        if (StringUtils.isNull(getRequest().getParameter("ids"))) {
            sysZck.setBorrower(borrower);
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
    @RequiresPermissions("system:zcb:add")
    @Log(title = "资产信息库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysZck sysZck) {
        List<SysZck> sysZckList = sysZckService.selectSysZckList(sysZck);
        Map<String, Long> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        Map<String, Long> map2 = new HashMap<>();
        for (SysZck sysZck1 : sysZckList) {
            map.put(sysZck1.getBorrower(), sysZck1.getId());
            map1.put(sysZck1.getBorrower(), sysZck1.getBorrower());
            map2.put(sysZck1.getBorrower(), sysZck1.getZcbId());
        }
        if (StringUtils.isNotNull(map1.get(sysZck.getBorrower()))) {
            if (map1.get(sysZck.getBorrower()).equals(sysZck.getBorrower())) {
                sysZck.setParentId(map.get(sysZck.getBorrower()));
            }
        }

        sysZck.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysZckService.insertSysZck(sysZck));
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
    @RequiresPermissions("system:zcb:edit")
    @Log(title = "资产信息库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysZck sysZck) {
        sysZck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysZckService.updateSysZck(sysZck));
    }

    /**
     * 删除资产信息库
     */
    @RequiresPermissions("system:zcb:remove")
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
    @RequiresPermissions("system:zcb:detail")
    @Log(title = "资产信息库", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysZck", sysZckService.selectSysZckById(id));
        return prefix + "/detail";
    }

    @Log(title = "资产信息库", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:zcb:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport, Long zcbId) throws Exception {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        List<SysZck> sysZckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysZckService.importZck(sysZckList, updateSupport, operName, zcbId);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:zcb:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.importTemplateExcel("资产库");
    }

    @RequiresPermissions("system:zcb:list")
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


}
