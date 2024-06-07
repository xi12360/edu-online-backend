package com.eduonline.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.ArticleLikeMapper;
import com.eduonline.backend.model.entity.ArticleLike;
import com.eduonline.backend.service.ArticleLikeService;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【article_like(文章点赞映射表)】的数据库操作Service实现
* @createDate 2023-08-31 10:05:32
*/
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike>
    implements ArticleLikeService {

}




