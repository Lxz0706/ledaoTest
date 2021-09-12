package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysZcb;

/**
 * 资产包Mapper接口
 *
 * @author lxz
 * @date 2020-06-11
 */
public interface SysZcbMapper {
    /**
     * 查询资产包
     *
     * @param id 资产包ID
     * @return 资产包
     */
    public SysZcb selectSysZcbById(Long id);

    /**
     * 查询资产包列表
     *
     * @param sysZcb 资产包
     * @return 资产包集合
     */
    public List<SysZcb> selectSysZcbList(SysZcb sysZcb);

    /**
     * 新增资产包
     *
     * @param sysZcb 资产包
     * @return 结果
     */
    public int insertSysZcb(SysZcb sysZcb);

    /**
     * 修改资产包
     *
     * @param sysZcb 资产包
     * @return 结果
     */
    public int updateSysZcb(SysZcb sysZcb);

    /**
     * 删除资产包
     *
     * @param id 资产包ID
     * @return 结果
     */
    public int deleteSysZcbById(Long id);

    /**
     * 批量删除资产包
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysZcbByIds(String[] ids);

    /**
     * 根据资产包状态查询
     */

    public List<SysZcb> selectZcbByAssetStatus();

    /**
     * 根据类型查询
     */
    public List<SysZcb> selectZcbList(SysZcb sysZcb);

    List<SysZcb> selectZcbListUseful(SysZcb sysZcb);

    List<SysZcb> selectSysZcbListNoLike(SysZcb sysZcb);
}
