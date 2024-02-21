package com.ledao.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.system.dao.SysReservationDate;
import com.ledao.system.service.ISysReservationDateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 预约时间设置Controller
 *
 * @author lxz
 * @date 2023-11-17
 */
@Controller
@RequestMapping("/system/reservationDate")
public class SysReservationDateController extends BaseController {
    private String prefix = "system/reservationDate";

    @Autowired
    private ISysReservationDateService sysReservationDateService;

    @RequiresPermissions("system:reservationDate:view")
    @GetMapping()
    public String reservationDate() {
        return prefix + "/reservationDate";
    }

    /**
     * 查询预约时间设置列表
     */
    @RequiresPermissions("system:reservationDate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysReservationDate sysReservationDate) {
        startPage();
        List<SysReservationDate> list = sysReservationDateService.selectSysReservationDateList(sysReservationDate);
        return getDataTable(list);
    }

    /**
     * 导出预约时间设置列表
     */
    @RequiresPermissions("system:reservationDate:export")
    @Log(title = "预约时间设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysReservationDate sysReservationDate) {
        List<SysReservationDate> list = sysReservationDateService.selectSysReservationDateList(sysReservationDate);
        ExcelUtil<SysReservationDate> util = new ExcelUtil<SysReservationDate>(SysReservationDate.class);
        return util.exportExcel(list, "reservationDate");
    }

    /**
     * 新增预约时间设置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预约时间设置
     */
    @RequiresPermissions("system:reservationDate:add")
    @Log(title = "预约时间设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysReservationDate sysReservationDate) {
        List<Date> dates = DateUtils.getDatesBetween(sysReservationDate.getReservationDate().split("~")[0], sysReservationDate.getReservationDate().split("~")[1]);
        System.out.println("dates:======" + dates);
        StringBuffer sb = new StringBuffer();
        JSONArray jsonArray = new JSONArray();
        for (Date date : dates) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", DateUtils.parseDateToStr("yyyy/MM/dd", date));
            jsonObject.put("info", "");
            jsonArray.add(jsonObject);
        }
        sysReservationDate.setReservationDate(jsonArray.toString());
        return toAjax(sysReservationDateService.insertSysReservationDate(sysReservationDate));
    }

    /**
     * 修改预约时间设置
     */
    @GetMapping("/edit/{reservationDateId}")
    public String edit(@PathVariable("reservationDateId") Long reservationDateId, ModelMap mmap) {
        SysReservationDate sysReservationDate = sysReservationDateService.selectSysReservationDateById(reservationDateId);
        mmap.put("sysReservationDate", sysReservationDate);
        return prefix + "/edit";
    }

    /**
     * 修改保存预约时间设置
     */
    @RequiresPermissions("system:reservationDate:edit")
    @Log(title = "预约时间设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysReservationDate sysReservationDate) {
        return toAjax(sysReservationDateService.updateSysReservationDate(sysReservationDate));
    }

    /**
     * 删除预约时间设置
     */
    @RequiresPermissions("system:reservationDate:remove")
    @Log(title = "预约时间设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysReservationDateService.deleteSysReservationDateByIds(ids));
    }
}
