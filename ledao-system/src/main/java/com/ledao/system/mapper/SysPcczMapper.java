package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysPccz;

/**
 * 破产网Mapper接口
 *
 * @author lxz
 * @date 2022-10-18
 */
public interface SysPcczMapper {
    /**
     * 查询破产网
     *
     * @param id 破产网ID
     * @return 破产网
     */
    public SysPccz selectSysPcczById(Long id);

    /**
     * 查询破产网列表
     *
     * @param sysPccz 破产网
     * @return 破产网集合
     */
    public List<SysPccz> selectSysPcczList(SysPccz sysPccz);

    /**
     * 新增破产网
     *
     * @param sysPccz 破产网
     * @return 结果
     */
    public int insertSysPccz(SysPccz sysPccz);

    /**
     * 修改破产网
     *
     * @param sysPccz 破产网
     * @return 结果
     */
    public int updateSysPccz(SysPccz sysPccz);

    /**
     * 删除破产网
     *
     * @param id 破产网ID
     * @return 结果
     */
    public int deleteSysPcczById(Long id);

    /**
     * 批量删除破产网
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPcczByIds(String[] ids);
}
