package com.tasksmart.sharedLibrary.configs.interceptors;

import feign.Request;
import org.springframework.context.annotation.Bean;

public class PythonRequestInterceptor {
    @Bean
    public Request.Options options() {
        return new Request.Options(
                300000, // Thời gian chờ kết nối, tính bằng mili giây (10 giây)
                300000  // Thời gian chờ đọc dữ liệu, tính bằng mili giây (20 giây)
        );
    }
}
