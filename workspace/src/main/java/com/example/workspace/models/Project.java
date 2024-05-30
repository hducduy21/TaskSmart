package com.example.workspace.models;

import com.example.workspace.models.enums.EUserRole;
import com.example.workspace.models.enums.EWorkSpaceType;
import jakarta.ws.rs.BadRequestException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a project model.
 *
 * @author Duy Hoang
 */
@Document("projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {
    /** The unique identifier for the Project. */
    @Id
    private String id;

    /** The name of the Project. */
    private String name;

    /** The background of the Project. */
    private String background;

    /** The description of the Project. */
    private String description;

    /** The list of ListCards associated with the Project. */
    private String workSpaceId;

    /** The list of users associated with the WorkSpace. */
    private List<UserRelation> users;

    public void setOwner(UserRelation owner) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(owner);
    }

    public void addMembers(String memberId) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(UserRelation.builder().userId(memberId).role(EUserRole.Member).build());
    }

    public void addCustomer(String customerId) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(UserRelation.builder().userId(customerId).role(EUserRole.Customer).build());
    }
}
