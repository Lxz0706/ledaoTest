package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学习培训对象 sys_training
 *
 * @author lxz
 * @date 2023-07-13
 */
public class SysTraining extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long trainId;

    /**
     * 文件ID
     */
    @Excel(name = "文件ID")
    private Long documentId;

    /**
     * 播放时间
     */
    @Excel(name = "播放时间")
    private Long playTime;

    /**
     * 播放进度
     */
    @Excel(name = "播放进度")
    private String playbackProgress;

    /**
     * 学习进度
     */
    @Excel(name = "学习进度")
    private String learnTime;

    /**
     * 总时长
     */
    private Long totalTime;

    /**
     * 文件名称
     */
    private String documentName;

    /**
     * 学习人员
     */
    private String creator;

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setPlayTime(Long playTime) {
        this.playTime = playTime;
    }

    public Long getPlayTime() {
        return playTime;
    }

    public void setPlaybackProgress(String playbackProgress) {
        this.playbackProgress = playbackProgress;
    }

    public String getPlaybackProgress() {
        return playbackProgress;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public String getLearnTime() {
        return learnTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("trainId", getTrainId())
                .append("documentId", getDocumentId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("playTime", getPlayTime())
                .append("playbackProgress", getPlaybackProgress())
                .append("learnTime", getLearnTime())
                .append("totalTime", getTotalTime())
                .toString();
    }
}
