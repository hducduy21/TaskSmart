package com.tasksmart.sharedLibrary.dtos.messages;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserJoinWorkSpaceMessage {
    private String id;
    private String name;
    private String userId;
}
