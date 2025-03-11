package itzhuo.system.dao.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息")
public class UserInfoVO {
    @Schema(description = "用户ID")
    private String id;

    @Schema(description = "用户姓名")
    private String username;

    @Schema(description = "用户头像")
    private String avatar;
}
