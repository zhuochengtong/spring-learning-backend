package itzhuo.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import itzhuo.system.dao.entity.UserEntity;
import itzhuo.system.mapper.UserMapper;
import itzhuo.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
