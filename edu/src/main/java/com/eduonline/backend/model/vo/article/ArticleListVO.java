package com.eduonline.backend.model.vo.article;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/5 14:34
 */
@Data
public class ArticleListVO {
    private List<ArticleVO> articleVOList;
    private long totalNum;
}
