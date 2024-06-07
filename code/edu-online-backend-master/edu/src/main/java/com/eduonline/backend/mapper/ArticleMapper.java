package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.Article;
import com.eduonline.backend.model.vo.article.ArticleVO;
import com.eduonline.backend.model.vo.course.CourseCommentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Acer
* @description 针对表【article(文章)】的数据库操作Mapper
* @createDate 2023-08-25 16:59:15
* @Entity com.eduonline.backend.model.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT article.*, coalesce(student.user_name,teacher.name) as authorName,coalesce(student.id,teacher.id) as authorId, coalesce(student.pic_img,teacher.pic_path) as authorImgUrl " +
            "FROM article " +
            "LEFT JOIN student ON article.author_id = student.id " +
            "LEFT JOIN teacher ON article.author_id = teacher.id " +
            "WHERE article.article_id = #{articleId} " +
            "AND article.is_delete = 0 ")
    ArticleVO queryById(String articleId);

    @Select("SELECT article.*, coalesce(student.user_name,teacher.name) as authorName,coalesce(student.id,teacher.id) as authorId, coalesce(student.pic_img,teacher.pic_path) as authorImgUrl " +
            "FROM article " +
            "LEFT JOIN student ON article.author_id = student.id " +
            "LEFT JOIN teacher ON article.author_id = teacher.id " +
            "WHERE article.title LIKE CONCAT('%', #{name}, '%') " +
            "AND article.status = #{status} " +
            "AND article.is_delete = 0 " +
            "ORDER BY article.publish_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<ArticleVO> queryArticles(String name, Integer start, Integer pageSize, Integer status);

    @Select("SELECT article.*, coalesce(student.user_name, teacher.name) as authorName, coalesce(student.id, teacher.id) as authorId, coalesce(student.pic_img, teacher.pic_path) as authorImgUrl " +
            "FROM article " +
            "LEFT JOIN student ON article.author_id = student.id " +
            "LEFT JOIN teacher ON article.author_id = teacher.id " +
            "WHERE (student.user_name = #{authorName} OR teacher.name = #{authorName}) " + // 根据作者名称过滤
            "AND article.status = #{status} " +
            "AND article.is_delete = 0 " +
            "ORDER BY article.publish_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<ArticleVO> queryArticleByAuthorName(String authorName, Integer start, Integer pageSize, Integer status);

    @Select("SELECT article.*,\n" +
            "       coalesce(student.user_name, teacher.name) as authorName,\n" +
            "       coalesce(student.id, teacher.id) as authorId,\n" +
            "       coalesce(student.pic_img, teacher.pic_path) as authorImgUrl\n" +
            "FROM article\n" +
            "         LEFT JOIN student ON article.author_id = student.id\n" +
            "         LEFT JOIN teacher ON article.author_id = teacher.id\n" +
            "ORDER BY article.click_num DESC -- 按点击降序排序\n" +
            "LIMIT 10;") // 限制结果为前十行
    List<ArticleVO> queryTop10ArticlesByClickNum(Integer status);

    @Select("SELECT article.*,\n" +
            "       coalesce(student.user_name, teacher.name) as authorName,\n" +
            "       coalesce(student.id, teacher.id) as authorId,\n" +
            "       coalesce(student.pic_img, teacher.pic_path) as authorImgUrl\n" +
            "FROM article\n" +
            "         LEFT JOIN student ON article.author_id = student.id\n" +
            "         LEFT JOIN teacher ON article.author_id = teacher.id\n" +
            "ORDER BY article.praise_num DESC -- 按点赞降序排序\n" +
            "LIMIT 10;") // 限制结果为前十行
    List<ArticleVO> queryTop10ArticlesByPraiseNum(Integer status);

    @Select("SELECT article.*, coalesce(student.user_name,teacher.name) as authorName,coalesce(student.id,teacher.id) as authorId, coalesce(student.pic_img,teacher.pic_path) as authorImgUrl " +
            "FROM article " +
            "LEFT JOIN student ON article.author_id = student.id " +
            "LEFT JOIN teacher ON article.author_id = teacher.id " +
            "WHERE article.article_type LIKE CONCAT('%', #{Type}, '%') " +
            "AND article.status = #{status} " +
            "AND article.is_delete = 0 " +
            "ORDER BY article.publish_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<ArticleVO> queryArticleByType(String Type, Integer start, Integer pageSize, Integer status);


    @Select("SELECT article.*,\n" +
            "       coalesce(student.user_name, teacher.name) as authorName,\n" +
            "       coalesce(student.id, teacher.id) as authorId,\n" +
            "       coalesce(student.pic_img, teacher.pic_path) as authorImgUrl\n" +
            "FROM article\n" +
            "         LEFT JOIN student ON article.author_id = student.id\n" +
            "         LEFT JOIN teacher ON article.author_id = teacher.id\n" +
            "ORDER BY article.publish_time DESC -- 按时间降序排序\n" +
            "LIMIT #{start}, #{pageSize}")
    List<ArticleVO> queryTop10ArticlesByTime(Integer start, Integer pageSize,Integer status);

    @Select("SELECT COUNT(*) as total " +
            "FROM article " +
            "LEFT JOIN student ON article.author_id = student.id " +
            "LEFT JOIN teacher ON article.author_id = teacher.id " +
            "WHERE article.title LIKE CONCAT('%', #{name}, '%')" +
            "AND article.status = #{status} " +
            "AND article.is_delete = 0 ")
    Long queryTotal(String name, Integer status);

    @Select(("SELECT COUNT(*) FROM article;"))
    Long queryTotal();
}




