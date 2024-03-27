package com.ledao.system.service.impl;

import com.ledao.common.core.text.Convert;
import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysReshuffle;
import com.ledao.system.mapper.SysReshuffleMapper;
import com.ledao.system.service.ISysReshuffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工岗位异动Service业务层处理
 *
 * @author lxz
 * @date 2024-03-19
 */
@Service
public class SysReshuffleServiceImpl implements ISysReshuffleService {
    @Autowired
    private SysReshuffleMapper sysReshuffleMapper;

    /**
     * 查询员工岗位异动
     *
     * @param reshuffleId 员工岗位异动ID
     * @return 员工岗位异动
     */
    @Override
    public SysReshuffle selectSysReshuffleById(Long reshuffleId) {
        return sysReshuffleMapper.selectSysReshuffleById(reshuffleId);
    }

    /**
     * 查询员工岗位异动列表
     *
     * @param sysReshuffle 员工岗位异动
     * @return 员工岗位异动
     */
    @Override
    public List<SysReshuffle> selectSysReshuffleList(SysReshuffle sysReshuffle) {
        return sysReshuffleMapper.selectSysReshuffleList(sysReshuffle);
    }

    /**
     * 新增员工岗位异动
     *
     * @param sysReshuffle 员工岗位异动
     * @return 结果
     */
    @Override
    public int insertSysReshuffle(SysReshuffle sysReshuffle) {
        return sysReshuffleMapper.insertSysReshuffle(sysReshuffle);
    }

    /**
     * 修改员工岗位异动
     *
     * @param sysReshuffle 员工岗位异动
     * @return 结果
     */
    @Override
    public int updateSysReshuffle(SysReshuffle sysReshuffle) {
        sysReshuffle.setUpdateTime(DateUtils.getNowDate());
        return sysReshuffleMapper.updateSysReshuffle(sysReshuffle);
    }

    /**
     * 删除员工岗位异动对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysReshuffleByIds(String ids) {
        return sysReshuffleMapper.deleteSysReshuffleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除员工岗位异动信息
     *
     * @param reshuffleId 员工岗位异动ID
     * @return 结果
     */
    @Override
    public int deleteSysReshuffleById(Long reshuffleId) {
        return sysReshuffleMapper.deleteSysReshuffleById(reshuffleId);
    }

    /**
     * 根据员工id查询异动信息
     *
     * @param staffId
     * @return
     */
    public List<SysReshuffle> selectReshuffleByStaffId(Long staffId) {
        return sysReshuffleMapper.selectReshuffleByStaffId(staffId);
    }
}
