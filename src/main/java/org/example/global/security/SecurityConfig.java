package org.example.global.security;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/login", "/members/login", "/members/signup");
    }

    public static class PasswordEncoder {
        public static String encodePassword(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }
        public static boolean checkPassword(String checkPassword, String hashedPassword) {
            return BCrypt.checkpw(checkPassword, hashedPassword);
        }
    }
}
