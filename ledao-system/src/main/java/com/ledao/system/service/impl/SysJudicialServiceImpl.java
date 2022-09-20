package com.ledao.system.service.impl;

import java.util.List;

import com.github.pagehelper.Page;
import com.ledao.common.annotation.DataSource;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.enums.DataSourceType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysJudicialMapper;
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
import com.ledao.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 司法拍卖项目Service业务层处理
 *
 * @author ledao
 * @date 2020-09-14
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class SysJudicialServiceImpl implements ISysJudicialService {
    @Autowired
    private SysJudicialMapper sysJudicialMapper;

    /**
     * 查询司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    @Override
    public SysJudicial selectSysJudicialById(String itemId) {
        return sysJudicialMapper.selectSysJudicialById(itemId);
    }

    /**
     * 查询司法拍卖项目列表
     *
     * @param sysJudicial 司法拍卖项目
     * @return 司法拍卖项目
     */
    @Override
    public List<SysJudicial> selectSysJudicialList(SysJudicial sysJudicial) {
        //PageDao pageDomain = TableSupport.buildPageRequest();
        //Integer pageNum = pageDomain.getPageNum();
        //Integer pageSize = pageDomain.getPageSize();
        //Page<SysJudicial> list = (Page<SysJudicial>) sysJudicialMapper.selectSysJudicialList(sysJudicial);
        //Page<SysJudicial> returnList = new Page<>();
        //for (SysJudicial sysJudicial1 : list) {
        //    returnList.add(sysJudicial1);
        //}
        //returnList.setTotal(CollectionUtils.isEmpty(list) ? 0 : list.getTotal());
        //returnList.setPageNum(pageNum);
        //returnList.setPageSize(pageSize);
        return sysJudicialMapper.selectSysJudicialList(sysJudicial);
    }

    /**
     * 新增司法拍卖项目
     *
     * @param sysJudicial 司法拍卖项目
     * @return 结果
     */
    @Override
    public int insertSysJudicial(SysJudicial sysJudicial) {
        return sysJudicialMapper.insertSysJudicial(sysJudicial);
    }

    /**
     * 修改司法拍卖项目
     *
     * @param sysJudicial 司法拍卖项目
     * @return 结果
     */
    @Override
    public int updateSysJudicial(SysJudicial sysJudicial) {
        return sysJudicialMapper.updateSysJudicial(sysJudicial);
    }

    /**
     * 删除司法拍卖项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialByIds(String ids) {
        return sysJudicialMapper.deleteSysJudicialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除司法拍卖项目信息
     *
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialById(Long id) {
        return sysJudicialMapper.deleteSysJudicialById(id);
    }


    /**
     * 查询总量
     *
     * @param sysJudicial
     * @return 结果
     */
    @Override
    public Long selectSysJudicialListTotal(SysJudicial sysJudicial) {
        return sysJudicialMapper.selectSysJudicialListTotal(sysJudicial);
    }

}
