package com.ledao.system.mapper;

import com.ledao.system.dao.SysReshuffle;

import java.util.List;

/**
 * 员工岗位异动Mapper接口
 *
 * @author lxz
 * @date 2024-03-19
 */
public interface SysReshuffleMapper {
    /**
     * 查询员工岗位异动
     *
     * @param reshuffleId 员工岗位异动ID
     * @return 员工岗位异动
     */
    public SysReshuffle selectSysReshuffleById(Long reshuffleId);

    /**
     * 查询员工岗位异动列表
     *
     * @param sysReshuffle 员工岗位异动
     * @return 员工岗位异动集合
     */
    public List<SysReshuffle> selectSysReshuffleList(SysReshuffle sysReshuffle);

    /**
     * 新增员工岗位异动
     *
     * @param sysReshuffle 员工岗位异动
     * @return 结果
     */
    public int insertSysReshuffle(SysReshuffle sysReshuffle);

    /**
     * 修改员工岗位异动
     *
     * @param sysReshuffle 员工岗位异动
     * @return 结果
     */
    public int updateSysReshuffle(SysReshuffle sysReshuffle);

    /**
     * 删除员工岗位异动
     *
     * @param reshuffleId 员工岗位异动ID
     * @return 结果
     */
    public int deleteSysReshuffleById(Long reshuffleId);

    /**
     * 批量删除员工岗位异动
     *
     * @param reshuffleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysReshuffleByIds(String[] reshuffleIds);

    /**
     * 根据员工id查询异动信息
     *
     * @param staffId
     * @return
     */
    public List<SysReshuffle> selectReshuffleByStaffId(Long staffId);
}
