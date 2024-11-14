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
        registrationBean.addUrlPatterns("/note/getStudentAllNote");
        registrationBean.addUrlPatterns("/note/allPublicNote");
        registrationBean.addUrlPatterns("/note/deleteNote");
        registrationBean.addUrlPatterns("/note/updateNoteInformation");
        registrationBean.addUrlPatterns("/note/updateNoteAuthority");
        registrationBean.addUrlPatterns("/note/upload");
        registrationBean.addUrlPatterns("/favorite/*");
        registrationBean.addUrlPatterns("/StudentCourse/*");
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.addUrlPatterns("/teaching/*");
        registrationBean.addUrlPatterns("/notifications/*");
        registrationBean.addUrlPatterns("/homework/*");
        registrationBean.addUrlPatterns("/peerAssignment/*");
        registrationBean.addUrlPatterns("/file/allCourseSource");
        return registrationBean;
    }
}
