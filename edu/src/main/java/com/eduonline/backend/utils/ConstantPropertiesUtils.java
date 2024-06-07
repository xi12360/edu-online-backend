package com.eduonline.backend.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhouhengzhe
 * @Description: 读取oss配置信息,当项目已启动，Spring接口，Spring加载之后，执行接口的一个方法
 * @date 2021/6/29上午1:47
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantPropertiesUtils implements InitializingBean {

    /**
     * 对应公网endpoint地址
     */
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    /**
     * 账号
     */
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    /**
     * 密码
     */
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    /**
     * 对应要存储到哪个块
     */
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        ACCESS_KEY_ID=keyId;
        ACCESS_KEY_SECRET=keySecret;
        BUCKET_NAME=bucketName;
    }
}


