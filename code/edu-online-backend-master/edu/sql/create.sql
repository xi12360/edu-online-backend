/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 5.7.19 : Database - edu_online
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`edu_online` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `edu_online`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
                         `id` varchar(127) NOT NULL COMMENT '管理员id',
                         `password` varchar(56) NOT NULL COMMENT '密码',
                         `phone` varchar(56) NOT NULL COMMENT '手机号',
                         `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
                         `name` varchar(127) NOT NULL DEFAULT 'admin' COMMENT '名称',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

/*Data for the table `admin` */

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
                           `article_id` varchar(127) NOT NULL COMMENT '文章id',
                           `title` varchar(56) NOT NULL COMMENT '文章标题',
                           `summary` varchar(256) DEFAULT NULL COMMENT '文章概要',
                           `key_word` varchar(56) DEFAULT NULL COMMENT '关键词',
                           `image_url` varchar(127) DEFAULT NULL COMMENT '封面图片链接',
                           `source` varchar(127) DEFAULT NULL COMMENT '文章来源',
                           `author_id` varchar(127) NOT NULL COMMENT '文章作者id',
                           `publish_time` datetime NOT NULL COMMENT '发布时间',
                           `article_type` varchar(56) NOT NULL COMMENT '文章类型',
                           `click_num` int(11) NOT NULL DEFAULT '0' COMMENT '点击数量',
                           `praise_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数量',
                           `comment_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
                           `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删 1已删)',
                           PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章';

/*Data for the table `article` */

/*Table structure for table `article_favorites` */

DROP TABLE IF EXISTS `article_favorites`;

CREATE TABLE `article_favorites` (
                                     `id` varchar(127) NOT NULL COMMENT 'id',
                                     `article_id` varchar(127) NOT NULL COMMENT '文章id',
                                     `user_id` varchar(127) NOT NULL COMMENT '用户id',
                                     `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                                     `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章收藏表';

/*Data for the table `article_favorites` */

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
                          `course_id` varchar(127) NOT NULL COMMENT '课程id',
                          `course_name` varchar(127) NOT NULL COMMENT '课程名',
                          `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                          `subject_link` varchar(127) DEFAULT NULL COMMENT '项目链接',
                          `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                          `source_price` int(11) NOT NULL DEFAULT '0' COMMENT '原价格',
                          `current_price` int(11) NOT NULL DEFAULT '0' COMMENT '当前价格',
                          `title` varchar(56) NOT NULL COMMENT '标题',
                          `lesson_num` varchar(127) NOT NULL COMMENT '课程号',
                          `logo` varchar(127) DEFAULT NULL COMMENT '封面LOGO',
                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                          `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
                          `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '浏览数量',
                          `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                          `teacher_id` varchar(127) NOT NULL COMMENT '教师id',
                          `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                          PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程';

/*Data for the table `course` */

/*Table structure for table `course_comment` */

DROP TABLE IF EXISTS `course_comment`;

CREATE TABLE `course_comment` (
                                  `comment_id` varchar(127) NOT NULL COMMENT '评论ID',
                                  `course_id` varchar(127) NOT NULL COMMENT '课程ID',
                                  `user_id` varchar(127) NOT NULL COMMENT '用户ID',
                                  `p_comment_id` varchar(127) NOT NULL COMMENT '父级评论id',
                                  `content` varchar(127) NOT NULL COMMENT '评论内容',
                                  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
                                  `other_id` varchar(127) NOT NULL COMMENT '被评论人ID',
                                  `praise_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
                                  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
                                  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                                  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `course_comment` */

/*Table structure for table `course_favor` */

DROP TABLE IF EXISTS `course_favor`;

CREATE TABLE `course_favor` (
                                `id` varchar(127) NOT NULL COMMENT '购买id',
                                `course_id` varchar(127) NOT NULL COMMENT '课程id',
                                `user_id` varchar(127) NOT NULL COMMENT '用户id',
                                `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
                                `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程收藏表';

/*Data for the table `course_favor` */

/*Table structure for table `course_info` */

DROP TABLE IF EXISTS `course_info`;

CREATE TABLE `course_info` (
                               `id` varchar(127) NOT NULL COMMENT '资料id',
                               `course_id` varchar(127) NOT NULL COMMENT '课程id',
                               `info_link` varchar(127) NOT NULL COMMENT '资料链接',
                               `info_type` int(11) NOT NULL COMMENT '资料类型',
                               `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程信息';

/*Data for the table `course_info` */

/*Table structure for table `course_test` */

DROP TABLE IF EXISTS `course_test`;

CREATE TABLE `course_test` (
                               `id` varchar(127) NOT NULL COMMENT '测试题id',
                               `course_id` varchar(127) NOT NULL COMMENT '课程id',
                               `question` varchar(512) NOT NULL COMMENT '问题',
                               `answer` int(11) NOT NULL COMMENT '答案',
                               `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试题';

/*Data for the table `course_test` */

/*Table structure for table `forum_comment` */

DROP TABLE IF EXISTS `forum_comment`;

CREATE TABLE `forum_comment` (
                                 `comment_id` varchar(127) NOT NULL COMMENT '评论id',
                                 `article_id` varchar(127) NOT NULL COMMENT '文章id',
                                 `user_id` varchar(127) NOT NULL COMMENT '用户id',
                                 `p_comment_id` varchar(127) NOT NULL COMMENT '父级评论id',
                                 `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
                                 `comment` varchar(512) NOT NULL COMMENT '评论内容',
                                 `other_id` varchar(127) NOT NULL COMMENT '被评论人id',
                                 `praise_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
                                 `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数',
                                 `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                                 PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='论坛评论';

/*Data for the table `forum_comment` */

/*Table structure for table `msg_system` */

DROP TABLE IF EXISTS `msg_system`;

CREATE TABLE `msg_system` (
                              `id` varchar(127) NOT NULL COMMENT 'id',
                              `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `content` varchar(512) NOT NULL COMMENT '内容',
                              `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                              `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息';

/*Data for the table `msg_system` */

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
                           `user_id` varchar(127) NOT NULL COMMENT '用户ID',
                           `phone` varchar(127) NOT NULL COMMENT '手机',
                           `email` varchar(127) DEFAULT NULL COMMENT '邮箱',
                           `password` varchar(127) NOT NULL COMMENT '密码',
                           `user_name` varchar(127) NOT NULL COMMENT '名称',
                           `sex` int(11) DEFAULT NULL COMMENT '性别',
                           `grade` varchar(30) DEFAULT NULL COMMENT '年级',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                           `status` int(11) NOT NULL COMMENT '用户状态',
                           `pic_img` varchar(127) DEFAULT NULL COMMENT '图片链接',
                           `major` varchar(127) DEFAULT NULL COMMENT '专业',
                           `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                           PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `student` */

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
                           `id` varchar(127) NOT NULL COMMENT '教师ID',
                           `name` varchar(127) NOT NULL COMMENT '名称',
                           `education` varchar(127) DEFAULT NULL COMMENT '所属机构',
                           `major` varchar(127) NOT NULL COMMENT '教授专业',
                           `pic_path` varchar(127) DEFAULT NULL COMMENT '图片链接',
                           `status` int(11) NOT NULL COMMENT '状态',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `introduction` varchar(512) DEFAULT NULL COMMENT '个人简介',
                           `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删1已删)',
                           `phone` varchar(56) NOT NULL COMMENT '手机号',
                           `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
                           `password` varchar(127) NOT NULL COMMENT '密码',
                           `email` varchar(127) DEFAULT NULL COMMENT '邮箱',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `teacher` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
