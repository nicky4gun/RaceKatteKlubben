package org.example.racekatteklubben.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String uploadpath = Paths.get("upload/images/").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/images/**").addResourceLocations(uploadpath);
    }
}