package itzhuo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.user.UserPagination;

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
}
