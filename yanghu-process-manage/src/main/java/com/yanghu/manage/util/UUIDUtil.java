package com.yanghu.manage.util;

import java.util.UUID;

/**
 * @description:
 * @Author: yuzhifeng
 * @Date: Create In 2020/9/10
 */
public class UUIDUtil {

    public static String getUUID(){
        int count = 1;
        String id="";
        for (int i = 0; i < count; i++) {
            id = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(id);
        }
        return id;
    }

    public static void main(String[] args) {
        getUUID();
    }

}
