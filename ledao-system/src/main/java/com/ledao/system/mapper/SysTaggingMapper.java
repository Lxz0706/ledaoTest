package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysTagging;

/**
 * 星标库Mapper接口
 *
 * @author lxz
 * @date 2020-11-03
 */
public interface SysTaggingMapper {
    /**
     * 查询星标库
     *
     * @param id 星标库ID
     * @return 星标库
     */
    public SysTagging selectSysTaggingById(Long id);

    /**
     * 查询星标库列表
     *
     * @param sysTagging 星标库
     * @return 星标库集合
     */
    public List<SysTagging> selectSysTaggingList(SysTagging sysTagging);

    /**
     * 新增星标库
     *
     * @param sysTagging 星标库
     * @return 结果
     */
    public int insertSysTagging(SysTagging sysTagging);

    /**
     * 修改星标库
     *
     * @param sysTagging 星标库
     * @return 结果
     */
    public int updateSysTagging(SysTagging sysTagging);

    /**
     * 删除星标库
     *
     * @param id 星标库ID
     * @return 结果
     */
    public int deleteSysTaggingById(Long id);

    /**
     * 批量删除星标库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTaggingByIds(String[] ids);

    /**
     * 根据爬虫表主键查询
     *
     * @param judicialId
     * @return 结果
     */
    public SysTagging selectSysTaggingByJudicialId(Long judicialId);
}
