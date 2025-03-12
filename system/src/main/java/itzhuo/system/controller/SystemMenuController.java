package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.system.dao.entity.SystemMenuEntity;
import itzhuo.system.service.SystemMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
public class SystemMenuController {

    @Resource
    private SystemMenuService systemMenuService;

    @GetMapping("/list")
    public ActionResult<List<SystemMenuEntity>> list(){
        try {
            List<SystemMenuEntity> list = systemMenuService.selectMenuTreeAll();
            return ActionResult.success(list);
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
}
