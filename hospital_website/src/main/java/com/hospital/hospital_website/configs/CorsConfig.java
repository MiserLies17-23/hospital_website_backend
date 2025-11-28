package com.hospital.hospital_website.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/hospitalImages/**")
                .addResourceLocations("file:C:/users/THUNDEROBOT/hospital_website/hospital_website/src/main/resources/static/images/hospitalImages/")
                .setCachePeriod(0);

        registry.addResourceHandler("/userImages/**")
                .addResourceLocations("file:C:/users/THUNDEROBOT/hospital_website/hospital_website/src/main/resources/static/images/userImages/")
                .setCachePeriod(0);
    }
}
