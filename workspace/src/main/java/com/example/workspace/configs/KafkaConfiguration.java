package com.example.workspace.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic workSpaceCreationTopic() {
        return TopicBuilder.name("workspace-creation").build();
    }

    @Bean
    public NewTopic workSpaceUpdationTopic() {
        return TopicBuilder.name("workspace-updation").build();
    }

    @Bean
    public NewTopic workSpaceAddMemberTopic() {
        return TopicBuilder.name("workspace-add-member").build();
    }

    @Bean
    public NewTopic projectCreationTopic() {
        return TopicBuilder.name("project-creation").build();
    }

    @Bean
    public NewTopic projectUpdationTopic() {
        return TopicBuilder.name("project-updation").build();
    }

    @Bean
    public NewTopic projectAddMemberTopic() {
        return TopicBuilder.name("project-add-member").build();
    }
}