package itzhuo.system.dao.entity;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

@Data
@TableName("base_users")
public class UserEntity {
    @TableId
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
    private LocalDateTime deleteTime;
    private String deleteUserId;
    private Integer deleteMark;
}
