package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.GisUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lxz
 * @date 2022/8/16
 */
@Controller
@RequestMapping("/system/valuationModel")
public class SysValuationModelController extends BaseController {
    private String prefix = "system/valuationModel";

    @Autowired
    private ISysJudicialService sysJudicialService;

    @GetMapping()
    public String valuationmap() {
        return prefix + "/valuationModel";
    }

    @Log(title = "估值计算", businessType = BusinessType.OTHER)
    @GetMapping("/valuationModel")
    public String valuationModel(String itemId, String latLon, ModelMap modelMap) {
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
        modelMap.put("latLon", latLon);
        return prefix + "/valuationModel";
    }

}
