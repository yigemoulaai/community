package com.wtu.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc  //全面接管 SpringMVC
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    SessionIntercept sessionIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(sessionIntercept).addPathPatterns("/**").excludePathPatterns("/", "/index.html",
                                    "/callback",
                                    "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                                    "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg");
    }
}
