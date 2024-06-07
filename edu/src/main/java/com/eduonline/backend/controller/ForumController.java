package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.forum.ArticlePublishRequest;
import com.eduonline.backend.model.dto.forum.ForumCommentRequest;
import com.eduonline.backend.model.dto.forum.ReplyForumCommentRequest;
import com.eduonline.backend.model.vo.article.*;
import com.eduonline.backend.model.vo.course.CourseCommentListVO;
import com.eduonline.backend.model.vo.course.CourseListVO;
import com.eduonline.backend.model.vo.forumComment.ForumCommentListVO;
import com.eduonline.backend.service.ArticleFavoritesService;
import com.eduonline.backend.service.ArticleService;
import com.eduonline.backend.service.ForumCommentService;
import com.eduonline.backend.service.MsgSystemService;
import com.eduonline.backend.service.impl.ArticleFavoritesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:42
 */
@RestController
@RequestMapping("/forum")
@Slf4j
@CrossOrigin
public class ForumController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleFavoritesService articleFavoritesService;

    @Resource
    private ForumCommentService commentService;

    @Resource
    private ArticleFavoritesServiceImpl articleFavoritesServiceImpl;

    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private MsgSystemService msgSystemService;

    /**
     * 发布帖子
     *
     * @param articlePublishRequest
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public BaseResponse<Integer> publish(@RequestBody ArticlePublishRequest articlePublishRequest,
                                         HttpServletRequest request) {
        return articleService.publish(articlePublishRequest, request);
    }

    /**
     * 分页查看文章评论
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/queryComment")
    public BaseResponse<ForumCommentListVO> queryComment
    (@RequestParam(value = "articleId") String articleId,
     @RequestParam(value = "userId") String userId,
     @RequestParam(value = "current", defaultValue = "1") Integer current,
     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
     HttpServletRequest request) {
        return commentService.queryComment(articleId,userId, current, pageSize, request);
    }


    /**
     * 发布评论
     *
     * @param forumCommentRequest
     * @param request
     * @return
     */
    @PostMapping("/comment")
    public BaseResponse<Integer> comment(@RequestBody ForumCommentRequest forumCommentRequest
            , HttpServletRequest request) {
        return commentService.comment(forumCommentRequest, request);
    }

    /**
     * 回复评论
     *
     * @param replyCommentRequest
     * @param request
     * @return
     */
    @PostMapping("/reply")
    public BaseResponse<Integer> replyComment(@RequestBody ReplyForumCommentRequest replyCommentRequest
            , HttpServletRequest request) {
        return commentService.replyComment(replyCommentRequest, request);
    }

    /**
     * 评论者或管理员删除评论
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @DeleteMapping("/del/comment")
    public BaseResponse<Integer> delComment(@RequestParam("commentId") String commentId,
                                            @RequestParam("userId") String userId,
                                            HttpServletRequest request) {
        return commentService.delComment(commentId, userId, request);
    }

    /**
     * 评论者或管理员删除帖子
     *
     * @param articleId
     * @param request
     * @param userId
     * @return
     */
    @DeleteMapping("/del/article")
    public BaseResponse<Integer> delArticle(@RequestParam("articleId") String articleId,
                                            @RequestParam("userId") String userId,
                                            HttpServletRequest request) {
        return articleService.delArticle(articleId, userId, request);
    }

    /**
     * 用户查看点赞量前十的文章
     *
     * @param request
     * @return
     */
    @GetMapping("/mostFavorArt")
    public BaseResponse<ArticleListVO> listMostFavorArticle(@RequestParam(value = "status", defaultValue = "0") Integer status,
                                                            HttpServletRequest request) {
        return articleService.listMostFavorArticle(status,request);
    }

    /**
     * 用户查看点击数前十的文章
     *
     * @param request
     * @return
     */
    @GetMapping("/mostClickArt")
    public BaseResponse<ArticleListVO> listMostClickArticle(@RequestParam(value = "status", defaultValue = "0") Integer status,
                                                            HttpServletRequest request) {
        return articleService.listMostClickArticle(status,request);
    }

    /**
     * 点赞评论
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/like/comment")
    public BaseResponse<Integer> likeComment(@RequestParam("commentId") String commentId,
                                             @RequestParam("userId") String userId,
                                             HttpServletRequest request) {
        return forumCommentService.likeComment(commentId, userId, request);
    }

    /**
     * 点赞文章
     *
     * @param articleId
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/like/article")
    public BaseResponse<Integer> likeArticle(@RequestParam("commentId") String articleId,
                                             @RequestParam("userId") String userId,
                                             HttpServletRequest request) {
        return articleService.likeArticle(articleId, userId, request);
    }

    /**
     * 收藏文章
     *
     * @param articleId
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/favor/article")
    public BaseResponse<Integer> favorArticle(@RequestParam("articleId") String articleId,
                                             @RequestParam("userId") String userId,
                                             HttpServletRequest request) {
        return articleFavoritesService.favorArticle(articleId, userId, request);
    }

    /**
     * 查看是否收藏文章
     *
     * @param articleId
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/queryFavor/article")
    public BaseResponse<Integer> queryFavorArticle(@RequestParam("articleId") String articleId,
                                              @RequestParam("userId") String userId,
                                              HttpServletRequest request) {
        return articleFavoritesService.queryFavorArticle(articleId, userId, request);
    }


    /**
     * 根据文章名模糊查询文章
     * @param name
     * @param current
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/query")
    public BaseResponse<ArticleListVO> queryArticleLike
            (@RequestParam(value = "name", defaultValue = "") String name,
             @RequestParam(value = "current", defaultValue = "1") Integer current,
             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
             @RequestParam(value = "status", defaultValue = "0") Integer status) {
        return articleService.queryArticleLike(name, current, pageSize, status);
    }

    /**
     * 根据作者名模糊查询文章
     * @param name
     * @param current
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/queryByAuthorName")
    public BaseResponse<ArticleListVO> queryArticleByAuthorName
    (@RequestParam(value = "name", defaultValue = "") String name,
     @RequestParam(value = "current", defaultValue = "1") Integer current,
     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
     @RequestParam(value = "status", defaultValue = "0") Integer status) {
        return articleService.queryArticleByAuthorName(name, current, pageSize, status);
    }

    /**
     * 点击文章
     * @param articleId
     * @param request
     * @return
     */
    @GetMapping("/onclick")
    public BaseResponse<Integer> onclickArticle(@RequestParam("articleId") String articleId,
                                               @RequestParam("studentId") String studentId,
                                               HttpServletRequest request) {
        return articleService.onclickArticle(articleId,studentId, request);
    }

    /**
     * 根据文章类型查文章
     * @param type
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/queryByType")
    BaseResponse<ArticleListVO> queryByType(@RequestParam("type") String type,
                                            @RequestParam(value = "current", defaultValue = "1") Integer current,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            HttpServletRequest request,
                                            @RequestParam(value = "status", defaultValue = "0") Integer status){
        return articleService.queryByType(type,current,pageSize,request,status);
    }

    /**
     * 根据文章作者ID查文章
     * @param authorId
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/queryByAuthorId")
    BaseResponse<ArticleListVO> queryByAuthorId(@RequestParam("authorId") String authorId,
                                            @RequestParam(value = "current", defaultValue = "1") Integer current,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            HttpServletRequest request){
        return articleService.queryByAuthorId(authorId,current,pageSize,request);
    }

    /**
     * 根据文章发布时间查文章
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/queryByTime")
    BaseResponse<ArticleListVO> queryByTime(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            HttpServletRequest request,
                                            @RequestParam(value = "status", defaultValue = "0") Integer status){
        return articleService.queryByTime(current,pageSize,request,status);
    }

    /**
     * 根据id查询
     * @param articleId
     * @param request
     * @return
     */
    @GetMapping("/queryById")
    public BaseResponse<ArticleVO> queryById(@RequestParam("articleId") String articleId,
                                             HttpServletRequest request) {
        return articleService.queryById(articleId, request);
    }

    /**
     * 查看用户收藏文章
     *
     * @param request
     * @return
     */
    @GetMapping("/listMyFavor")
    public BaseResponse<ArticleListVO> listMyFavor(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam("userId") String userId,
                                                  HttpServletRequest request) {
        return articleFavoritesService.listMyFavor(current, pageSize, userId, request);
    }

    /**
     * 查看用户发布文章
     *
     * @param request
     * @return
     */
    @GetMapping("/listMyPublish")
    public BaseResponse<ArticleListVO> listMyPublish(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                   @RequestParam("userId") String userId,
                                                   HttpServletRequest request) {
        return articleService.listMyPublish(current, pageSize, userId, request);
    }

    /**
     * 查看子评论
     * @param request
     * @return
     */
    @GetMapping("/queryChildComment")
    public BaseResponse<ForumCommentListVO> queryChildComment
    (@RequestParam(value = "commentId") String commentId,
    @RequestParam(value = "userId") String userId,
     HttpServletRequest request) {
        return forumCommentService.queryChildComment(commentId,userId, request);
    }


}

