package com.ledao.system.service;

import java.util.List;
import com.ledao.system.dao.wenShu;

/**
 * 文书网Service接口
 * 
 * @author lxz
 * @date 2022-12-14
 */
public interface IwenShuService 
{
    /**
     * 查询文书网
     * 
     * @param courtId 文书网ID
     * @return 文书网
     */
    public wenShu selectwenShuById(String courtId);

    /**
     * 查询文书网列表
     * 
     * @param wenShu 文书网
     * @return 文书网集合
     */
    public List<wenShu> selectwenShuList(wenShu wenShu);

    /**
     * 新增文书网
     * 
     * @param wenShu 文书网
     * @return 结果
     */
    public int insertwenShu(wenShu wenShu);

    /**
     * 修改文书网
     * 
     * @param wenShu 文书网
     * @return 结果
     */
    public int updatewenShu(wenShu wenShu);

    /**
     * 批量删除文书网
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletewenShuByIds(String ids);

    /**
     * 删除文书网信息
     * 
     * @param courtId 文书网ID
     * @return 结果
     */
    public int deletewenShuById(String courtId);
}
