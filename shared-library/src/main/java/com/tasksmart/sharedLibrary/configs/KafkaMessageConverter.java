package com.tasksmart.sharedLibrary.configs;

import com.tasksmart.sharedLibrary.dtos.messages.*;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class KafkaMessageConverter extends JsonMessageConverter {
    public KafkaMessageConverter() {
        super();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.tasksmart.sharedLibrary.dtos.messages");
        typeMapper.setIdClassMapping(Collections.singletonMap("user", UserMessage.class));
        typeMapper.setIdClassMapping(Collections.singletonMap("workspace", WorkSpaceMessage.class));
        typeMapper.setIdClassMapping(Collections.singletonMap("project", ProjectMessage.class));
        typeMapper.setIdClassMapping(Collections.singletonMap("userJoinWorkSpace", UserJoinWorkSpaceMessage.class));
        typeMapper.setIdClassMapping(Collections.singletonMap("UserJoinProjectMessage", UserJoinProjectMessage.class));
        this.setTypeMapper(typeMapper);
    }
}
