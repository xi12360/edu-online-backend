package com.eduonline.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.ForumCommentLikeMapper;
import com.eduonline.backend.model.entity.ForumCommentLike;
import com.eduonline.backend.service.ForumCommentLikeService;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【forum_comment_like(论坛评论点赞映射表)】的数据库操作Service实现
* @createDate 2023-08-31 10:05:32
*/
@Service
public class ForumCommentLikeServiceImpl extends ServiceImpl<ForumCommentLikeMapper, ForumCommentLike>
    implements ForumCommentLikeService {

}




