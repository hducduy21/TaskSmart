package com.tasksmart.workspace.dtos.response;

import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.sharedLibrary.models.enums.ELevel;
import com.tasksmart.sharedLibrary.models.enums.EStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CardResponse {
    /** The unique identifier for the Card. */
    private String id;

    /** The name of the Card. */
    public String name;

    /** The color of the Card. */
    public String color;

    /** The description of the Card. */
    public String description;

    /** The status of the Card. */
    public EStatus status;

    /** The priority level of the Card. */
    public ELevel priority;

    /** The risk level of the Card. */
    public ELevel risk;

    /** The effort level of the Card. */
    public ELevel effort;

    /** The estimated completion time for the Card. */
    public LocalDateTime estimate;

    /** The start time of the Card. */
    public LocalDateTime startTime;

    /** The list of CheckLists associated with the Card. */
    public List<CheckListGroupResponse> checkLists;

    public List<CommentResponse> comments;

    public List<AttachmentResponse> attachments;

    public List<UserRelation> implementers;
}
