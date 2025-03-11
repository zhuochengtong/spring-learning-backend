package itzhuo.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示当前登录用户的信息。
 * 包含用户的唯一标识符（userId）和用户名（username）。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String userId;
    private String username;
}