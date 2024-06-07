package com.eduonline.backend.model.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/5 14:35
 */
@Data
public class ArticleVO {
    private String articleId;
    private String title;
    private String summary;
    private String keyWord;
    private String imgUrl;
    private String source;
    private String authorId;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishTime;
    private String articleType;
    private int clickNum;
    private int praiseNum;
    private int commentNum;
    private String content;
    private String authorImgUrl;
    private String authorName;
}
