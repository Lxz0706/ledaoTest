package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysTaggingMapper;
import com.ledao.system.dao.SysTagging;
import com.ledao.system.service.ISysTaggingService;
import com.ledao.common.core.text.Convert;

/**
 * 星标库Service业务层处理
 *
 * @author lxz
 * @date 2020-11-03
 */
@Service
@DataSource(value = DataSourceType.SLAVE1)
public class SysTaggingServiceImpl implements ISysTaggingService {
    @Autowired
    private SysTaggingMapper sysTaggingMapper;

    /**
     * 查询星标库
     *
     * @param id 星标库ID
     * @return 星标库
     */
    @Override
    public SysTagging selectSysTaggingById(Long id) {
        return sysTaggingMapper.selectSysTaggingById(id);
    }

    /**
     * 查询星标库列表
     *
     * @param sysTagging 星标库
     * @return 星标库
     */
    @Override
    public List<SysTagging> selectSysTaggingList(SysTagging sysTagging) {
        return sysTaggingMapper.selectSysTaggingList(sysTagging);
    }

    /**
     * 新增星标库
     *
     * @param sysTagging 星标库
     * @return 结果
     */
    @Override
    public int insertSysTagging(SysTagging sysTagging) {
        return sysTaggingMapper.insertSysTagging(sysTagging);
    }

    /**
     * 修改星标库
     *
     * @param sysTagging 星标库
     * @return 结果
     */
    @Override
    public int updateSysTagging(SysTagging sysTagging) {
        return sysTaggingMapper.updateSysTagging(sysTagging);
    }

    /**
     * 删除星标库对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTaggingByIds(String ids) {
        return sysTaggingMapper.deleteSysTaggingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除星标库信息
     *
     * @param id 星标库ID
     * @return 结果
     */
    @Override
    public int deleteSysTaggingById(Long id) {
        return sysTaggingMapper.deleteSysTaggingById(id);
    }

    /**
     * 根据爬虫表主键查询
     *
     * @param judicialId
     * @return 结果
     */
    @Override
    public SysTagging selectSysTaggingByJudicialId(Long judicialId) {
        return sysTaggingMapper.selectSysTaggingByJudicialId(judicialId);
    }

    /**
     * 根据爬虫表主键查询
     *
     * @param itemId
     * @return 结果
     */
    @Override
    public SysTagging selectSysTaggingByItemId(String itemId) {
        return sysTaggingMapper.selectSysTaggingByItemId(itemId);
    }
}
