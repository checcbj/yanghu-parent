package com.yanghu.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanghu.manage.entity.User;
import com.yanghu.manage.mapper.UserMapper;
import com.yanghu.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.NewThreadAction;

import java.util.List;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/9
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUserList() {
        return userMapper.selectList(null);
    }

    @Override
    public User findOneUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findOneUserByName(String userName) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername,userName));
    }
}
