package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysProjectysyf;

/**
 * 流转已收已付Mapper接口
 * 
 * @author ledao
 * @date 2020-08-31
 */
public interface SysProjectysyfMapper 
{
    /**
     * 查询流转已收已付
     * 
     * @param id 流转已收已付ID
     * @return 流转已收已付
     */
    public SysProjectysyf selectSysProjectysyfById(Long id);

    /**
     * 查询流转已收已付列表
     * 
     * @param sysProjectysyf 流转已收已付
     * @return 流转已收已付集合
     */
    public List<SysProjectysyf> selectSysProjectysyfList(SysProjectysyf sysProjectysyf);

    /**
     * 新增流转已收已付
     * 
     * @param sysProjectysyf 流转已收已付
     * @return 结果
     */
    public int insertSysProjectysyf(SysProjectysyf sysProjectysyf);

    /**
     * 修改流转已收已付
     * 
     * @param sysProjectysyf 流转已收已付
     * @return 结果
     */
    public int updateSysProjectysyf(SysProjectysyf sysProjectysyf);

    /**
     * 删除流转已收已付
     * 
     * @param id 流转已收已付ID
     * @return 结果
     */
    public int deleteSysProjectysyfById(Long id);

    /**
     * 批量删除流转已收已付
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectysyfByIds(String[] ids);
}
