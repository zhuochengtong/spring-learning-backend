package itzhuo.system.dao.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SystemMenuListVO implements Serializable {
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
    private List<SystemMenuListVO> children = new ArrayList<>();
}
