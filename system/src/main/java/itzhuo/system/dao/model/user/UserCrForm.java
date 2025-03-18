package itzhuo.system.dao.model.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserCrForm implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer isEnabled;
}
