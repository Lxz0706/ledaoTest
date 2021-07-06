package com.ledao.common.utils;/**
 * @author 名字
 * @Title:TreeUtil
 * @ProjectName ledaoTest
 * #Descrption:
 * @date 2021/6/30 9:32
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ledao.common.core.dao.DepartmentTree;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TreeUtil
 * @Description TODO
 * @Author 87852
 * @Date 2021/6/30 9:32
 * @Version 1.0
 */
public class TreeUtil {
    private List<DepartmentTree> rootList; //根节点对象存放到这里

    private List<DepartmentTree> bodyList; //其他节点存放到这里，可以包含根节点

    public TreeUtil(List<DepartmentTree> rootList, List<DepartmentTree> bodyList) {
        this.rootList = rootList;
        this.bodyList = bodyList;
    }

    public List<DepartmentTree> getTree() {   //调用的方法入口
        if (bodyList != null && !bodyList.isEmpty()) {
            //声明一个map，用来过滤已操作过的数据
            Map<Long, Long> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree, map));//传递根对象和一个空map
            return rootList;
        }
        return null;
    }

    public void getChild(DepartmentTree beanTree, Map<Long, Long> map) {
        List<DepartmentTree> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getId()))//map内不包含子节点的code(过滤掉id和pid不含子节点的节点)
                .filter(c -> c.getPid().equals(beanTree.getId()))//子节点的父id==根节点的code 继续循环(过滤掉pid含子节点的节点)
                .forEach(c -> {
                    map.put(c.getId(), c.getPid());//当前节点code和父节点id
                    getChild(c, map);//递归调用
                    childList.add(c);
                });
        beanTree.setChildren(childList);
    }
}
