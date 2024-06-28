package com.tasksmart.workspace.models;

import com.tasksmart.workspace.models.enums.EUserRole;
import com.tasksmart.workspace.models.enums.EWorkSpaceType;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import jakarta.ws.rs.BadRequestException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private String categoryId;

    /** The type of the WorkSpace. */
    private EWorkSpaceType type;

    /** The description of the WorkSpace. */
    private String description;

    private String backgroundUnsplashId;

    @Builder.Default
    private Invitation invitation = Invitation.builder()
            .isPublic(false)
            .code(UUID.randomUUID().toString())
            .build();

    /** The list of users associated with the WorkSpace. */
    @Builder.Default
    private List<UserRelation> users = new ArrayList<>();

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
        owner.setRole(EUserRole.Owner);
        this.users.add(owner);
    }

    public void addMembers(UserRelation member) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        member.setRole(EUserRole.Member);
        this.users.add(member);
    }

    public void addCustomer(UserRelation customer) {
        if(CollectionUtils.isEmpty(this.users)) {
            this.users = new ArrayList<>();
        }
        customer.setRole(EUserRole.Customer);
        this.users.add(customer);
    }
}
