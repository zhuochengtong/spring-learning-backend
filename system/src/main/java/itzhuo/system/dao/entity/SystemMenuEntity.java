package itzhuo.system.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import itzhuo.system.dao.model.system.SystemMenuListVO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("base_sys_menu")
public class SystemMenuEntity implements Serializable {
    @TableId
    private String id;
    private String parentId;
    private String name;
    private String title;
    private Integer sort;
    private String icon;
    private String path;
    private String component;
    private String perms;
    private Integer status;
    private LocalDateTime creatorTime;
    private String creatorUserId;
    private LocalDateTime lastModifyTime;
    private String lastModifyUserId;
    private LocalDateTime deleteTime;
    private String deleteUserId;
    private Integer deleteMark;
    @TableField(exist = false)
    private List<SystemMenuEntity> children = new ArrayList<>();
}
