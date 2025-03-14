package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.common.utils.JsonUtil;
import itzhuo.system.dao.model.system.SystemMenuCrFrom;
import itzhuo.system.dao.model.system.SystemMenuListVO;
import itzhuo.system.service.SystemMenuService;
import jakarta.annotation.Resource;
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
    public ActionResult<String> create(@RequestBody SystemMenuCrFrom systemMenuCrFrom){
        try {
            systemMenuService.createMenu(systemMenuCrFrom);
            return ActionResult.success("菜单创建成功！");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

}
