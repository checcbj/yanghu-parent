package com.yanghu.manage.controller;


import com.yanghu.manage.entity.User;
import com.yanghu.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/findAll")
    public List<User> findUserList(){
        return userService.findUserList();
    }

    /**
     * 查询单个用户
     * @param userId
     * @return
     */
    @GetMapping("/findOne/{id}")
    public User findUserList(@PathVariable(name = "id")Long userId){
        return userService.findOneUserById(userId);
    }
}
