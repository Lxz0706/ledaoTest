package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.domain.SysBgczzck;

/**
 * 重组并购项目信息库Mapper接口
 *
 * @author lxz
 * @date 2020-06-16
 */
public interface SysBgczzckMapper {
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
     * 删除重组并购项目信息库
     *
     * @param id 重组并购项目信息库ID
     * @return 结果
     */
    public int deleteSysBgczzckById(Long id);

    /**
     * 批量删除重组并购项目信息库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBgczzckByIds(String[] ids);

    /**
     * 根据项目状态查询项目
     */
    public List<SysBgczzck> selectByProjectStatus();
}
