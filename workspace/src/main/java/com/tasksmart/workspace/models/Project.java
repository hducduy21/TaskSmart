package com.tasksmart.workspace.models;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.workspace.models.enums.EUserRole;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private String backgroundColor;
    private UnsplashResponse backgroundUnsplash;

    /** The description of the Project. */
    private String description;

    /** The specification document of project. */
    @Builder.Default
    private boolean speDoc = false;

    /** The list of ListCards associated with the Project. */
    private String workspaceId;

    @Builder.Default
    private boolean isTemplate = false;

    @Builder.Default
    private String databaseStructure = "";

    @Builder.Default
    private List<String> listCardIds = new ArrayList<>();

    @Builder.Default
    private Invitation invitation = Invitation.builder()
                                                .isPublic(false)
                                                .code(UUID.randomUUID().toString())
                                                .build();

    /** The list of users associated with the WorkSpace. */
    @Builder.Default
    private List<UserRelation> users = new ArrayList<>();

    public Project copyWithoutWorkSpace() {
        return Project.builder()
                .name(this.name)
                .backgroundUnsplash(this.backgroundUnsplash)
                .backgroundColor(this.backgroundColor)
                .description(this.description)
                .build();
    }

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
