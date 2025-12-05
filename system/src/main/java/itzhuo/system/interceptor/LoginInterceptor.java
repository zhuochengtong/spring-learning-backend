package itzhuo.system.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import itzhuo.common.context.LoginUser;
import itzhuo.common.context.LoginUserContext;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、判断是否需要拦截（ThreadLocal中是否有用户）
        if (LoginUserContext.getLoginUser() == null) {
            // 没有，需要拦截
            response.setStatus(401);
            // 拦截
            return false;
        }
        // 有用户，则放行
        return true;
    }
}
