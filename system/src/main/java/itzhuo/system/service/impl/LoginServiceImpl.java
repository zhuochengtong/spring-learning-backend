package itzhuo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import io.netty.util.internal.StringUtil;
import itzhuo.common.utils.JwtUtil;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.login.CaptchaVO;
import itzhuo.system.dao.model.login.LoginVO;
import itzhuo.system.dao.model.user.UserInfoVO;
import itzhuo.system.service.LoginService;
import itzhuo.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserService userService;

    /**
     * 获取验证码
     * @return
     */
    @Override
    public CaptchaVO getCaptcha() {
        // 1、创建图形验证码对象
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 2、获取图新验证码并转为小写
        String verCode = specCaptcha.text().toLowerCase();
        // 3、生成随机key
        String key = UUID.randomUUID().toString();
        // 4、将验证码存入redis 30分钟
        redisTemplate.opsForValue().set(key,verCode,30, TimeUnit.MINUTES);
        // 5、返回图片地址和验证码的key
        return new CaptchaVO(specCaptcha.toBase64(),key);

    }

    @Override
    public String login(LoginVO loginVO) throws Exception {
        // 1、判断是否是入了验证码
        if (!StringUtils.hasText(loginVO.getCaptchaCode())) {
            throw new RuntimeException("未输入验证码！");
        }
        // 2、校验验证码
        if (!loginVO.getCaptchaCode().equals(redisTemplate.opsForValue().get(loginVO.getCaptchaKey()))) {
            throw new RuntimeException("验证码错误！");
        }
        // 3、校验用户名密码
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername,loginVO.getUsername()).eq(UserEntity::getPassword,DigestUtils.sha256Hex(loginVO.getPassword()));
        System.out.println(DigestUtils.sha256Hex(loginVO.getPassword()));
        UserEntity userEntity = userService.getOne(wrapper);
        if (userEntity == null) {
            throw new RuntimeException("用户名或密码错误！");
        }
        // 4、生成token
        return JwtUtil.createToken(userEntity.getId(),userEntity.getUsername());
    }

    @Override
    public UserInfoVO getUserInfo(String userId) {
        UserEntity userEntity = userService.getById(userId);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userEntity,userInfoVO);
        return userInfoVO;
    }
}
