package com.ledao.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.utils.qrCode.WxQrCode;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysReservation;
import com.ledao.system.service.ISysConfigService;
import com.ledao.system.service.ISysReservationService;
import com.ledao.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 预约Controller
 *
 * @author lxz
 * @date 2023-11-17
 */
@Controller
@RequestMapping("/system/reservation")
public class SysReservationController extends BaseController {
    private String prefix = "system/reservation";

    @Autowired
    private ISysReservationService sysReservationService;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("system:reservation:view")
    @GetMapping()
    public String reservation() {
        return prefix + "/reservation";
    }

    /**
     * 查询预约列表
     */
    @RequiresPermissions("system:reservation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysReservation sysReservation) {
        startPage();
        List<SysReservation> list = sysReservationService.selectSysReservationList(sysReservation);
        return getDataTable(list);
    }

    /**
     * 导出预约列表
     */
    @RequiresPermissions("system:reservation:export")
    @Log(title = "预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysReservation sysReservation) {
        List<SysReservation> list = sysReservationService.selectSysReservationList(sysReservation);
        ExcelUtil<SysReservation> util = new ExcelUtil<SysReservation>(SysReservation.class);
        return util.exportExcel(list, "reservation");
    }

    /**
     * 新增预约
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预约
     */
    @RequiresPermissions("system:reservation:add")
    @Log(title = "预约", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysReservation sysReservation) {
        return toAjax(sysReservationService.insertSysReservation(sysReservation));
    }

    /**
     * 修改预约
     */
    @GetMapping("/edit/{reservationId}")
    public String edit(@PathVariable("reservationId") Long reservationId, ModelMap mmap) {
        SysReservation sysReservation = sysReservationService.selectSysReservationById(reservationId);
        mmap.put("sysReservation", sysReservation);
        return prefix + "/edit";
    }

    /**
     * 修改保存预约
     */
    @RequiresPermissions("system:reservation:edit")
    @Log(title = "预约", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysReservation sysReservation) {
        return toAjax(sysReservationService.updateSysReservation(sysReservation));
    }

    /**
     * 删除预约
     */
    @RequiresPermissions("system:reservation:remove")
    @Log(title = "预约", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysReservationService.deleteSysReservationByIds(ids));
    }

    @GetMapping(value = "/code")
    public Object twoCode(HttpServletResponse response) throws IOException {
        JSONObject data = new JSONObject();
        String accessToken = null;
        try {
            JSONObject parmData = new JSONObject();
            parmData.put("scene", ShiroUtils.getUserId());
            parmData.put("url", "");

            // 提交 提交使用
            String parm = parmData.toJSONString();
            accessToken = configService.getLeDaoPanDaAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                throw new RuntimeException("获取accessToken失败");
            }
            data.put("twoCodeUrl", WxQrCode.getWxQrCode(accessToken, FileUploadUtils.getDefaultBaseDir(), StringUtils.replaceBlank(ShiroUtils.getSysUser().getUserName()), parm, response));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
