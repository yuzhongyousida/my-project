package com.myself.bserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {


    /**
     * 添加encodingFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean encodingFilter(){
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("encoding", "utf-8");

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharacterEncodingFilter());
        registration.setName("encodingFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(3);
        return registration;
    }
}
