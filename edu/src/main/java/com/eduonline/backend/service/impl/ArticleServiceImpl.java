package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.StudentMapper;
import com.eduonline.backend.mapper.TeacherMapper;
import com.eduonline.backend.model.dto.forum.ArticlePublishRequest;
import com.eduonline.backend.model.entity.*;
import com.eduonline.backend.model.vo.article.*;
import com.eduonline.backend.model.vo.course.CourseListVO;
import com.eduonline.backend.model.vo.course.CourseVO;
import com.eduonline.backend.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.ArticleConstant.*;
import static com.eduonline.backend.constant.UserConstant.*;

/**
 * @author Acer
 * @description 针对表【article(文章)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 发布文章
     *
     * @param articlePublishRequest
     * @param request
     * @return
     */
    // todo 改成token校验
    @Override
    public BaseResponse<Integer> publish(ArticlePublishRequest articlePublishRequest, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String id = articlePublishRequest.getAuthorId();
        int role = articlePublishRequest.getRole();
        //学生
        if (role == 0) {
            Student student = studentMapper.selectById(id);
            if (student == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id错误");
            }
            Article article = new Article();
            BeanUtils.copyProperties(articlePublishRequest, article);
            article.setArticleId("article_" + RandomUtil.randomString(30));
            article.setPublishTime(new Date());
            try {
                save(article);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作失败");
            }
            return ResultUtils.success(1);
        } else if (role == 1) { //教师
            Teacher teacher = teacherMapper.selectById(id);
            if (teacher == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id错误");
            }
            Article article = new Article();
            BeanUtils.copyProperties(articlePublishRequest, article);
            article.setArticleId("article_" + RandomUtil.randomString(30));
            article.setPublishTime(new Date());
            try {
                save(article);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作失败");
            }
            return ResultUtils.success(1);
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "角色不明");
        }
    }

    /**
     * 发帖者或管理员删除帖子
     *
     * @param articleId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> delArticle(String articleId, String userId, HttpServletRequest request) {
        if (articleId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        // 查询数据库,校验删除者是否是本人或管理员
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getArticleId, articleId)
                .eq(Article::getAuthorId, userId);
        long count = count(wrapper);
        if (count < 1) {
            //校验是否是管理员
            Object admin = request.getSession().getAttribute(ADMIN_ROLE);
            Integer adminNum = (Integer) admin;
            if (adminNum == null || adminNum != 1) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "帖子和所属者id不匹配或不存在");
            }
        }
        //校验成功删除数据
        try {
            super.removeById(articleId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "删除数据出现异常");
        }
        return ResultUtils.success(1);
    }

    /**
     * 点赞数前十
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> listMostFavorArticle(Integer status,HttpServletRequest request) {
        List<ArticleVO> articleVOList = articleMapper.queryTop10ArticlesByPraiseNum(status);
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);

        return ResultUtils.success(articleListVO);
    }

    /**
     * 点击数前十
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> listMostClickArticle(Integer status,HttpServletRequest request) {
        List<ArticleVO> articleVOList = articleMapper.queryTop10ArticlesByClickNum(status);
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);

        return ResultUtils.success(articleListVO);
    }


    /**
     * 点赞文章
     *
     * @param articleId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> likeArticle(String articleId, String userId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(articleId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 用户id和评论id的相互联系 key为userId::commentId 值为0或1，代表点赞还是未点赞
        String key = getLikedKey(userId, articleId);
        //判断当前登录用户是否点赞
        Object status = redisTemplate.opsForHash().get(ARTICLE_LIKE, key);
        //用户未点赞
        if (status == null || (Integer) status == 0) { // 空值表示未记录点赞情况
            redisTemplate.opsForHash().put(ARTICLE_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(ARTICLE_LIKE_COUNT, articleId, 1);
        }
        //用户已经点赞
        else if ((Integer) status == 1) {
            redisTemplate.opsForHash().delete(ARTICLE_LIKE, key);
            //点赞数-1
            redisTemplate.opsForHash().increment(ARTICLE_LIKE_COUNT, articleId, -1);
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "系统异常，点赞失败");
        }
//        Integer integer = (Integer) redisTemplate.opsForHash().get(ARTICLE_LIKE, key);
//        return ResultUtils.success(integer != null ? 1 : 0);
        return ResultUtils.success(1);
    }

    /**
     * 模糊查询文章
     *
     * @param name
     * @param current
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> queryArticleLike(String name, Integer current, Integer pageSize, Integer status) {
        if(current == 0) {
            current  = 1;
        }
        List<ArticleVO> articleVOList = articleMapper.queryArticles(name, (current - 1) * pageSize, pageSize, status);
        Long total = articleMapper.queryTotal(name, status);
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);

        return ResultUtils.success(articleListVO);
    }

    /**
     * 模糊查询文章
     *
     * @param name
     * @param current
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> queryArticleByAuthorName(String name, Integer current, Integer pageSize, Integer status) {
        if(current == 0) {
            current  = 1;
        }
        List<ArticleVO> articleVOList = articleMapper.queryArticleByAuthorName(name, (current - 1) * pageSize, pageSize, status);
        Long total = articleMapper.queryTotal(name, status);
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);

        return ResultUtils.success(articleListVO);
    }

    /**
     * 点击文章统计
     *
     * @param articleId
     * @param studentId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> onclickArticle(String articleId, String studentId, HttpServletRequest request) {
        // 用户id和评论id的相互联系 key为userId::commentId 值为0或1，代表点赞还是未点赞
        String key = getClickedKey(studentId, articleId);
        //判断当前登录用户是否点击过
        Object status = redisTemplate.opsForHash().get(ARTICLE_CLICK, key);
        //用户未点击
        if (status == null || (Integer) status == 0) { // 空值表示未记录点赞情况
            redisTemplate.opsForHash().put(ARTICLE_CLICK, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(ARTICLE_CLICK, articleId, 1);
        }
        return ResultUtils.success(1);
    }

    /**
     * 根据id查询文章
     *
     * @param articleId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ArticleVO> queryById(String articleId, HttpServletRequest request) {
        if (articleId == null || articleId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        ArticleVO articleVO = articleMapper.queryById(articleId);
        return ResultUtils.success(articleVO);
    }

    @Override
    public BaseResponse<ArticleListVO> listMyPublish(Integer current, Integer pageSize, String userId, HttpServletRequest request) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getAuthorId, userId);
        Page<Article> page = new Page<>(current, pageSize);

        Page<Article> articlePage = page(page, wrapper);
        List<Article> articleList = articlePage.getRecords();
        long total = articlePage.getTotal();
        List<ArticleVO> articleVOList;
        articleVOList = articleList.stream().map(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtil.copyProperties(article, articleVO);
            return articleVO;
        }).collect(Collectors.toList());


        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);

        return ResultUtils.success(articleListVO);
    }

    /**
     * 根据文章内容查询文章
     *
     * @param type
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> queryByType(String type, Integer current, Integer pageSize,HttpServletRequest request,Integer status) {
        if(current == 0) {
            current  = 1;
        }
        List<ArticleVO> articleVOList = articleMapper.queryArticleByType(type, (current - 1) * pageSize, pageSize, status);
        Long total = articleMapper.queryTotal(type, status);
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);
        return ResultUtils.success(articleListVO);
    }


    /**
     * 根据文章作者ID查询文章
     *
     * @param authorId
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ArticleListVO> queryByAuthorId(String authorId, Integer current, Integer pageSize, HttpServletRequest request) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        Page<Article> page = new Page<>(current, pageSize);
        wrapper.eq(Article::getArticleId, authorId);

        Page<Article> articlePage = page(page, wrapper);
        List<Article> articleList = articlePage.getRecords();
        long total = articlePage.getTotal();
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

    @Override
    public BaseResponse<ArticleListVO> queryByTime(Integer current, Integer pageSize, HttpServletRequest request,Integer status) {
        if(current == 0) {
            current  = 1;
        }
        List<ArticleVO> articleVOList = articleMapper.queryTop10ArticlesByTime( (current - 1) * pageSize, pageSize, status);
        Long total = articleMapper.queryTotal();
        ArticleListVO articleListVO = new ArticleListVO();
        articleListVO.setArticleVOList(articleVOList);
        articleListVO.setTotalNum(total);
        return ResultUtils.success(articleListVO);
    }


    static String getLikedKey(String userId, String commentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append(":like:");
        builder.append(commentId);
        return builder.toString();
    }

    static String getClickedKey(String userId, String commentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append(":clicked:");
        builder.append(commentId);
        return builder.toString();
    }
}




