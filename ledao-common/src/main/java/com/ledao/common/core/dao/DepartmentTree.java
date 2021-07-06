package com.ledao.common.core.dao;

import java.util.List;

/**
 * @ClassName DepartmentTree
 * @Description TODO
 * @Author 87852
 * @Date 2021/6/30 8:53
 * @Version 1.0
 */
public class DepartmentTree {
    private Long id;
    private Long Pid;
    private String name;
    private String title;
    private List<DepartmentTree> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return Pid;
    }

    public void setPid(Long pid) {
        Pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DepartmentTree> getChildren() {
        return children;
    }

    public void setChildren(List<DepartmentTree> children) {
        this.children = children;
    }
}
