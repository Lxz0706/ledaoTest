package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysRecapture;

/**
 * 投后项目现金回现Mapper接口
 *
 * @author lxz
 * @date 2020-11-23
 */
public interface SysRecaptureMapper {
    /**
     * 查询投后项目现金回现
     *
     * @param recaptureId 投后项目现金回现ID
     * @return 投后项目现金回现
     */
    public SysRecapture selectSysRecaptureById(Long recaptureId);

    /**
     * 查询投后项目现金回现列表
     *
     * @param sysRecapture 投后项目现金回现
     * @return 投后项目现金回现集合
     */
    public List<SysRecapture> selectSysRecaptureList(SysRecapture sysRecapture);

    /**
     * 新增投后项目现金回现
     *
     * @param sysRecapture 投后项目现金回现
     * @return 结果
     */
    public int insertSysRecapture(SysRecapture sysRecapture);

    /**
     * 修改投后项目现金回现
     *
     * @param sysRecapture 投后项目现金回现
     * @return 结果
     */
    public int updateSysRecapture(SysRecapture sysRecapture);

    /**
     * 删除投后项目现金回现
     *
     * @param recaptureId 投后项目现金回现ID
     * @return 结果
     */
    public int deleteSysRecaptureById(Long recaptureId);

    /**
     * 批量删除投后项目现金回现
     *
     * @param recaptureIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRecaptureByIds(String[] recaptureIds);

    /**
     * 根据项目id查询
     *
     * @param projectId
     * @return 结果
     */
    public List<SysRecapture> selectSysRecaptureByProjectId(Long projectId);

    /**
     * 根据projectId查询出总的现金回现金额
     *
     * @param projectId
     * @return
     */
    public SysRecapture selectTotalRecaptureByProjectId(Long projectId);

    public SysRecapture selectSysRecapture(String zckIds);
}
