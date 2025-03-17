package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.common.result.PageListVO;
import itzhuo.common.result.PaginationVO;
import itzhuo.common.utils.JsonUtil;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.user.UserListVO;
import itzhuo.system.dao.model.user.UserPagination;
import itzhuo.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

//    @GetMapping("/pageTest")
//    public ActionResult<List<UserListVO>> pageTset(@RequestParam(defaultValue = "1") int pageNum,
//                                               @RequestParam(defaultValue = "10") int pageSize,
//                                               @RequestParam(required = false) String userName){
//        try {
//            List<UserEntity> list = userService.getListTest(pageNum, pageSize, userName);
//            List<UserListVO> listVOS = JsonUtil.getJsonToList(list, UserListVO.class);
//            System.out.println(listVOS);
//            return ActionResult.success(listVOS);
//        } catch (Exception e) {
//            return ActionResult.fail(e.getMessage());
//        }
//    }


    @GetMapping("/page")
    public ActionResult<PageListVO<UserListVO>> page(UserPagination pagination){
        try {
            List<UserEntity> list = userService.getList(pagination);
            List<UserListVO> listVOS = JsonUtil.getJsonToList(list, UserListVO.class);
            PaginationVO vo = JsonUtil.getJsonToBean(pagination, PaginationVO.class);
            return ActionResult.page(listVOS, vo);
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
}
