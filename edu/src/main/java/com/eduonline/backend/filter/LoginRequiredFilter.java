package com.eduonline.backend.filter;

import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.eduonline.backend.constant.UserConstant.*;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/9/1 11:07
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginRequiredFilter implements Filter {

    @Resource
    private StringRedisTemplate redisTemplate;

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response  =(HttpServletResponse)servletResponse;
        //获取URI
        String requestURI = request.getRequestURI();

        //不需要处理的路径
        String[] urls = new String[] {
                "/api/student/login",
                "/api/teacher/login",
                "/api/admin/login",
                "/api/student/register",
                "/api/teacher/register",
        };
        //检测请求是否需要处理
        boolean check = check(urls, requestURI);
        //如果不需要处理
        if(check) {
            log.info("无需处理{}",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

//        //token验证登录
//        String token = request.getHeader("Authorization");
//        if(token == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
//        }
//        //校验
//        String tokenValue = redisTemplate.opsForValue().get(String.format(TOKEN, token));
//        if(tokenValue == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
//        }
//        redisTemplate.opsForValue().set(String.format(TOKEN, token), tokenValue, EXPIRE, TimeUnit.MINUTES);

        //判断登录状态,如果登录就放行
        if (request.getSession().getAttribute(USER_LOGIN_STATE) != null) {
            log.info("用户已经登陆:{}",request.getSession().getAttribute(USER_LOGIN_STATE));
            filterChain.doFilter(request,response);
            return;
        } else {
            //未登录抛异常
            request.setAttribute("exception", new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录"));
            request.getRequestDispatcher("/error/login").forward(request, response);
        }
    }

    /**
     * 路径匹配,检查请求是否需要放行
     * @param requestURI
     * @param urls
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for(String url: urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) {
                return true;
            }
        }
        return false;
    }
}
