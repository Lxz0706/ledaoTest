package com.ledao.web.controller.system;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.qrCode.WxQrCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysTrainAdmin;
import com.ledao.system.service.ISysTrainAdminService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签到管理Controller
 *
 * @author lxz
 * @date 2021-08-29
 */
@Controller
@RequestMapping("/system/train")
public class SysTrainAdminController extends BaseController
{
    private String prefix = "system/trainadmin";

    @Autowired
    private ISysTrainAdminService sysTrainAdminService;

//    @RequiresPermissions("system:train:view")
    @GetMapping()
    public String train()
    {
        return prefix + "/trainAdmin";
    }

    /**
     * 查询签到管理列表
     */
//    @RequiresPermissions("system:train:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysTrainAdmin sysTrainAdmin)
    {
        startPage();
        List<SysTrainAdmin> list = sysTrainAdminService.selectSysTrainAdminList(sysTrainAdmin);
        return getDataTable(list);
    }

    /**
     * 导出签到管理列表
     */
//    @RequiresPermissions("system:train:export")
    @Log(title = "签到管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysTrainAdmin sysTrainAdmin)
    {
        List<SysTrainAdmin> list = sysTrainAdminService.selectSysTrainAdminList(sysTrainAdmin);
        ExcelUtil<SysTrainAdmin> util = new ExcelUtil<SysTrainAdmin>(SysTrainAdmin.class);
        return util.exportExcel(list, "train");
    }

    /**
     * 新增签到管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    @GetMapping("/goTrainUser")
    public String goTrainUser(@RequestParam("trainId")Long trainId, ModelMap mmap)
    {
        mmap.put("trainId",trainId);
        return prefix + "/trainUser";
    }

    /**
     * 新增保存签到管理
     */
//    @RequiresPermissions("system:train:add")
    @Log(title = "签到管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysTrainAdmin sysTrainAdmin)
    {
        return toAjax(sysTrainAdminService.insertSysTrainAdmin(sysTrainAdmin));
    }

    /**
     * 修改签到管理
     */
    @GetMapping("/edit/{trainId}")
    public String edit(@PathVariable("trainId") Long trainId, ModelMap mmap)
    {
        SysTrainAdmin sysTrainAdmin = sysTrainAdminService.selectSysTrainAdminById(trainId);
        mmap.put("sysTrainAdmin", sysTrainAdmin);
        return prefix + "/edit";
    }

    /**
     * 修改保存签到管理
     */
//    @RequiresPermissions("system:train:edit")
    @Log(title = "签到管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysTrainAdmin sysTrainAdmin)
    {
        return toAjax(sysTrainAdminService.updateSysTrainAdmin(sysTrainAdmin));
    }

    /**
     * 接收二维码
     * @return
     * @throws IOException
     */
    @GetMapping(value="/code")
    public Object twoCode(Long trainId, HttpServletResponse response) throws IOException {
        JSONObject data=new JSONObject();
        String accessToken = null;
        try{
            JSONObject parmData = new JSONObject();
            parmData.put("scene","trainId="+trainId);
            parmData.put("url","");
            String parm = parmData.toJSONString();
            accessToken = WxQrCode.getAccessToken(WeChatConstants.WXAPPID,WeChatConstants.WXSECRET);
            System.out.println("accessToken;"+accessToken);
            String twoCodeUrl = WxQrCode.getminiqrQr(accessToken, FileUploadUtils.getDefaultBaseDir(),response,parm);
            data.put("twoCodeUrl", twoCodeUrl);
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除签到管理
     */
//    @RequiresPermissions("system:train:remove")
    @Log(title = "签到管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysTrainAdminService.deleteSysTrainAdminByIds(ids));
    }
}
