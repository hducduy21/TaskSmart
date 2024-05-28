package com.tasksmart.sharedLibrary.configs;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class CommonAppConfig {
    /**
     * Bean for providing a ModelMapper instance.
     *
     * @return A ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
