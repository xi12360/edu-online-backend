package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.ArticleLike;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author Acer
* @description 针对表【article_like(文章点赞映射表)】的数据库操作Mapper
* @createDate 2023-08-31 10:05:32
* @Entity generator.domain.ArticleLike
*/
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
    @Select("SELECT * FROM article_like WHERE comment_id = #{articleId} AND author_id = #{userId} " +
            "AND article_like.is_delete = 0 ")
    ArticleLike countByCommentIdAndUserId(String articleId, String userId);
}




