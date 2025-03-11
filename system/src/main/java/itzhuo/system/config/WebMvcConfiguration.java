package itzhuo.system.config;

import itzhuo.system.interceptor.AuthenticationInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类，用于配置拦截器和格式化器。
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * 指定拦截器应该拦截的路径
     */
    @Value("${system.auth.path-patterns.include}")
    private String[] includePathPatterns;

    /**
     * 指定拦截器应该排除的路径
     */
    @Value("${system.auth.path-patterns.exclude}")
    private String[] excludePathPatterns;

    /**
     * 添加拦截器并配置拦截和排除的路径模式。
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/captcha","/login");
    }
}