package itzhuo.system.dao.model.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemMenuCrFrom implements Serializable {
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
}
