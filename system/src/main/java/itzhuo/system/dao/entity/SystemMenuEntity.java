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
    @TableId("id")
    private String id;
    @TableField("parent_id")
    private String parentId;
    @TableField("name")
    private String name;
    @TableField("title")
    private String title;
    @TableField("sort")
    private Integer sort;
    @TableField("icon")
    private String icon;
    @TableField("path")
    private String path;
    @TableField("component")
    private String component;
    @TableField("perms")
    private String perms;
    @TableField("status")
    private Integer status;
    @TableField("creator_time")
    private LocalDateTime creatorTime;
    @TableField("creator_user_id")
    private String creatorUserId;
    @TableField("last_modify_time")
    private LocalDateTime lastModifyTime;
    @TableField("last_modify_user_id")
    private String lastModifyUserId;
    @TableField("delete_time")
    private LocalDateTime deleteTime;
    @TableField("delete_user_id")
    private String deleteUserId;
    @TableField("delete_mark")
    private Integer deleteMark;
    @TableField(exist = false)
    private List<SystemMenuEntity> children = new ArrayList<>();
}
