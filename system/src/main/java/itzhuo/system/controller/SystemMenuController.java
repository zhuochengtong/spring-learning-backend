package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.common.utils.JsonUtil;
import itzhuo.system.dao.entity.SystemMenuEntity;
import itzhuo.system.dao.model.system.SystemMenuCrForm;
import itzhuo.system.dao.model.system.SystemMenuInfoVO;
import itzhuo.system.dao.model.system.SystemMenuListVO;
import itzhuo.system.dao.model.system.SystemMenuUpForm;
import itzhuo.system.service.SystemMenuService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
public class SystemMenuController {

    @Resource
    private SystemMenuService systemMenuService;

    @GetMapping("/list")
    public ActionResult<List<SystemMenuListVO>> list(){
        try {
            List<SystemMenuListVO> list = JsonUtil.getJsonToList(systemMenuService.selectMenuTreeAll(), SystemMenuListVO.class);
            return ActionResult.success(list);
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ActionResult<String> create(@RequestBody SystemMenuCrForm systemMenuCrForm){
        try {
            SystemMenuEntity menuEntity = JsonUtil.getJsonToBean(systemMenuCrForm, SystemMenuEntity.class);
            systemMenuService.save(menuEntity);
            return ActionResult.success("菜单创建成功！");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ActionResult<SystemMenuInfoVO> info(@PathVariable("id") String id){
        try {
            SystemMenuEntity entity = systemMenuService.getById(id);
            SystemMenuInfoVO infoVO = JsonUtil.getJsonToBean(entity, SystemMenuInfoVO.class);
            return ActionResult.success(infoVO);
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ActionResult<String> update(@PathVariable("id") String id, @RequestBody SystemMenuUpForm systemMenuUpForm){
        try {
            SystemMenuEntity menuEntity = JsonUtil.getJsonToBean(systemMenuUpForm, SystemMenuEntity.class);
            menuEntity.setId(id);
            systemMenuService.updateById(menuEntity);
            return ActionResult.success("菜单更新成功！");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<String> delete(@PathVariable("id") String id){
        try {
            systemMenuService.removeById(id);
            return ActionResult.success("菜单删除成功！");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PostMapping("/batchDelete")
    public ActionResult<String> batchDelete(@RequestBody List<String> ids){
        try {
            systemMenuService.removeByIds(ids);
            return ActionResult.success("菜单批量删除成功！");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
}
