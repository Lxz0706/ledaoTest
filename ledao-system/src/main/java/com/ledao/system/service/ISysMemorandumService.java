package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysMemorandum;

/**
 * 备忘录Service接口
 *
 * @author lxz
 * @date 2021-03-04
 */
public interface ISysMemorandumService {
    /**
     * 查询备忘录
     *
     * @param memorandumId 备忘录ID
     * @return 备忘录
     */
    public SysMemorandum selectSysMemorandumById(Long memorandumId);

    /**
     * 查询备忘录列表
     *
     * @param sysMemorandum 备忘录
     * @return 备忘录集合
     */
    public List<SysMemorandum> selectSysMemorandumList(SysMemorandum sysMemorandum);

    /**
     * 新增备忘录
     *
     * @param sysMemorandum 备忘录
     * @return 结果
     */
    public int insertSysMemorandum(SysMemorandum sysMemorandum);

    /**
     * 修改备忘录
     *
     * @param sysMemorandum 备忘录
     * @return 结果
     */
    public int updateSysMemorandum(SysMemorandum sysMemorandum);

    /**
     * 批量删除备忘录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMemorandumByIds(String ids);

    /**
     * 删除备忘录信息
     *
     * @param memorandumId 备忘录ID
     * @return 结果
     */
    public int deleteSysMemorandumById(Long memorandumId);
}
