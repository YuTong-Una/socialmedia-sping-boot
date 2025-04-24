package com.una.esunbank.socialmedia;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 設定允許的來源，可以設置為特定的域名或使用 * 表示任何來源
        registry.addMapping("/**") // 所有的接口都允許跨域
                .allowedOrigins("http://localhost:5173", "http://localhost:9173") // 允許的前端網址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的 HTTP 方法
                .allowedHeaders("*") // 允許的請求頭
                .allowCredentials(true); // 是否允許發送 cookie

    }
}
