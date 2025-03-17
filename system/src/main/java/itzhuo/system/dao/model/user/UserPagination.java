package itzhuo.system.dao.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import itzhuo.common.result.Pagination;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserPagination extends Pagination implements Serializable {
    /**
     * 智能体的名称
     */
    @Schema(description = "姓名")
    private String username;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Integer isEnabled;
}
