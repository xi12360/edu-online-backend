package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.CourseCommentLike;
import org.apache.ibatis.annotations.Select;

/**
* @author Acer
* @description 针对表【course_comment_like(课程评论点赞映射表)】的数据库操作Mapper
* @createDate 2023-08-31 10:05:32
* @Entity generator.domain.CourseCommentLike
*/
public interface CourseCommentLikeMapper extends BaseMapper<CourseCommentLike> {

    @Select("SELECT * FROM course_comment_like WHERE comment_id = #{commentId} AND user_id = #{userId} " +
            "AND course_comment_like.is_delete = 0 " )
    CourseCommentLike countByCommentIdAndUserId(String commentId, String userId);
}




