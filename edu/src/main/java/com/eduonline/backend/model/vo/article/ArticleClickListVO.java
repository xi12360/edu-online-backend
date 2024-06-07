package com.eduonline.backend.model.vo.article;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/7 16:43
 */
@Data
public class ArticleClickListVO {
    private List<ArticleClickVO> articleClickVOList;
    private int totalNum;
}
