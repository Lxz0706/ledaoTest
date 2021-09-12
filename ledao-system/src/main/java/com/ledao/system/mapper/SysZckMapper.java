package com.ledao.system.mapper;

import com.ledao.system.dao.SysZck;

import java.util.List;

/**
 * 资产信息库Mapper接口
 *
 * @author lxz
 * @date 2020-06-09
 */
public interface SysZckMapper {
    /**
     * 查询资产信息库
     *
     * @param id 资产信息库ID
     * @return 资产信息库
     */
    public SysZck selectSysZckById(Long id);

    /**
     * 查询资产信息库列表
     *
     * @param sysZck 资产信息库
     * @return 资产信息库集合
     */
    public List<SysZck> selectSysZckList(SysZck sysZck);

    /**
     * 新增资产信息库
     *
     * @param sysZck 资产信息库
     * @return 结果
     */
    public int insertSysZck(SysZck sysZck);

    /**
     * 修改资产信息库
     *
     * @param sysZck 资产信息库
     * @return 结果
     */
    public int updateSysZck(SysZck sysZck);

    /**
     * 删除资产信息库
     *
     * @param id 资产信息库ID
     * @return 结果
     */
    public int deleteSysZckById(Long id);

    /**
     * 批量删除资产信息库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysZckByIds(String[] ids);

    /**
     * 根据资产包ID查询资产库
     */
    public List<SysZck> selectSysZckByZcbId(Long zcbId);

    /**
     * 根据zcbID查询相同借款人
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> selectSysZck(SysZck sysZck);

    /**
     * 查询出自己以及子集的数据
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> selectSysZckByParentId(SysZck sysZck);


    /**
     * 根据zckId查询结果
     *
     * @param zckIds
     * @return 结果
     */
    public List<SysZck> selectSysZckByZckId(String[] zckIds);


    /**
     * 查询所有
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> queryAll(SysZck sysZck);

    List<SysZck> selectSysZckListNoLike(SysZck sysZck);
}
