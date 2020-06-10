package com.ledao.system.service;

import java.util.List;

import com.ledao.system.domain.SysUser;
import com.ledao.system.domain.SysZck;

/**
 * 资产信息库Service接口
 *
 * @author ledao
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
    public String importZck(List<SysZck> zckList, Boolean isUpdateSupport, String operName);
}
