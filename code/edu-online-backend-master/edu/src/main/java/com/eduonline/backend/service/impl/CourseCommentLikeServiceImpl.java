package com.eduonline.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.CourseCommentLikeMapper;
import com.eduonline.backend.model.entity.CourseCommentLike;
import com.eduonline.backend.service.CourseCommentLikeService;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【course_comment_like(课程评论点赞映射表)】的数据库操作Service实现
* @createDate 2023-08-31 10:05:32
*/
@Service
public class CourseCommentLikeServiceImpl extends ServiceImpl<CourseCommentLikeMapper, CourseCommentLike>
    implements CourseCommentLikeService {

}




