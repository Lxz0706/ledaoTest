package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysMonomerLaw;

/**
 * 项目法律信息Mapper接口
 *
 * @author lxz
 * @date 2021-08-23
 */
public interface SysMonomerLawMapper {
    /**
     * 查询项目法律信息
     *
     * @param monomerLawId 项目法律信息ID
     * @return 项目法律信息
     */
    public SysMonomerLaw selectSysMonomerLawById(Long monomerLawId);

    /**
     * 查询项目法律信息列表
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 项目法律信息集合
     */
    public List<SysMonomerLaw> selectSysMonomerLawList(SysMonomerLaw sysMonomerLaw);

    /**
     * 新增项目法律信息
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 结果
     */
    public int insertSysMonomerLaw(SysMonomerLaw sysMonomerLaw);

    /**
     * 修改项目法律信息
     *
     * @param sysMonomerLaw 项目法律信息
     * @return 结果
     */
    public int updateSysMonomerLaw(SysMonomerLaw sysMonomerLaw);

    /**
     * 删除项目法律信息
     *
     * @param monomerLawId 项目法律信息ID
     * @return 结果
     */
    public int deleteSysMonomerLawById(Long monomerLawId);

    /**
     * 批量删除项目法律信息
     *
     * @param monomerLawIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMonomerLawByIds(String[] monomerLawIds);
}
