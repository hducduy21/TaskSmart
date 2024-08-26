package com.tasksmart.workspace.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * KafkaConfiguration class for managing Kafka operations.
 * This class is used to manage the Kafka operations.
 *
 */
@Configuration
public class KafkaConfiguration {
    /**
     * Create a new topic for workspace creation.
     * @return NewTopic
     */
    @Bean
    public NewTopic workSpaceCreationTopic() {
        return TopicBuilder.name("workspace-creation").build();
    }

    /**
     * Create a new topic for workspace updation.
     * @return NewTopic
     */
    @Bean
    public NewTopic workSpaceUpdationTopic() {
        return TopicBuilder.name("workspace-updation").build();
    }

    /**
     * Create a new topic for workspace deletion.
     * @return NewTopic
     */
    @Bean
    public NewTopic workSpaceAddMemberTopic() {
        return TopicBuilder.name("workspace-add-member").build();
    }

    /**
     * Create a new topic for project creation.
     * @return NewTopic
     */
    @Bean
    public NewTopic projectCreationTopic() {
        return TopicBuilder.name("project-creation").build();
    }

    /**
     * Create a new topic for project updation.
     * @return NewTopic
     */
    @Bean
    public NewTopic projectUpdationTopic() {
        return TopicBuilder.name("project-updation").build();
    }

    /**
     * Create a new topic for project deletion.
     * @return NewTopic
     */
    @Bean
    public NewTopic projectAddMemberTopic() {
        return TopicBuilder.name("project-add-member").build();
    }

    /**
     * Create a new topic for project deletion.
     * @return NewTopic
     */
    @Bean
    public NewTopic projectAccess() {
        return TopicBuilder.name("project-access").build();
    }
}