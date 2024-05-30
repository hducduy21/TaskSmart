package com.tasksmart.sharedLibrary.dtos.messages;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectMessage {
    private String id;

    /** The name of the WorkSpace. */
    private String name;

    /** The type of the WorkSpace. */
    private String type;

    /** The description of the WorkSpace. */
    private String description;

    private List<UserRelation> users;
}