package com.tasksmart.workspace.dtos.response;

import com.tasksmart.workspace.models.UserRelation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentResponse {
    /** The unique identifier for the Comment. */
    private String id;

    /** The content of the Comment. */
    public String content;

    /** The unique identifier for the User who created the Comment. */
    public Date createdAt;

    public UserRelation user;
}
