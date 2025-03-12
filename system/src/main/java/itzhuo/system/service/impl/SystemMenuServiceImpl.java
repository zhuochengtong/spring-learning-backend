package itzhuo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import itzhuo.system.dao.entity.SystemMenuEntity;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.system.SystemMenuListVO;
import itzhuo.system.mapper.SystemMenuMapper;
import itzhuo.system.mapper.UserMapper;
import itzhuo.system.service.SystemMenuService;
import itzhuo.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenuEntity> implements SystemMenuService {


    /**
     * 获取菜单树
     * @return
     */
    @Override
    public List<SystemMenuEntity> selectMenuTreeAll() {
        // 阶段1：获取基础数据
        // 查询所有状态为启用（status=1）的菜单，并按sort字段升序排序
        List<SystemMenuEntity> menus = this.list(
                new LambdaQueryWrapper<SystemMenuEntity>()
                        .eq(SystemMenuEntity::getStatus, 1)  // 状态1表示启用状态
                        .orderByAsc(SystemMenuEntity::getSort)  // 按排序字段升序排列
        );
        // 阶段2：构建树形结构
        List<SystemMenuEntity> rootMenus = new ArrayList<>();
        // 步骤2.1：筛选根节点（约定parentId=0表示根菜单）
        for (SystemMenuEntity menu : menus) {
            if (menu.getParentId().equals("0")) {  // 使用字符串类型的父ID比较
                rootMenus.add(menu);  // 将根节点加入列表
            }
        }
        // 步骤2.2：为每个根节点构建子树
        for (SystemMenuEntity rootMenu : rootMenus) {
            // 递归获取所有层级的子菜单
            List<SystemMenuEntity> children = getChild(rootMenu.getId(), menus);
            rootMenu.setChildren(children);  // 设置子树到当前根节点
        }
        return rootMenus;  // 返回完整的树形结构
    }

    /**
     * 递归构建子菜单树
     * @param id    当前处理的菜单节点ID
     * @param menus 所有可用菜单的完整列表
     * @return      具有层级结构的子菜单树（当无子节点时返回null）
     */
    private List<SystemMenuEntity> getChild(String id, List<SystemMenuEntity> menus) {
        // 步骤1：获取直接子节点
        List<SystemMenuEntity> childList = new ArrayList<>();
        for (SystemMenuEntity menu : menus) {
            // 筛选出父ID等于当前节点ID的菜单项
            if (menu.getParentId().equals(id)) {
                childList.add(menu);  // 加入直接子节点列表
            }
        }
        // 步骤2：递归处理子节点的子节点
        for (SystemMenuEntity menu : childList) {
            // 递归调用获取当前菜单项的子菜单
            List<SystemMenuEntity> grandchildren = getChild(menu.getId(), menus);
            // 注意：当返回null时，setChildren()可能不会创建空列表字段（取决于JSON序列化配置）
            menu.setChildren(grandchildren);
        }
        // 处理空列表的特殊情况
        return childList.isEmpty() ? null : childList;  // 返回null避免生成空数组
    }

}
