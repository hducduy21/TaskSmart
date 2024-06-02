package com.tasksmart.user.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for application beans.
 * This class defines beans for various components used in the application.
 *
 * @author Duy Hoang
 */
@Configuration
public class AppConfig {
    /**
     * Bean for providing a PasswordEncoder instance.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
