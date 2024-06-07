package com.eduonline.backend.timer;

import cn.hutool.core.util.RandomUtil;
import com.eduonline.backend.mapper.*;
import com.eduonline.backend.model.entity.*;
import com.eduonline.backend.service.*;
import io.swagger.models.auth.In;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.eduonline.backend.constant.ArticleConstant.*;
import static com.eduonline.backend.constant.CommentConstant.*;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/5 16:37
 */
@Component
public class RedisToMysqlSyncTask {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleLikeService articleLikeService;

    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private ForumCommentLikeService forumCommentLikeService;

    @Resource
    private CourseCommentLikeService courseCommentLikeService;

    @Resource
    private CourseCommentService courseCommentService;

    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Resource
    private ForumCommentLikeMapper forumCommentLikeMapper;

    @Resource
    private CourseCommentLikeMapper courseCommentLikeMapper;

    @Resource
    private CourseService courseService;

    @Scheduled(cron = "0 0/10 * * * ?")  // 每10分钟执行一次
    public void syncData() {
        // 1.读取文章点赞数量
        Map<String, Integer> entries = redisTemplate.opsForHash().entries(ARTICLE_LIKE_COUNT);
        List<Article> articleList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: entries.entrySet()) {
            String articleId = entry.getKey();
            Integer praiseNum = entry.getValue();
            if(articleId != null || !articleId.equals("")) {
                Article article = new Article();
                article.setArticleId(articleId);
                article.setPraiseNum(praiseNum);
                articleList.add(article);
            }
        }
        // 连接 MySQL，写入数据
        articleService.updateBatchById(articleList);

        // 2.读取文章点赞关系映射表
        Map<String, Integer> likeEntries = redisTemplate.opsForHash().entries(ARTICLE_LIKE);
        List<ArticleLike> articleLikeList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: likeEntries.entrySet()) {
            String key = entry.getKey();
            Integer status = entry.getValue();
            if(status == null || key == null || key.equals("")) {
                continue;
            }
            String[] parts = key.split(":like:");
            String authorId = parts[0];
            String articleId = parts[1];

            ArticleLike articleLike = articleLikeMapper.countByCommentIdAndUserId(articleId, authorId);
            if(articleLike != null) {
                // 如果存在相同记录，则跳过当前迭代
                articleLike.setStatus(status);
                articleLikeMapper.updateById(articleLike);
                continue;
            }
            articleLike = new ArticleLike();
            articleLike.setId("article_like_" + RandomUtil.randomString(30));
            articleLike.setArticleId(articleId);
            articleLike.setAuthorId(authorId);
            articleLike.setStatus(status);
            articleLikeList.add(articleLike);
        }
        // 连接 MySQL，写入数据
        articleLikeService.saveBatch(articleLikeList);

        // 3.读取文章评论点赞数量
        Map<String, Integer> forumCommentLikeEntries = redisTemplate.opsForHash().entries(FORUM_COMMENT_LIKE_COUNT);
        List<ForumComment> forumCommentList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: forumCommentLikeEntries.entrySet()) {
            String commentId = entry.getKey();
            Integer praiseNum = entry.getValue();
            if(commentId != null || !commentId.equals("")) {
                ForumComment forumComment = new ForumComment();
                forumComment.setCommentId(commentId);
                forumComment.setPraiseCount(praiseNum);
                forumCommentList.add(forumComment);
            }
        }
        // 连接 MySQL，写入数据
        forumCommentService.updateBatchById(forumCommentList);

        // 4.读取文章评论点赞关系映射表
        Map<String, Integer> forumCommentEntries = redisTemplate.opsForHash().entries(FORUM_COMMENT_LIKE);
        List<ForumCommentLike> forumCommentLikeList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: forumCommentEntries.entrySet()) {
            String key = entry.getKey();
            Integer status = entry.getValue();
            if(status == null || key == null || key.equals("")) {
                continue;
            }
            String[] parts = key.split(":like:");
            String userId = parts[0];
            String commentId = parts[1];

            ForumCommentLike forumCommentLike = forumCommentLikeMapper.countByCommentIdAndUserId(commentId, userId);
            if(forumCommentLike != null) {
                // 如果存在相同记录，则跳过当前迭代
                forumCommentLike.setStatus(status);
                forumCommentLikeMapper.updateById(forumCommentLike);
                continue;
            }
            forumCommentLike = new ForumCommentLike();
            forumCommentLike.setId("forum_comment_like_" + RandomUtil.randomString(30));
            forumCommentLike.setCommentId(commentId);
            forumCommentLike.setUserId(userId);
            forumCommentLike.setStatus(status);
            forumCommentLikeList.add(forumCommentLike);
        }
        // 连接 MySQL，写入数据
        forumCommentLikeService.saveOrUpdateBatch(forumCommentLikeList);

        // 5.读取课程评论点赞数量
        Map<String, Integer> courseCommentLikeEntries = redisTemplate.opsForHash().entries(COURSE_COMMENT_LIKE_COUNT);
        List<CourseComment> courseCommentList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: courseCommentLikeEntries.entrySet()) {
            String commentId = entry.getKey();
            Integer praiseNum = entry.getValue();
            if(commentId != null || !commentId.equals("")) {
                CourseComment courseComment = new CourseComment();
                courseComment.setCommentId(commentId);
                courseComment.setPraiseCount(praiseNum);
                courseCommentList.add(courseComment);
            }
        }
        // 连接 MySQL，写入数据
        courseCommentService.updateBatchById(courseCommentList);

        // 6.读取课程评论点赞关系映射表
        Map<String, Integer> courseCommentEntries = redisTemplate.opsForHash().entries(COURSE_COMMENT_LIKE);
        List<CourseCommentLike> courseCommentLikeList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: courseCommentEntries.entrySet()) {
            String key = entry.getKey();
            Integer status = entry.getValue();
            if(status == null || key == null || key.equals("")) {
                continue;
            }
            String[] parts = key.split(":like:");
            String userId = parts[0];
            String commentId = parts[1];

            CourseCommentLike courseCommentLike = courseCommentLikeMapper.countByCommentIdAndUserId(commentId, userId);
            if(courseCommentLike != null) {
                // 如果存在相同记录，则跳过当前迭代
                courseCommentLike.setStatus(status);
                courseCommentLikeMapper.updateById(courseCommentLike);
                continue;
            }

            courseCommentLike = new CourseCommentLike();
            courseCommentLike.setId("forum_comment_like_" + RandomUtil.randomString(30));
            courseCommentLike.setCommentId(commentId);
            courseCommentLike.setUserId(userId);
            courseCommentLike.setStatus(status);
            courseCommentLikeList.add(courseCommentLike);
        }
        // 连接 MySQL，写入数据
        courseCommentLikeService.saveOrUpdateBatch(courseCommentLikeList);

        // 7.读取课程点击数量
        Map<String, Integer> courseClickEntries = redisTemplate.opsForHash().entries(COURSE_CLICK_COUNT);
        List<Course> courseList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: courseClickEntries.entrySet()) {
            String courseId = entry.getKey();
            Integer clickNum = entry.getValue();
            if(courseId != null || !courseId.equals("")) {
                Course course = new Course();
                course.setCourseId(courseId);
                course.setViewCount(clickNum);
                courseList.add(course);
            }
        }
        // 连接 MySQL，写入数据
        courseService.updateBatchById(courseList);

    }


}
