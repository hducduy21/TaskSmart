package com.tasksmart.user.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic userRegistrationTopic() {
        return TopicBuilder.name("user-registration").build();
    }

    @Bean
    public NewTopic userUpdationTopic() {
        return TopicBuilder.name("user-updation").build();
    }
}
