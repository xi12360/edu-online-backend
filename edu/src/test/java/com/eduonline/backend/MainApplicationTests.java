package com.eduonline.backend;


import javax.annotation.Resource;

import cn.hutool.core.util.RandomUtil;
import com.eduonline.backend.model.entity.*;
import com.eduonline.backend.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Date;

import static com.sun.javafx.font.FontResource.SALT;

/**
 * 主类测试
 *
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private CourseService courseService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseTestService courseTestService;

    @Resource
    private ArticleService articleService;

    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private CourseCommentLikeService courseCommentLikeService;

    @Resource
    private MsgSystemService msgSystemService;
//    @Resource
//    private WxOpenConfig wxOpenConfig;
//
//    @Test
//    void contextLoads() {
//        System.out.println(wxOpenConfig);
//    }
//
//
//    @Test
//    void insertUser() {
//        User user = new User();
//        user.setUserAccount("yyq123321");
//        user.setUserPassword("");
//    }

    @Test
    public void insertCourse() {
        for(int i = 1; i <= 10; i ++) {
            Course course = new Course();
            course.setCourseId("course_" + RandomUtil.randomString(30));
            course.setStatus(0);
            course.setTeacherId("teacher_0czha2qmh26kn0ggqzlvhyhvq6ne6g");
            course.setCourseName("course_" + i);
            course.setTitle("course_" + i);
            course.setLessonNum("" + i);
            course.setEndTime(new Date(124, 1, 1));
            courseService.save(course);
        }
    }

    @Test
    public void insertStudent() {
        for(int i=  1; i <= 100; i ++) {
            Student student = new Student();
            student.setId("student_" + RandomUtil.randomString(30));
            student.setUserName("student_1");
            student.setSex(1);
            String phone = "1880123" + (4567+ i);
            student.setPhone(phone);
            student.setPassword(DigestUtils.md5DigestAsHex((SALT + "123456").getBytes()));
            student.setPicImg("https://ts1.cn.mm.bing.net/th/id/R-C.1bd4619895b23e93c14ead740b9524ec?rik=IoGxECWc4wUyLA&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20181216%2f5ac69ccd7a5747c3896f3f6d08eebb88.jpeg&ehk=%2bRuckXoyVFZA0NqvaFYN4N83C9u%2bvU5PsizwK1It2Eg%3d&risl=&pid=ImgRaw&r=0");
            studentService.save(student);
        }
    }

    @Test
    public void insertTeacher() {
        for(int i=  1; i <= 100; i ++) {
            Teacher teacher = new Teacher();
            teacher.setId("teacher_" + RandomUtil.randomString(30));
            teacher.setName("teacher_1");
            teacher.setSex(1);
            String phone = "1890123" + (4567+ i);
            teacher.setPhone(phone);
            teacher.setPassword(DigestUtils.md5DigestAsHex((SALT + "123456").getBytes()));
            teacher.setIntroduction("cs");
            teacher.setMajor("cs");
            teacher.setSex(1);
            teacher.setEducation("cs");
            teacher.setPicPath("https://ts1.cn.mm.bing.net/th/id/R-C.1bd4619895b23e93c14ead740b9524ec?rik=IoGxECWc4wUyLA&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20181216%2f5ac69ccd7a5747c3896f3f6d08eebb88.jpeg&ehk=%2bRuckXoyVFZA0NqvaFYN4N83C9u%2bvU5PsizwK1It2Eg%3d&risl=&pid=ImgRaw&r=0");
            teacherService.save(teacher);
        }
    }

//    @Test
//    public void insertTest() {
//        for(int i = 1; i <= 5; i ++) {
//            CourseTest courseTest = new CourseTest();
//            courseTest.setId("course_test_" + RandomUtil.randomString(30));
//            courseTest.setCourseId("course_cselp94ycqg3x7p8qamzflwalqplfk");
//            courseTest.setQuestion("1 + 1 = 2");
//            courseTest.setAnswer(1);
//            courseTestService.save(courseTest);
//        }
//    }


    @Test
    public void insertArticle() {
        for(int i = 1; i <= 10; i ++) {
            Article article = new Article();
            article.setArticleId("article_" + RandomUtil.randomString(30));
            article.setContent("this is article" + i);
            article.setClickNum(i);
            article.setArticleType("cs");
            article.setTitle("cs" + i);
            article.setAuthorId("teacher_0czha2qmh26kn0ggqzlvhyhvq6ne6g");
            article.setImageUrl("https://ts1.cn.mm.bing.net/th/id/R-C.1bd4619895b23e93c14ead740b9524ec?rik=IoGxECWc4wUyLA&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20181216%2f5ac69ccd7a5747c3896f3f6d08eebb88.jpeg&ehk=%2bRuckXoyVFZA0NqvaFYN4N83C9u%2bvU5PsizwK1It2Eg%3d&risl=&pid=ImgRaw&r=0");
            article.setKeyWord("Lebron James");
            article.setSummary("King Lebron James");
            article.setPublishTime(new Date());
            articleService.save(article);
        }
    }

    @Test
    public void insertCourseChapterInfo() {
        String courseId = "course_317mmxqmr4rb4k9t3higem0sb0nm1v";
        for(int i = 1; i <= 10; i ++) {
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setCourseId(courseId);
            courseInfo.setId("course_info_" + RandomUtil.randomString(30));
            courseInfo.setContent("这里是课程章节" + i);
            courseInfo.setTitle("章节" + i);
            courseInfo.setChapterNum(i);
            courseInfo.setVideoLink("https://v.qq.com/x/cover/mzc00200jzft9jn/j0046hvcest.html");
            courseInfo.setInfoLink("mock");
            courseInfo.setIntro("this is Lebron James");
            courseInfoService.save(courseInfo);
        }
    }

    @Test
    public void insertCourseCommentLike() {
        CourseCommentLike courseCommentLike = new CourseCommentLike();
        courseCommentLike.setId("course_comment_like_" + RandomUtil.randomString(30));
        courseCommentLike.setCommentId("course_comment_06xa4ah8cb2xw8ux8ue5jvpdrvgkmv");
        courseCommentLike.setUserId("student_053quw8mbyv5a2q6hehvvtftidykt6");
        courseCommentLike.setStatus(1);
        courseCommentLikeService.save(courseCommentLike);
    }

    @Test
    public void insertSysMsg() {
        for(int i = 1; i <= 10; i ++) {
            MsgSystem msgSystem = new MsgSystem();
            msgSystem.setToId("student_053quw8mbyv5a2q6hehvvtftidykt6");
            msgSystem.setContent("decision" + i);
            msgSystem.setStatus(0);
            msgSystem.setId("msg_sys_" + RandomUtil.randomString(30));
            msgSystem.setAddTime(new Date());
            msgSystem.setUpdateTime(new Date());
            msgSystemService.save(msgSystem);
        }
    }
    private Teacher teacher;
    @BeforeEach
    public void init() {
        teacher = new Teacher();
        System.out.println("初始化成功");
    }
    @Test
    public void insert() {

        //teacher.setId("teacher_" + RandomUtil.randomString(30));
        teacher.setId("teacher_zitkatxw1tate95ifp006tk764gpxw");
        teacher.setName("布朗尼");
        teacher.setSex(1);
        String phone = "18901294574";
        teacher.setPhone(phone);
        teacher.setPassword(DigestUtils.md5DigestAsHex((SALT + "123456").getBytes()));
        teacher.setIntroduction("我是人工智能学院教授布老师，主要研究方向为计算机视觉");
        teacher.setMajor("Computer Science");
        teacher.setSex(1);
        teacher.setEducation("东南大学硕士毕业");
        teacher.setPicPath("https://ts1.cn.mm.bing.net/th/id/R-C.1bd4619895b23e93c14ead740b9524ec?rik=IoGxECWc4wUyLA&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20181216%2f5ac69ccd7a5747c3896f3f6d08eebb88.jpeg&ehk=%2bRuckXoyVFZA0NqvaFYN4N83C9u%2bvU5PsizwK1It2Eg%3d&risl=&pid=ImgRaw&r=0");
        teacherService.save(teacher);
    }
    @AfterEach
    public void destroy() {
        teacher = null;
        teacherService.removeById("teacher_zitkatxw1tate95ifp006tk764gpxw");
        System.out.println("销毁成功");
    }
}
