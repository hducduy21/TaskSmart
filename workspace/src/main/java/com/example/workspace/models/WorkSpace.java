package com.example.workspace.models;

import com.example.workspace.models.enums.EUserRole;
import com.example.workspace.models.enums.EWorkSpaceType;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import jakarta.ws.rs.BadRequestException;
import lombok.*;
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
@Builder
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

    public UserRelation getOwner() {
        return this.users.stream()
                .filter(user -> user.getRole().equals(EUserRole.Owner))
                .findFirst()
                .orElseThrow(() -> new InternalServerError("Owner not found in workspace-" + this.id));
    }

    public void setOwner(UserRelation owner) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(owner);
    }

    public void addMembers(String memberId) {
        if(this.type.equals(EWorkSpaceType.Personal)) {
            throw new BadRequestException("Personal workspace cannot invite members");
        }
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(UserRelation.builder().userId(memberId).role(EUserRole.Member).build());
    }

    public void addCustomer(String customerId) {
        if(this.type.equals(EWorkSpaceType.Personal)) {
            throw new BadRequestException("Personal workspace cannot invite customers");
        }
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        this.users.add(UserRelation.builder().userId(customerId).role(EUserRole.Customer).build());
    }
}
