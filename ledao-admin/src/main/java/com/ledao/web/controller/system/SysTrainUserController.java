package com.ledao.web.controller.system;

import java.util.List;
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
import com.ledao.system.dao.SysTrainUser;
import com.ledao.system.service.ISysTrainUserService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 签到人员Controller
 * 
 * @author lxz
 * @date 2021-08-29
 */
@Controller
@RequestMapping("/system/trainuser")
public class SysTrainUserController extends BaseController
{
    private String prefix = "system/trainuser";

    @Autowired
    private ISysTrainUserService sysTrainUserService;

    @RequiresPermissions("system:trainuser:view")
    @GetMapping()
    public String trainuser()
    {
        return prefix + "/trainuser";
    }

    /**
     * 查询签到人员列表
     */
    @RequiresPermissions("system:trainuser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysTrainUser sysTrainUser)
    {
        startPage();
        List<SysTrainUser> list = sysTrainUserService.selectSysTrainUserList(sysTrainUser);
        return getDataTable(list);
    }


/*
    //    @Value("${ma.appid}")
    private String APIKEY="wxab03e9b278534a80";//小程序id
    //    @Value("${ma.secret}")
    private String SECRETKEY="b992f830ec418093323d487ddd109e9f";//小程序密钥
    @Value("${file.path.upload}")
    private String uploadPath;

    *//**
     * 接收二维码
     * @param request
     * @return
     * @throws IOException
     *//*
    @GetMapping(value="/code")
    public Object twoCode(HttpServletRequest request) throws IOException {
        JSONObject data=new JSONObject();
        String accessToken = null;
        try{
            accessToken = WxQrCode.getAccessToken(APIKEY,SECRETKEY);
            System.out.println("accessToken;"+accessToken);
            String twoCodeUrl = WxQrCode.getminiqrQr(accessToken,uploadPath,request);
            data.put("twoCodeUrl", twoCodeUrl);
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * 导出签到人员列表
     */
    @RequiresPermissions("system:trainuser:export")
    @Log(title = "签到人员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysTrainUser sysTrainUser)
    {
        List<SysTrainUser> list = sysTrainUserService.selectSysTrainUserList(sysTrainUser);
        ExcelUtil<SysTrainUser> util = new ExcelUtil<SysTrainUser>(SysTrainUser.class);
        return util.exportExcel(list, "trainuser");
    }

    /**
     * 新增签到人员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存签到人员
     */
    @RequiresPermissions("system:trainuser:add")
    @Log(title = "签到人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysTrainUser sysTrainUser)
    {
        return toAjax(sysTrainUserService.insertSysTrainUser(sysTrainUser));
    }

    /**
     * 修改签到人员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysTrainUser sysTrainUser = sysTrainUserService.selectSysTrainUserById(id);
        mmap.put("sysTrainUser", sysTrainUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存签到人员
     */
    @RequiresPermissions("system:trainuser:edit")
    @Log(title = "签到人员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysTrainUser sysTrainUser)
    {
        return toAjax(sysTrainUserService.updateSysTrainUser(sysTrainUser));
    }

    /**
     * 删除签到人员
     */
    @RequiresPermissions("system:trainuser:remove")
    @Log(title = "签到人员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysTrainUserService.deleteSysTrainUserByIds(ids));
    }
}
