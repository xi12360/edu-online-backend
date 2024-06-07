package com.eduonline.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.model.entity.ArticleLike;
import com.eduonline.backend.model.entity.Course;
import com.eduonline.backend.model.entity.CourseCommentLike;
import com.eduonline.backend.model.entity.ForumCommentLike;
import com.eduonline.backend.service.ArticleLikeService;
import com.eduonline.backend.service.CourseCommentLikeService;
import com.eduonline.backend.service.CourseService;
import com.eduonline.backend.service.ForumCommentLikeService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

import static com.eduonline.backend.constant.ArticleConstant.*;
import static com.eduonline.backend.constant.CommentConstant.*;

/**
 * 主类（项目启动入口）
 *
 */
// todo 如需开启 Redis，须移除 exclude 中的内容
//@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@SpringBootApplication()
@MapperScan("com.eduonline.backend.mapper")
//@ComponentScan("com.api.backend.config")
@EnableScheduling //开启定时任务
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Resource
    private ArticleLikeService articleLikeService;

    @Resource
    private ForumCommentLikeService forumCommentLikeService;

    @Resource
    private CourseCommentLikeService courseCommentLikeService;

    @Resource
    private CourseService courseService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @PostConstruct
    @Transactional
    public void loadDataToRedis() {
        // 从数据库中查询数据
        // 1.查询文章点赞表
        LambdaQueryWrapper<ArticleLike> articleLikeWrapper = new LambdaQueryWrapper<>();
        articleLikeWrapper.eq(ArticleLike::getStatus, 1);
        List<ArticleLike> articleLikeList = articleLikeService.list(articleLikeWrapper);
        for(ArticleLike articleLike: articleLikeList) {
            String articleId = articleLike.getArticleId();
            String authorId = articleLike.getAuthorId();
            String key = getLikedKey(authorId, articleId);
            redisTemplate.opsForHash().put(ARTICLE_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(ARTICLE_LIKE_COUNT, articleId, 1);
        }

        // 2.查询文章评论点赞表
        List<ForumCommentLike> forumCommentLikeList = forumCommentLikeService.list();
        for(ForumCommentLike forumCommentLike: forumCommentLikeList) {
            String commentId = forumCommentLike.getCommentId();
            String userId = forumCommentLike.getUserId();
            String key = getLikedKey(userId, commentId);
            redisTemplate.opsForHash().put(FORUM_COMMENT_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(FORUM_COMMENT_LIKE_COUNT, commentId, 1);
        }

        // 3.查询课程评论点赞表
        List<CourseCommentLike> courseCommentLikeList = courseCommentLikeService.list();
        for(CourseCommentLike courseCommentLike: courseCommentLikeList) {
            String commentId = courseCommentLike.getCommentId();
            String userId = courseCommentLike.getUserId();
            String key = getLikedKey(userId, commentId);
            redisTemplate.opsForHash().put(COURSE_COMMENT_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(COURSE_COMMENT_LIKE_COUNT, commentId, 1);
        }

        // 4.查询课程表，获取点击数量
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1);
        List<Course> courseList = courseService.list(wrapper);
        for(Course course: courseList) {
            String courseId = course.getCourseId();
            Integer viewCount = course.getViewCount();
            redisTemplate.opsForHash().put(COURSE_CLICK_COUNT, courseId, viewCount);
        }

    }

    static String getLikedKey(String userId, String commentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append(":like:");
        builder.append(commentId);
        return builder.toString();
    }

}
