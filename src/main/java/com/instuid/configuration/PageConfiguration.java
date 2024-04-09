package com.instuid.configuration;


import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConfiguration {

    @Bean
    public PaginationInnerInterceptor PaginationInnerInterceptor(){
        return new PaginationInnerInterceptor();
    }
}
