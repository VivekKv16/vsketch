package com.vivek.vsketch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 🔹 Order uploaded images (customers)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:D:/sketch/image/orders/");

        // 🔹 Gallery images (your drawings)
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
