package com.eduonline.backend.model.dto.forum;

import lombok.Data;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 16:40
 */
@Data
public class ArticlePublishRequest {
    private String authorId;
    private int role;
    private String title;
    private String summary;
    private String keyWord;
    private String imageUrl;
    private String source;
    private String articleType;
    private String content;
}
