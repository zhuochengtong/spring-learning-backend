package itzhuo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.user.UserCrForm;
import itzhuo.system.dao.model.user.UserPagination;
import itzhuo.system.dao.model.user.UserUpForm;

import java.util.List;

public interface UserService extends IService<UserEntity> {
//    /**
//     * 获取用户列表（一般的分页写法）
//     * @param pageNum
//     * @param pageSize
//     */
//    List<UserEntity> getListTest(int pageNum, int pageSize, String userName);

    /**
     * 获取用户列表（封装分页参数）
     * @param userPagination
     * @return
     */
    List<UserEntity> getList(UserPagination userPagination);

    /**
     * 创建用户
     * @param userCrForm
     */
    void create(UserCrForm userCrForm);

    /**
     * 更新用户
     * @param id
     * @param userUpForm
     */
    void update(String id, UserUpForm userUpForm);

    /**
     * 删除用户
     * @param id
     */
    void delete(String id);

    /**
     * 批量删除用户
     * @param ids
     */
    void deleteBatch(List<String> ids);


}
