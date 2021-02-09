//package com.yanghu.manage.config;
//
//import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author RunningGan
// */
//@Configuration
//@MapperScan(basePackages = {"com.yanghu.manage.mapper"})
//public class MybatisPlusConfig {
//
////    /**
////     * 分页插件
////     */
////    @Bean
////    public PaginationInterceptor paginationInterceptor() {
//////        return new PaginationInterceptor().setDialectType("oracle");
//////        return new PaginationInterceptor().setDialectType("postgresql");
////        return new PaginationInterceptor().setDialectType("mysql");
////    }
//
//    /**
//     * 乐观锁插件
//     *
//     * @return
//     */
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
//    }
//
//}
