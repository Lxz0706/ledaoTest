package com.ledao.activity.controller;

import com.github.pagehelper.Page;
import com.ledao.activity.dao.ActIdGroup;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.utils.StringUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程用户组Controller
 *
 * @author Xianlu Tech
 * @date 2019-10-02
 */
@Controller
@RequestMapping("/group")
public class ActIdGroupController extends BaseController {
    private String prefix = "group";

    @Autowired
    private IdentityService identityService;

    @GetMapping()
    public String group() {
        return prefix + "/group";
    }

    /**
     * 查询流程用户组列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ActIdGroup query) {
        PageDao pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StringUtils.isNotBlank(query.getId())) {
            groupQuery.groupId(query.getId());
        }
        if (StringUtils.isNotBlank(query.getName())) {
            groupQuery.groupNameLike("%" + query.getName() + "%");
        }
        List<Group> groupList = groupQuery.listPage((pageNum - 1) * pageSize, pageSize);
        Page<ActIdGroup> list = new Page<>();
        list.setTotal(groupQuery.count());
        list.setPageNum(pageNum);
        list.setPageSize(pageSize);
        for (Group group : groupList) {
            ActIdGroup idGroup = new ActIdGroup();
            idGroup.setId(group.getId());
            idGroup.setName(group.getName());
            list.add(idGroup);
        }
        return getDataTable(list);
    }

}
