package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.annotation.RepeatSubmit;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.GisUtils;
import com.ledao.common.utils.LonlatConver;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysTagging;
import com.ledao.system.service.ISysTaggingService;
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
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 司法拍卖项目Controller
 *
 * @author ledao
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/system/judicial")
public class SysJudicialController extends BaseController {
    private String prefix = "system/judicial";

    @Autowired
    private ISysJudicialService sysJudicialService;

    @Autowired
    private ISysTaggingService sysTaggingService;

    @RequiresPermissions("system:judicial:view")
    @GetMapping()
    public String judicial() {
        return prefix + "/judicial";
    }

    /**
     * 查询司法拍卖项目列表
     */
    @RequiresPermissions("system:judicial:list")
    @PostMapping("/list")
    @ResponseBody
    @RepeatSubmit
    @Log(title = "网拍线索查询", businessType = BusinessType.QUERY)
    public TableDataInfo list(SysJudicial sysJudicial) {
        startPage();
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        for (SysJudicial sysJudicial1 : list) {
            SysTagging sysTagging = new SysTagging();
            sysTagging.setCreateBy(ShiroUtils.getLoginName());
            sysTagging.setItemId(sysJudicial1.getItemId());
            sysTagging.setJudicial("Y");
            List<SysTagging> sysTaggingList = sysTaggingService.selectSysTaggingList(sysTagging);
            if (sysTaggingList.size() > 0) {
                sysJudicial1.setTaggings("Y");
            }
        }
        return getDataTable(list, sysJudicial);
    }

    @PostMapping("/valuationmapList")
    @ResponseBody
    public String valuationmapList(SysJudicial sysJudicial) {
        JSONArray array = new JSONArray();
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        Map<String, String> map = new HashMap<>();
        List<SysJudicial> sysJudicialList2 = new ArrayList<>();
        for (SysJudicial sysJudicial1 : list) {
            if (StringUtils.isNotEmpty(sysJudicial1.getCoordinate())) {
                if (!"0".equals(sysJudicial1.getCoordinate())) {
                    if (map.containsKey(sysJudicial1.getCoordinate())) {
                        StringBuffer sb = new StringBuffer();
                        for (String s : sysJudicial1.getCoordinate().trim().split(",")) {
                            sb.append(new BigDecimal(s.trim()).add(new BigDecimal("0.00001"))).append(",");
                        }
                        sysJudicial1.setCoordinate(sb.substring(0, sb.toString().length() - 1));
                        map.put(sysJudicial1.getCoordinate(), sysJudicial1.getItemId());
                        sysJudicialList2.add(sysJudicial1);
                    } else {
                        map.put(sysJudicial1.getCoordinate(), sysJudicial1.getItemId());
                        sysJudicialList2.add(sysJudicial1);
                    }
                }
            }
        }
        for (SysJudicial sysJudicial1 : sysJudicialList2) {
            JSONObject object = new JSONObject();
            if (StringUtils.isNotEmpty(sysJudicial1.getCoordinate())) {
                if (!"0".equals(sysJudicial1.getCoordinate())) {
                    //地址
                    object.put("address", sysJudicial1.getAddress());
                    //标题
                    object.put("title", sysJudicial1.getItemTitle());
                    //坐标
                    object.put("itemXY", sysJudicial1.getCoordinate());
                    //标题
                    object.put("itemTitle", sysJudicial1.getItemTitle());
                    //类型
                    object.put("type", sysJudicial1.getItemType());
                    //网址
                    object.put("itemLink", sysJudicial1.getItemLink());
                    //成交价
                    object.put("itemCurrentprice", sysJudicial1.getItemCurrentprice());
                    //成交日期
                    if (StringUtils.isNotNull(sysJudicial1.getItemEndTime())) {
                        object.put("itemEndTime", DateUtils.parseDateToStr("yyyy-MM-dd", sysJudicial1.getItemEndTime()));
                    }
                    //状态
                    object.put("tags", sysJudicial1.getTags());
                    object.put("itemId", sysJudicial1.getItemId());
                    object.put("latLon", GisUtils.getDistance(Double.valueOf(sysJudicial.getLatLon().split(",")[0]), Double.valueOf(sysJudicial.getLatLon().split(",")[1]), Double.valueOf(sysJudicial1.getCoordinate().split(",")[0]), Double.valueOf(sysJudicial1.getCoordinate().split(",")[1])));
                    array.add(object);
                }
            }
        }
        return array.toString();
    }

    public static void main(String[] args) {
        String s = "120.29650599,31.64596393; 133.997776, 36.749563; 120.36901443,31.55381549;120.34924183,31.60148982";
        StringBuffer sb = new StringBuffer();
        for (String ss : s.split(";")) {
            for (String s1 : ss.split(",")) {
                sb.append(new BigDecimal(s1.trim()).add(new BigDecimal("0.00001"))).append(",");
            }
        }
        System.out.println("======" + sb.substring(0, sb.toString().length() - 1));
    }

    protected TableDataInfo getDataTable(List<?> list, SysJudicial sysJudicial) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(sysJudicialService.selectSysJudicialListTotal(sysJudicial));
        return rspData;
    }

    /**
     * 导出司法拍卖项目列表
     */
    @RequiresPermissions("system:judicial:export")
    @Log(title = "司法拍卖项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJudicial sysJudicial) {
        sysJudicial.setItemType("住宅用房");
        sysJudicial.setItemStatus("已结束");
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        List<SysJudicial> list1 = new ArrayList<>();
        for (SysJudicial sysJudicial1 : list) {
            if (StringUtils.isNotNull(sysJudicial1.getCoordinate())) {
                if (sysJudicial1.getCoordinate().split(",").length > 2) {
                    if (checkItude(sysJudicial1.getCoordinate().split(",")[0].trim(), sysJudicial1.getCoordinate().split(",")[1].trim())) {
                        Double[] latitudeAndLongitude = LonlatConver.bd09_To_Gcj02(Double.valueOf(sysJudicial1.getCoordinate().split(",")[0].trim()), Double.valueOf(sysJudicial1.getCoordinate().split(",")[1].trim()));
                        sysJudicial1.setItemIds(sysJudicial1.getItemId());
                        sysJudicial1.setRadiues("5000");
                        sysJudicial1.setLng1(Double.valueOf(120.94775));
                        sysJudicial1.setLat1(Double.valueOf(31.380546));
                        sysJudicial1.setFlag(GisUtils.isInCircle(sysJudicial1.getLng1(), sysJudicial1.getLat1(), latitudeAndLongitude[0], latitudeAndLongitude[1], "5000"));
                        if (GisUtils.isInCircle(sysJudicial1.getLng1(), sysJudicial1.getLat1(), latitudeAndLongitude[0], latitudeAndLongitude[1], "5000")) {
                            list1.add(sysJudicial1);
                        }
                    }
                }
            }
        }
        logger.info("总数量：------" + list1.size());
        ExcelUtil<SysJudicial> util = new ExcelUtil<SysJudicial>(SysJudicial.class);
        return util.exportExcel(list1, "司法拍卖");
    }

    public static boolean checkItude(String longitude, String latitude) {
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        longitude = longitude.trim();
        latitude = latitude.trim();
        return longitude.matches(reglo) == true ? latitude.matches(regla) : false;
    }

    /**
     * 新增司法拍卖项目
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存司法拍卖项目
     */
    @RequiresPermissions("system:judicial:add")
    @Log(title = "司法拍卖项目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.insertSysJudicial(sysJudicial));
    }

    /**
     * 修改司法拍卖项目
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("itemId") String itemId, ModelMap mmap) {
        SysJudicial sysJudicial = sysJudicialService.selectSysJudicialById(itemId);
        mmap.put("sysJudicial", sysJudicial);
        return prefix + "/edit";
    }

    /**
     * 修改保存司法拍卖项目
     */
    @RequiresPermissions("system:judicial:edit")
    @Log(title = "司法拍卖项目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.updateSysJudicial(sysJudicial));
    }

    /**
     * 删除司法拍卖项目
     */
    @RequiresPermissions("system:judicial:remove")
    @Log(title = "司法拍卖项目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysJudicialService.deleteSysJudicialByIds(ids));
    }


    /**
     * 新增保存星标库
     */
    @RequiresPermissions("system:tagging:add")
    @Log(title = "星标库", businessType = BusinessType.INSERT)
    @PostMapping("/addTagging")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult addTagging(SysTagging sysTagging) {
        sysTagging.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysTaggingService.insertSysTagging(sysTagging));
    }


    /**
     * 删除星标库
     */
    @RequiresPermissions("system:tagging:remove")
    @Log(title = "星标库", businessType = BusinessType.DELETE)
    @PostMapping("/removeTagging")
    @ResponseBody
    @RepeatSubmit
    public AjaxResult removeTagging(SysTagging sysTagging) {
        SysTagging sysTagging1 = sysTaggingService.selectSysTaggingByItemId(sysTagging.getItemId());
        return toAjax(sysTaggingService.deleteSysTaggingById(sysTagging1.getId()));
    }

    @Log(title = "估值计算", businessType = BusinessType.OTHER)
    @GetMapping("/valuationModel")
    public String valuationModel(String itemId, ModelMap modelMap) {

        SysJudicial sysJudicial1 = sysJudicialService.selectSysJudicialById(itemId);
        if (StringUtils.isNull(sysJudicial1.getItemConsultprice())) {
            modelMap.put("itemMarketPrice", sysJudicial1.getItemMarketprice());
        } else {
            modelMap.put("itemMarketPrice", sysJudicial1.getItemConsultprice());
        }
        modelMap.put("floorSpace", StringUtils.isNull(sysJudicial1.getFloorSpace()) ? Long.valueOf(0) : sysJudicial1.getFloorSpace());
        modelMap.put("itemCurrentprice", sysJudicial1.getItemCurrentprice());
        modelMap.put("itemType", sysJudicial1.getItemType());
        modelMap.put("itemStatus", sysJudicial1.getItemStatus());
        modelMap.put("itemMarketPrice", sysJudicial1.getItemMarketprice());
        return "system/valuationModel/valuationModel";
    }
}
