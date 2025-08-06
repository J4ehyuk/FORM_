package org.example.form_;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/*") // ✅ 모든 경로에 CORS 허용
            .allowedOrigins("http://localhost:5173") // Vite dev server 주소
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("") // ✅ 모든 헤더 허용
            .allowCredentials(true);
  }
}