package com.example.bcp.utils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/secure/*");
        registrationBean.addUrlPatterns("/comment/*");
        registrationBean.addUrlPatterns("/discussion/*");
        registrationBean.addUrlPatterns("/note/*");
        registrationBean.addUrlPatterns("/favorite/*");
        registrationBean.addUrlPatterns("/StudentCourse/*");
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.addUrlPatterns("/teaching/*");
        registrationBean.addUrlPatterns("/notifications/*");
        return registrationBean;
    }
}
