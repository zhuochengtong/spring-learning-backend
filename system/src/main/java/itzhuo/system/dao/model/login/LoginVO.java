package itzhuo.system.dao.model.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录参数")
public class LoginVO {
    @Schema(description="用户名")
    private String username;
    @Schema(description="密码")
    private String password;
    @Schema(description="验证码key")
    private String captchaKey;
    @Schema(description="验证码code")
    private String captchaCode;
}
