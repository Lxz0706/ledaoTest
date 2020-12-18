package com.ledao.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysBusinesscard;
import com.ledao.system.service.ISysBusinesscardService;
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
import com.ledao.system.dao.SysNamecard;
import com.ledao.system.service.ISysNamecardService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 名片Controller
 *
 * @author lxz
 * @date 2020-12-07
 */
@Controller
@RequestMapping("/system/namecard")
public class SysNamecardController extends BaseController {
    private String prefix = "system/namecard";

    @Autowired
    private ISysNamecardService sysNamecardService;

    @Autowired
    private ISysBusinesscardService sysBusinesscardService;

    @RequiresPermissions("system:namecard:view")
    @GetMapping()
    public String namecard() {
        return prefix + "/namecard";
    }

    /**
     * 查询名片列表
     */
    @RequiresPermissions("system:namecard:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysNamecard sysNamecard) {
        SysBusinesscard sysBusinesscard = new SysBusinesscard();
        List<SysBusinesscard> sysBusinesscardList = sysBusinesscardService.selectSysBusinesscardList(sysBusinesscard);
        for (SysBusinesscard sysBussinessCard : sysBusinesscardList) {
            JSONObject jsonStr = JSONObject.parseObject(sysBussinessCard.getCardData());
            JSONObject jsonobject = JSONObject.parseObject(jsonStr.toString());
            SysNamecard sysNamecard1 = new SysNamecard();
            sysNamecard1.setCardName(jsonobject.getString("姓名"));
            sysNamecard1.setPosition(jsonobject.getString("职位"));
            sysNamecard1.setCompany(jsonobject.getString("公司"));
            sysNamecard1.setAddress(jsonobject.getString("地址"));
            sysNamecard1.setPhoneNumber(jsonobject.getString("手机"));
            sysNamecard1.setTelephone(jsonobject.getString("电话"));
            sysNamecard1.setWechat(jsonobject.getString("微信"));
            sysNamecard1.setAvatar(sysBussinessCard.getCardBase64());
            sysNamecard1.setCreateBy(ShiroUtils.getLoginName());
            SysNamecard sysNamecard2 = new SysNamecard();
            sysNamecard2.setAvatar(sysNamecard1.getAvatar());
            List<SysNamecard> sysNamecardList = sysNamecardService.selectSysNamecardList(sysNamecard2);
            if (sysNamecardList.size() <= 0) {
                sysNamecardService.insertSysNamecard(sysNamecard1);
            }
        }
        startPage();
        List<SysNamecard> list = sysNamecardService.selectSysNamecardList(sysNamecard);
        return getDataTable(list);
    }

    /**
     * 导出名片列表
     */
    @RequiresPermissions("system:namecard:export")
    @Log(title = "名片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysNamecard sysNamecard) {
        String ids = getRequest().getParameter("ids");
        List<SysNamecard> list = new ArrayList<>();
        if (StringUtils.isEmpty(ids)) {
            list = sysNamecardService.selectSysNamecardList(sysNamecard);
        } else {
            list = sysNamecardService.selectSysNamecardListByCardId(ids);
        }

        ExcelUtil<SysNamecard> util = new ExcelUtil<SysNamecard>(SysNamecard.class);
        return util.exportExcel(list, "名片");
    }

    /**
     * 新增名片
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存名片
     */
    @RequiresPermissions("system:namecard:add")
    @Log(title = "名片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysNamecard sysNamecard) {
        return toAjax(sysNamecardService.insertSysNamecard(sysNamecard));
    }

    /**
     * 修改名片
     */
    @GetMapping("/edit/{cardId}")
    public String edit(@PathVariable("cardId") Long cardId, ModelMap mmap) {
        SysNamecard sysNamecard = sysNamecardService.selectSysNamecardById(cardId);
        sysNamecard.setAvatar("data:image/png;base64," + sysNamecard.getAvatar());
        mmap.put("sysNamecard", sysNamecard);
        return prefix + "/edit";
    }

    /**
     * 修改保存名片
     */
    @RequiresPermissions("system:namecard:edit")
    @Log(title = "名片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysNamecard sysNamecard) {
        return toAjax(sysNamecardService.updateSysNamecard(sysNamecard));
    }

    /**
     * 删除名片
     */
    @RequiresPermissions("system:namecard:remove")
    @Log(title = "名片", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysNamecardService.deleteSysNamecardByIds(ids));
    }
}
