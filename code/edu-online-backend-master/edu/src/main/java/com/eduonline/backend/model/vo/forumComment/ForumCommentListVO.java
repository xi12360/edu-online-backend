package com.eduonline.backend.model.vo.forumComment;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/5 14:47
 */
@Data
public class ForumCommentListVO {
    private List<ForumCommentVO> forumCommentVOList;
    private int total;
}
