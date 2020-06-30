package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysZcbMapper;
import com.ledao.system.domain.SysZcb;
import com.ledao.system.service.ISysZcbService;
import com.ledao.common.core.text.Convert;

/**
 * 资产包Service业务层处理
 *
 * @author lxz
 * @date 2020-06-11
 */
@Service
public class SysZcbServiceImpl implements ISysZcbService {
    @Autowired
    private SysZcbMapper sysZcbMapper;

    /**
     * 查询资产包
     *
     * @param id 资产包ID
     * @return 资产包
     */
    @Override
    public SysZcb selectSysZcbById(Long id) {
        return sysZcbMapper.selectSysZcbById(id);
    }

    /**
     * 查询资产包列表
     *
     * @param sysZcb 资产包
     * @return 资产包
     */
    @Override
    public List<SysZcb> selectSysZcbList(SysZcb sysZcb) {
        return sysZcbMapper.selectSysZcbList(sysZcb);
    }

    /**
     * 新增资产包
     *
     * @param sysZcb 资产包
     * @return 结果
     */
    @Override
    public int insertSysZcb(SysZcb sysZcb) {
        sysZcb.setCreateTime(DateUtils.getNowDate());
        return sysZcbMapper.insertSysZcb(sysZcb);
    }

    /**
     * 修改资产包
     *
     * @param sysZcb 资产包
     * @return 结果
     */
    @Override
    public int updateSysZcb(SysZcb sysZcb) {
        sysZcb.setUpdateTime(DateUtils.getNowDate());
        return sysZcbMapper.updateSysZcb(sysZcb);
    }

    /**
     * 删除资产包对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysZcbByIds(String ids) {
        return sysZcbMapper.deleteSysZcbByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资产包信息
     *
     * @param id 资产包ID
     * @return 结果
     */
    @Override
    public int deleteSysZcbById(Long id) {
        return sysZcbMapper.deleteSysZcbById(id);
    }

    /**
     * 根据资产包状态查询
     */
    public List<SysZcb> selectZcbByAssetStatus() {
        return sysZcbMapper.selectZcbByAssetStatus();
    }


}
