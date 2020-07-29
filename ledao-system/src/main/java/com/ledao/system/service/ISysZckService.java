package com.ledao.system.service;

import java.util.List;

import com.ledao.system.domain.SysUser;
import com.ledao.system.domain.SysZck;

/**
 * 资产信息库Service接口
 *
 * @author lxz
 * @date 2020-06-09
 */
public interface ISysZckService {
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
     * 批量删除资产信息库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysZckByIds(String ids);

    /**
     * 删除资产信息库信息
     *
     * @param id 资产信息库ID
     * @return 结果
     */
    public int deleteSysZckById(Long id);

    /**
     * 导入用户数据
     *
     * @param zckList         用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importZck(List<SysZck> zckList, Boolean isUpdateSupport, String operName, Long zcbId);

    /**
     * 根据资产包ID查询资产库信息
     *
     * @param zcbId
     * @return 结果
     */
    public List<SysZck> selectSysZckByZcbId(Long zcbId);


    /**
     * 根据借款人名称分
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
    public List<SysZck> selectSysZckByZckId(String zckIds);

    /**
     * 查询所有数据
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> queryAll(SysZck sysZck);
}
