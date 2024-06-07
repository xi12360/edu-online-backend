package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.ArticleMapper;
import com.eduonline.backend.model.entity.Article;
import com.eduonline.backend.model.entity.ArticleFavorites;
import com.eduonline.backend.model.vo.article.ArticleListVO;
import com.eduonline.backend.model.vo.article.ArticleVO;
import com.eduonline.backend.service.ArticleFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.ArticleFavoritesMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Acer
 * @description 针对表【article_favorites(文章收藏表)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class ArticleFavoritesServiceImpl extends ServiceImpl<ArticleFavoritesMapper, ArticleFavorites>
        implements ArticleFavoritesService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public BaseResponse<Integer> favorArticle(String articleId, String userId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(articleId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        LambdaQueryWrapper<ArticleFavorites> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorites::getArticleId, articleId)
                .eq(ArticleFavorites::getUserId, userId);
        long count = count(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "你已经收藏过了");
        }
        ArticleFavorites articleFavorites = new ArticleFavorites();
        articleFavorites.setArticleId(articleId);
        articleFavorites.setUserId(userId);
        articleFavorites.setId("article_favor_" + RandomUtil.randomString(30));
        articleFavorites.setAddTime(new Date());
        save(articleFavorites);
        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<Integer> queryFavorArticle(String articleId, String userId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(articleId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        LambdaQueryWrapper<ArticleFavorites> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorites::getArticleId, articleId)
                .eq(ArticleFavorites::getUserId, userId);
        long count = count(wrapper);
        if (count > 0) {
            return ResultUtils.success(1);
        }
        return ResultUtils.success(0);
    }

    @Override
    public BaseResponse<ArticleListVO> listMyFavor(Integer current, Integer pageSize, String userId, HttpServletRequest request) {
        LambdaQueryWrapper<ArticleFavorites> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleFavorites::getUserId, userId);
        //筛选出该ID报名的课程
        Page<ArticleFavorites> page = new Page<>(current, pageSize);
        Page<ArticleFavorites> articleFavoritesPage = page(page, wrapper);
        List<ArticleFavorites> articleFavoritesList = articleFavoritesPage.getRecords();
        long total = articleFavoritesPage.getTotal();

        List<String> articleIdList;
        articleIdList = articleFavoritesList.stream().map(articleFavorites -> {
            String articleId = articleFavorites.getArticleId();
            return articleId;
        }).collect(Collectors.toList());
        List<Article> articleList = articleMapper.selectBatchIds(articleIdList);
        List<ArticleVO> articleVOList;
        articleVOList = articleList.stream().map(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            return articleVO;
        }).collect(Collectors.toList());

        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);
        return ResultUtils.success(articleListVO);
    }
}




