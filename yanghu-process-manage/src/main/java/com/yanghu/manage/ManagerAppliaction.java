package com.yanghu.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2021/2/9
 */
@SpringBootApplication
@MapperScan("com.yanghu.manage.mapper")
public class ManagerAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(ManagerAppliaction.class, args);
    }
}
