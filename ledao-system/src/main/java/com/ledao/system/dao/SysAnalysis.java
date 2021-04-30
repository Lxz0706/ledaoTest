package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系统模块分析对象 sys_analysis
 *
 * @author lxz
 * @date 2021-04-06
 */
public class SysAnalysis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long analysisId;

    /**
     * 分析名称
     */
    @Excel(name = "分析名称")
    private String analysisName;

    /**
     * 分析备注
     */
    @Excel(name = "分析备注")
    private String remarks;

    /**
     * 是否显示
     */
    @Excel(name = "是否显示")
    private String showFlag;

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public String getShowFlag() {
        return showFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("analysisId", getAnalysisId())
                .append("analysisName", getAnalysisName())
                .append("remarks", getRemarks())
                .append("showFlag", getShowFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
