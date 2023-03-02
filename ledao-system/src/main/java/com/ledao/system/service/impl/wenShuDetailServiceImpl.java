package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.wenShuDetailMapper;
import com.ledao.system.dao.wenShuDetail;
import com.ledao.system.service.IwenShuDetailService;
import com.ledao.common.core.text.Convert;

/**
 * 文书网数据Service业务层处理
 *
 * @author lxz
 * @date 2022-12-14
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class wenShuDetailServiceImpl implements IwenShuDetailService {
    @Autowired
    private wenShuDetailMapper wenShuDetailMapper;

    /**
     * 查询文书网数据
     *
     * @param itemCourtId 文书网数据ID
     * @return 文书网数据
     */
    @Override
    public wenShuDetail selectwenShuDetailById(String itemCourtId) {
        return wenShuDetailMapper.selectwenShuDetailById(itemCourtId);
    }

    /**
     * 查询文书网数据列表
     *
     * @param wenShuDetail 文书网数据
     * @return 文书网数据
     */
    @Override
    public List<wenShuDetail> selectwenShuDetailList(wenShuDetail wenShuDetail) {
        return wenShuDetailMapper.selectwenShuDetailList(wenShuDetail);
    }

    /**
     * 新增文书网数据
     *
     * @param wenShuDetail 文书网数据
     * @return 结果
     */
    @Override
    public int insertwenShuDetail(wenShuDetail wenShuDetail) {
        return wenShuDetailMapper.insertwenShuDetail(wenShuDetail);
    }

    /**
     * 修改文书网数据
     *
     * @param wenShuDetail 文书网数据
     * @return 结果
     */
    @Override
    public int updatewenShuDetail(wenShuDetail wenShuDetail) {
        return wenShuDetailMapper.updatewenShuDetail(wenShuDetail);
    }

    /**
     * 删除文书网数据对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletewenShuDetailByIds(String ids) {
        return wenShuDetailMapper.deletewenShuDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文书网数据信息
     *
     * @param itemCourtId 文书网数据ID
     * @return 结果
     */
    @Override
    public int deletewenShuDetailById(String itemCourtId) {
        return wenShuDetailMapper.deletewenShuDetailById(itemCourtId);
    }

    /**
     * 查询文书网数据list
     *
     * @param wenShuDetail
     * @return
     */
    @Override
    public List<wenShuDetail> selectItemCourtIdList(wenShuDetail wenShuDetail) {
        return wenShuDetailMapper.selectItemCourtIdList(wenShuDetail);
    }
}
