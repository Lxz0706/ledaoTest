package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.wenShuDetail;

/**
 * 文书网数据Mapper接口
 *
 * @author lxz
 * @date 2022-12-14
 */
public interface wenShuDetailMapper {
    /**
     * 查询文书网数据
     *
     * @param itemCourtId 文书网数据ID
     * @return 文书网数据
     */
    public wenShuDetail selectwenShuDetailById(String itemCourtId);

    /**
     * 查询文书网数据列表
     *
     * @param wenShuDetail 文书网数据
     * @return 文书网数据集合
     */
    public List<wenShuDetail> selectwenShuDetailList(wenShuDetail wenShuDetail);

    /**
     * 新增文书网数据
     *
     * @param wenShuDetail 文书网数据
     * @return 结果
     */
    public int insertwenShuDetail(wenShuDetail wenShuDetail);

    /**
     * 修改文书网数据
     *
     * @param wenShuDetail 文书网数据
     * @return 结果
     */
    public int updatewenShuDetail(wenShuDetail wenShuDetail);

    /**
     * 删除文书网数据
     *
     * @param itemCourtId 文书网数据ID
     * @return 结果
     */
    public int deletewenShuDetailById(String itemCourtId);

    /**
     * 批量删除文书网数据
     *
     * @param itemCourtIds 需要删除的数据ID
     * @return 结果
     */
    public int deletewenShuDetailByIds(String[] itemCourtIds);

    /**
     * 查询文书网数据list
     *
     * @param wenShuDetail
     * @return
     */
    public List<wenShuDetail> selectItemCourtIdList(wenShuDetail wenShuDetail);
}
