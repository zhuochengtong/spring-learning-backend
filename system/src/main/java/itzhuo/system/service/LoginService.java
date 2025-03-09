package itzhuo.system.service;

import itzhuo.system.dao.model.login.CaptchaVO;
import itzhuo.system.dao.model.login.LoginVO;
import itzhuo.system.dao.model.user.UserInfoVO;

public interface LoginService {
    CaptchaVO getCaptcha();

    String login(LoginVO loginVO) throws Exception;

    UserInfoVO getUserInfo(String userId);
}
