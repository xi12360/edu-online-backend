package com.eduonline.backend.model.vo.article;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/7 18:43
 */
@Data
public class ArticleFavorListVO {
    private List<ArticleFavorVO> articleFavorVOList;
    private int totalNum;
}
