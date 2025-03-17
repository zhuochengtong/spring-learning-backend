package itzhuo.system.dao.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class UserListVO implements Serializable {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private LocalDateTime lastLogin;
    private Integer isEnabled;
    private Integer isLocked;
    private LocalDateTime creatorTime;
    private String creatorUserId;
    private LocalDateTime lastModifyTime;
    private String lastModifyUserId;
}
