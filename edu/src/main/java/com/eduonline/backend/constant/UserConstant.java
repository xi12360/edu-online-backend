package com.eduonline.backend.constant;

/**
 * 用户常量
 *
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";
    /**
     * 教师角色
     */
    String TEACHER_ROLE = "teacher";
    /**
     * 学生角色
     */
    String STUDENT_ROLE = "student";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    String TOKEN = "token_";

    int EXPIRE = 60;

    // endregion
}
