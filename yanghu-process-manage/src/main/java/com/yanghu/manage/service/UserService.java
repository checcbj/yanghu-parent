package com.yanghu.manage.service;


import com.yanghu.manage.entity.User;

import java.util.List;

public interface UserService {



    List<User> findUserList();

    User findOneUserById(Long id);

    User findOneUserByName(String userName);
}
