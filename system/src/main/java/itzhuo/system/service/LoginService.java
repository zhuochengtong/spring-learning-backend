package itzhuo.system.service;

import itzhuo.system.dao.model.login.CaptchaVO;
import itzhuo.system.dao.model.login.LoginVO;
import itzhuo.system.dao.model.user.UserInfoVO;

import java.util.Map;

public interface LoginService {
    CaptchaVO getCaptcha();

    LoginVO login(Map<String, String> parameters) throws Exception;

    UserInfoVO getUserInfo(String userId);
}
