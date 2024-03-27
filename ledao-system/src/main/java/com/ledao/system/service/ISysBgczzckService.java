package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysBgczzck;
import com.ledao.system.dao.SysZck;

/**
 * 重组并购项目信息库Service接口
 *
 * @author lxz
 * @date 2020-06-16
 */
public interface ISysBgczzckService {
    /**
     * 查询重组并购项目信息库
     *
     * @param id 重组并购项目信息库ID
     * @return 重组并购项目信息库
     */
    public SysBgczzck selectSysBgczzckById(Long id);

    /**
     * 查询重组并购项目信息库列表
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 重组并购项目信息库集合
     */
    public List<SysBgczzck> selectSysBgczzckList(SysBgczzck sysBgczzck);

    /**
     * 新增重组并购项目信息库
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 结果
     */
    public int insertSysBgczzck(SysBgczzck sysBgczzck);

    /**
     * 修改重组并购项目信息库
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 结果
     */
    public int updateSysBgczzck(SysBgczzck sysBgczzck);

    /**
     * 批量删除重组并购项目信息库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBgczzckByIds(String ids);

    /**
     * 删除重组并购项目信息库信息
     *
     * @param id 重组并购项目信息库ID
     * @return 结果
     */
    public int deleteSysBgczzckById(Long id);

    /**
     * 导入用户数据
     *
     * @param sysBgczzckList  用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importBgczzk(List<SysBgczzck> sysBgczzckList, Boolean isUpdateSupport, String operName);

    /**
     * 根据项目状态查询项目
     */
    public List<SysBgczzck> selectByProjectStatus();


    /**
     * 根据id查询
     */
    public List<SysBgczzck> selectByIds(String ids);

    List<SysBgczzck> selectSysBgczzckListUseful(SysBgczzck sysBgczzck);


}
