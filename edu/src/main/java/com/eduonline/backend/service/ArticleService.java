package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.forum.ArticlePublishRequest;
import com.eduonline.backend.model.entity.Article;
import com.eduonline.backend.model.vo.article.*;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【article(文章)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface ArticleService extends IService<Article> {

    BaseResponse<Integer> publish(ArticlePublishRequest articlePublishRequest, HttpServletRequest request);

    BaseResponse<Integer> delArticle(String articleId, String userId, HttpServletRequest request);

    BaseResponse<ArticleListVO> listMostFavorArticle(Integer status,HttpServletRequest request);

    BaseResponse<ArticleListVO> listMostClickArticle(Integer status,HttpServletRequest request);

    BaseResponse<Integer> likeArticle(String articleId, String userId, HttpServletRequest request);

    BaseResponse<ArticleListVO> queryArticleLike(String name, Integer current, Integer pageSize, Integer status);

    BaseResponse<Integer> onclickArticle(String articleId, String studentId, HttpServletRequest request);

    BaseResponse<ArticleVO> queryById(String articleId, HttpServletRequest request);

    BaseResponse<ArticleListVO> listMyPublish(Integer current, Integer pageSize, String userId, HttpServletRequest request);

    BaseResponse<ArticleListVO> queryByType(String type,Integer current, Integer pageSize,HttpServletRequest request,Integer status);

    BaseResponse<ArticleListVO> queryByAuthorId(String authorId,Integer current, Integer pageSize,HttpServletRequest request);

    BaseResponse<ArticleListVO> queryByTime(Integer current, Integer pageSize, HttpServletRequest request,Integer status);

    BaseResponse<ArticleListVO> queryArticleByAuthorName(String name,Integer current, Integer pageSize,Integer status);
}
