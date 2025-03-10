package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.system.dao.model.login.CaptchaVO;
import itzhuo.system.dao.model.login.LoginVO;
import itzhuo.system.dao.model.user.UserInfoVO;
import itzhuo.system.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/captcha")
    public ActionResult<CaptchaVO> login(){
        try {
            return ActionResult.success(loginService.getCaptcha());
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    /**
     * 登录
     * @param parameters
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ActionResult<LoginVO> login(@RequestParam Map<String, String> parameters){
        try {
            return ActionResult.success(loginService.login(parameters));
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActionResult<UserInfoVO> getUserInfo(@PathVariable("id") String id){
        try {
            return ActionResult.success(loginService.getUserInfo(id));
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
}
