package com.example.demo.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Slf4j
@Configuration
public class FilterConfig {
    @Bean
    FilterRegistrationBean<CramSchoolIdFilter> registerCorrectFilter(){

        FilterRegistrationBean<CramSchoolIdFilter> regBean = new FilterRegistrationBean<>();

        regBean.setFilter(new CramSchoolIdFilter());

        regBean.addUrlPatterns("/login");

        regBean.setOrder(Ordered.HIGHEST_PRECEDENCE);


        return regBean;
    }
}
