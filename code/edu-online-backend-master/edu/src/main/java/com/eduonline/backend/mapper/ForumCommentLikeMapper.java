package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.ForumCommentLike;
import org.apache.ibatis.annotations.Select;

/**
* @author Acer
* @description 针对表【forum_comment_like(论坛评论点赞映射表)】的数据库操作Mapper
* @createDate 2023-08-31 10:05:32
* @Entity generator.domain.ForumCommentLike
*/
public interface ForumCommentLikeMapper extends BaseMapper<ForumCommentLike> {

    @Select("SELECT * FROM forum_comment_like WHERE comment_id = #{commentId} AND user_id = #{userId} " +
            "AND forum_comment_like.is_delete = 0 ")
    ForumCommentLike countByCommentIdAndUserId(String commentId, String userId);
}




