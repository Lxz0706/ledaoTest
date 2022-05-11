package com.ledao.web.controller.system;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.CoordinateUtil;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
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
import com.ledao.system.dao.SysValuationmap;
import com.ledao.system.service.ISysValuationmapService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 【请填写功能名称】Controller
 *
 * @author lxz
 * @date 2021-02-22
 */
@Controller
@RequestMapping("/system/valuationmap")
public class SysValuationmapController extends BaseController {
    private String prefix = "system/valuationmap";

    @Autowired
    private ISysValuationmapService sysValuationmapService;

    @Autowired
    private ISysJudicialService sysJudicialService;

    @RequiresPermissions("system:valuationmap:view")
    @GetMapping()
    public String valuationmap() {
        return prefix + "/valuationmap";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:valuationmap:list")
    @PostMapping("/list")
    @ResponseBody
    public String list(SysValuationmap sysValuationmap) {
        JSONArray array = new JSONArray();
        List<SysValuationmap> list = sysValuationmapService.selectSysValuationmapList(sysValuationmap);
        ThreadLocal<Long> startTime = new ThreadLocal<>();
        startTime.set(System.currentTimeMillis());
        for (SysValuationmap sysValuationmap1 : list) {
            JSONObject object = new JSONObject();
            if (StringUtils.isNotEmpty(sysValuationmap1.getItemX()) && StringUtils.isNotEmpty(sysValuationmap1.getItemY())) {
                if (!"0".equals(sysValuationmap1.getItemX()) && !"0".equals(sysValuationmap1.getItemY())) {
                    object.put("address", sysValuationmap1.getItemTitle());
                    object.put("city", sysValuationmap1.getItemCity());
                    object.put("title", sysValuationmap1.getItemTitle());
                    object.put("areaMeasure", sysValuationmap1.getItemAreameasure());
                    object.put("itemXY", sysValuationmap1.getItemX() + "," + sysValuationmap1.getItemY());
                    object.put("type", sysValuationmap1.getItemType());
                    object.put("statue", sysValuationmap1.getItemStatus());
                    object.put("itemX", sysValuationmap1.getItemX());
                    object.put("itemY", sysValuationmap1.getItemY());
                    array.add(object);
                }
            }
        }
        return array.toString();
    }

    public static void main(String[] args) {
        System.out.println(getWgs84xy(13269197.0,4085292.7));
    }

    public static String getWgs84xy(double x, double y) {

        //先转 国测局坐标
        double[] doubles_gcj = CoordinateUtil.bd09togcj02(x, y);

        //国测局坐标转wgs84
        double[] doubles_wgs84 = CoordinateUtil.gcj02towgs84(doubles_gcj[0], doubles_gcj[1]);

        //返回 纠偏后 坐标
        return doubles_wgs84[0] + "," + doubles_wgs84[1];
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:valuationmap:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysValuationmap sysValuationmap) {
        List<SysValuationmap> list = sysValuationmapService.selectSysValuationmapList(sysValuationmap);
        ExcelUtil<SysValuationmap> util = new ExcelUtil<SysValuationmap>(SysValuationmap.class);
        return util.exportExcel(list, "valuationmap");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:valuationmap:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysValuationmap sysValuationmap) {
        return toAjax(sysValuationmapService.insertSysValuationmap(sysValuationmap));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysValuationmap sysValuationmap = sysValuationmapService.selectSysValuationmapById(id);
        mmap.put("sysValuationmap", sysValuationmap);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:valuationmap:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysValuationmap sysValuationmap) {
        return toAjax(sysValuationmapService.updateSysValuationmap(sysValuationmap));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:valuationmap:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysValuationmapService.deleteSysValuationmapByIds(ids));
    }
}
