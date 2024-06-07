package com.eduonline.backend.controller;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.utils.ConstantPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/29 15:41
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@CrossOrigin
public class UploadController {

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "<yourAccessKeyId>";
    private static String accessKeySecret = "<yourAccessKeySecret>";

    /**
     * 上传图片，返回图片url
     *
     * @param file
     * @return
     */
    @PostMapping("/image")
    public BaseResponse<String> uploadImg(@RequestParam(value = "file", required = false) MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "上传文件为空");
        }
        File tmpFile = null;
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        //块名
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件真实名称
            String originalFilename = file.getOriginalFilename();
            //重命名，防止相同文件出现覆盖 生成的f4f2e1a3-391a-4d5a-9438-0c9f5d27708=》需要替换成f4f2e1a3391a4d5a94380c9f5d27708c
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //新的文件名
            originalFilename = uuid + originalFilename;
            //2、把文件按照日期进行分类
            // 2021/6/30
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接 021/6/30/1.jpg
            originalFilename = "image/" + datePath + "/" + originalFilename;
            // oss实现上传文件
            //第一个参数：Bucket名称
            //第二个参数：上传到oss文件路径和文件名称 /zhz/avatar.txt
            ossClient.putObject(bucketName, originalFilename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来->https://zhz-mail.oss-cn-beijing.aliyuncs.com/WechatIMG19.jpeg
            String url = "https://" + bucketName + "." + endpoint + "/" + originalFilename;
            return ResultUtils.success(url);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传失败");
        } finally {
//            File f = new File(tmpFile.toURI());
//            f.delete();
        }
    }

    /**
     * 上传资料，返回图片url
     *
     * @param file
     * @return
     */
    @PostMapping("/file")
    public BaseResponse<String> uploadMaterial(@RequestParam(value = "file", required = false) MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "上传文件为空");
        }
        File tmpFile = null;
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        //块名
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件真实名称
            String originalFilename = file.getOriginalFilename();
            //重命名，防止相同文件出现覆盖 生成的f4f2e1a3-391a-4d5a-9438-0c9f5d27708=》需要替换成f4f2e1a3391a4d5a94380c9f5d27708c
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //新的文件名
            originalFilename = uuid + originalFilename;
            //2、把文件按照日期进行分类
            // 2021/6/30
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接 021/6/30/1.jpg
            originalFilename = "material/" + datePath + "/" + originalFilename;
            // oss实现上传文件
            //第一个参数：Bucket名称
            //第二个参数：上传到oss文件路径和文件名称 /zhz/avatar.txt
            ossClient.putObject(bucketName, originalFilename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来->https://zhz-mail.oss-cn-beijing.aliyuncs.com/WechatIMG19.jpeg
            String url = "https://" + bucketName + "." + endpoint + "/" + originalFilename;
            return ResultUtils.success(url);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传失败");
        } finally {
//            File f = new File(tmpFile.toURI());
//            f.delete();
        }
    }
}
