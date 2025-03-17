package itzhuo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.dao.model.user.UserPagination;
import itzhuo.system.mapper.UserMapper;
import itzhuo.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
//    @Override
//    public List<UserEntity> getListTest(int pageNum, int pageSize, String userName) {
//        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotBlank(userName)) {
//            wrapper.like(UserEntity::getUsername,userName);
//        }
//        Page<UserEntity> page = new Page<>(pageNum, pageSize);
//        IPage<UserEntity> Ipage = this.page(page,wrapper);
//        return Ipage.getRecords();
//    }

    @Override
    public List<UserEntity> getList(UserPagination pagination) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pagination.getUsername())) {
            wrapper.like(UserEntity::getUsername,pagination.getUsername());
        }
        Page<UserEntity> page = new Page<>(pagination.getCurrentPage(), pagination.getPageSize());
        IPage<UserEntity> Ipage = this.page(page,wrapper);
        return pagination.setData(Ipage.getRecords(),Ipage.getTotal());
    }
}
