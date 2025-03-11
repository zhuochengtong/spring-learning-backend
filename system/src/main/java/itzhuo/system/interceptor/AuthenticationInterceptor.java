package itzhuo.system.interceptor;

import io.jsonwebtoken.Claims;
import itzhuo.common.context.LoginUser;
import itzhuo.common.context.LoginUserContext;
import itzhuo.common.exception.BizException;
import itzhuo.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Spring MVC拦截器，用于在处理请求之前进行身份验证
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 在处理请求之前进行身份验证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("--------Pre Handle method is Calling-------------");
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            throw new BizException("token不存在,未登录");
        }
        if (!authHeader.startsWith("Bearer ")) {
            throw new BizException("非法Token格式");
        }
        // 提取并去除 Bearer 前缀(这是在前端拼接的前缀)
        String token = authHeader.substring(7);
        Claims claims = JwtUtil.parseToken(token);
        String userId = claims.get("userId", String.class);
        String username = claims.get("username", String.class);
        LoginUserContext.setLoginUser(new LoginUser(userId, username));
        return true;
    }

    /**
     * 在处理请求之后清除登录用户信息
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("--------After Completion method is Calling--------");
        LoginUserContext.clear();
    }
}