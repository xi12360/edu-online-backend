package com.eduonline.backend.model.vo.course;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:20
 */
@Data
public class CourseCommentVO {

    /**
     * 评论ID
     */
    private String commentId;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 用户ID
     */
    private String userId;

    private String userName;
    private String userImgUrl;

    /**
     * 父级评论id
     */
    private String pCommentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 发表时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;

    /**
     * 被评论人ID
     */
    private String otherId;

    /**
     * 点赞数
     */
    private Integer praiseCount;

    /**
     * 回复数
     */
    private Integer replyCount;
    private boolean liked;
}
