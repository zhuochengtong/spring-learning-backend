package itzhuo.system.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import itzhuo.common.context.LoginUser;
import itzhuo.common.context.LoginUserContext;
import itzhuo.common.exception.BizException;
import itzhuo.common.utils.JsonUtil;
import itzhuo.common.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.lang.model.element.NestingKind;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public RefreshTokenInterceptor(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取请求头中的token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 2、基于token获取redis中的用户信息
        String key = "login:" + token;
        Object userObject = redisTemplate.opsForValue().get(key);
        HashMap<String, Object> userMap = (HashMap<String, Object>) redisTemplate.opsForValue().get(key);
        System.out.println(userMap);
        // 3、判断用户是否存在
        if (userMap.isEmpty()) {
            return true;
        }
        // 4、将查询到的hash数据转为UserDTO
        LoginUser loginUser = JsonUtil.getJsonToBean(userMap, LoginUser.class);
        // 5、存在，将用户保存到 ThreadLocal
        LoginUserContext.setLoginUser(loginUser);
        // 6、刷新 token 有效期
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        // 7、放行
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
        LoginUserContext.clear();
    }
}
