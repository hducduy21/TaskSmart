package com.example.workspace.models;

import com.example.workspace.models.enums.EUserRole;
import com.example.workspace.models.enums.EWorkSpaceType;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a workspace model.
 *
 * @author Duy Hoang
 */
@Document("workspaces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkSpace {
    /** The unique identifier for the WorkSpace. */
    @Id
    private String id;

    /** The name of the WorkSpace. */
    private String name;

    /** The type of the WorkSpace. */
    private EWorkSpaceType type;

    /** The description of the WorkSpace. */
    private String description;

    /** The list of users associated with the WorkSpace. */
    private List<UserRelation> users;

    public void setOwner(String ownerId) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(UserRelation.builder().userId(ownerId).role(EUserRole.Owner).build());
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
