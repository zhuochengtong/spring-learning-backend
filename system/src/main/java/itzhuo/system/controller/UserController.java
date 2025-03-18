package itzhuo.system.controller;

import itzhuo.common.result.ActionResult;
import itzhuo.common.result.PageListVO;
import itzhuo.common.result.PaginationVO;
import itzhuo.common.utils.JsonUtil;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.user.UserCrForm;
import itzhuo.system.dao.model.user.UserListVO;
import itzhuo.system.dao.model.user.UserPagination;
import itzhuo.system.dao.model.user.UserUpForm;
import itzhuo.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ActionResult<UserListVO> info(@PathVariable String id){
        try {
            UserEntity userEntity = userService.getById(id);
            UserListVO vo = JsonUtil.getJsonToBean(userEntity, UserListVO.class);
            return ActionResult.success(vo);
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ActionResult<String> create(@RequestBody UserCrForm userCrForm){
        try {
            userService.create(userCrForm);
            return ActionResult.success("创建用户成功");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ActionResult<String> update(@PathVariable String id,@RequestBody UserUpForm userUpForm){
        try {
            userService.update(id,userUpForm);
            return ActionResult.success("更新用户成功");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ActionResult<String> delete(@PathVariable String id){
        try {
            userService.delete(id);
            return ActionResult.success("删除用户成功");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }

    @PostMapping("/deleteBatch")
    public ActionResult<String> deleteBatch(@RequestBody List<String> ids){
        try {
            userService.deleteBatch(ids);
            return ActionResult.success("批量删除用户成功");
        } catch (Exception e) {
            return ActionResult.fail(e.getMessage());
        }
    }
}
