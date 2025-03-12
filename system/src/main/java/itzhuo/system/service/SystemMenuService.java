package itzhuo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import itzhuo.system.dao.entity.SystemMenuEntity;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.system.SystemMenuListVO;

import java.util.List;

public interface SystemMenuService extends IService<SystemMenuEntity> {

    /**
     * 查询所有菜单树
     * @return 菜单列表
     */
    List<SystemMenuEntity> selectMenuTreeAll();
}
