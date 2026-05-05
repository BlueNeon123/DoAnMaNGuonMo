package com.quanan.QuanLyQuanAnBinhDan.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Tên thư mục chứa ảnh
        Path uploadDir = Paths.get("dish-images");
        
        // Dùng toUri().toString() để tạo đường dẫn chuẩn (file:///D:/...)
        // Cách này hoạt động tốt nhất trên Windows
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/dish-images/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}