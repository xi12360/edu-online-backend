package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.entity.ArticleFavorites;
import com.eduonline.backend.model.vo.article.ArticleListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【article_favorites(文章收藏表)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface ArticleFavoritesService extends IService<ArticleFavorites> {

    BaseResponse<Integer> favorArticle(String articleId, String userId, HttpServletRequest request);

    BaseResponse<Integer> queryFavorArticle(String articleId, String userId, HttpServletRequest request);

    BaseResponse<ArticleListVO> listMyFavor(Integer current, Integer pageSize, String userId, HttpServletRequest request);
}
