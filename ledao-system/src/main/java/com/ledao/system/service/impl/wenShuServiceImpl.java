package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.wenShuMapper;
import com.ledao.system.dao.wenShu;
import com.ledao.system.service.IwenShuService;
import com.ledao.common.core.text.Convert;

/**
 * 文书网Service业务层处理
 * 
 * @author lxz
 * @date 2022-12-14
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class wenShuServiceImpl implements IwenShuService 
{
    @Autowired
    private wenShuMapper wenShuMapper;

    /**
     * 查询文书网
     * 
     * @param courtId 文书网ID
     * @return 文书网
     */
    @Override
    public wenShu selectwenShuById(String courtId)
    {
        return wenShuMapper.selectwenShuById(courtId);
    }

    /**
     * 查询文书网列表
     * 
     * @param wenShu 文书网
     * @return 文书网
     */
    @Override
    public List<wenShu> selectwenShuList(wenShu wenShu)
    {
        return wenShuMapper.selectwenShuList(wenShu);
    }

    /**
     * 新增文书网
     * 
     * @param wenShu 文书网
     * @return 结果
     */
    @Override
    public int insertwenShu(wenShu wenShu)
    {
        return wenShuMapper.insertwenShu(wenShu);
    }

    /**
     * 修改文书网
     * 
     * @param wenShu 文书网
     * @return 结果
     */
    @Override
    public int updatewenShu(wenShu wenShu)
    {
        return wenShuMapper.updatewenShu(wenShu);
    }

    /**
     * 删除文书网对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletewenShuByIds(String ids)
    {
        return wenShuMapper.deletewenShuByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文书网信息
     * 
     * @param courtId 文书网ID
     * @return 结果
     */
    @Override
    public int deletewenShuById(String courtId)
    {
        return wenShuMapper.deletewenShuById(courtId);
    }
}
