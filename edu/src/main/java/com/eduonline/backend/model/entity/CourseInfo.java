package com.eduonline.backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程信息
 * @TableName course_info
 */
@TableName(value ="course_info")
@Data
public class CourseInfo implements Serializable {
    /**
     * 资料id
     */
    @TableId
    private String id;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 视频链接
     */
    private String videoLink;

    /**
     * 资料链接
     */
    private String infoLink;

    /**
     * 逻辑删除(0未删1已删)
     */
    private Integer isDelete;

    /**
     * 章节详情
     */
    private String content;

    /**
     * 课程章节数
     */
    private Integer chapterNum;

    /**
     * 章节简介
     */
    private String intro;

    /**
     * 章节标题
     */
    private String title;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CourseInfo other = (CourseInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getVideoLink() == null ? other.getVideoLink() == null : this.getVideoLink().equals(other.getVideoLink()))
            && (this.getInfoLink() == null ? other.getInfoLink() == null : this.getInfoLink().equals(other.getInfoLink()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getChapterNum() == null ? other.getChapterNum() == null : this.getChapterNum().equals(other.getChapterNum()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getVideoLink() == null) ? 0 : getVideoLink().hashCode());
        result = prime * result + ((getInfoLink() == null) ? 0 : getInfoLink().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getChapterNum() == null) ? 0 : getChapterNum().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", videoLink=").append(videoLink);
        sb.append(", infoLink=").append(infoLink);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", content=").append(content);
        sb.append(", chapterNum=").append(chapterNum);
        sb.append(", intro=").append(intro);
        sb.append(", title=").append(title);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}