package com.example.user.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * This class indicate the User model.
 *
 * @author Duy Hoang
 */
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    /** This is the storage field for the user's identification. */
    private String id;

    /** This is the storage field for the user's name. */
    private String name;

    /** This is the storage field for the username. */
    @Indexed(unique = true)
    private String username;

    /** This is the storage field for the password encoded. */
    private String password;

    /** This is the storage field for the user's email. */
    @Indexed(unique = true)
    private String email;

    /** This is the storage field for the user's role. */
    private Set<String> role;

    /** This is the storage field for the user's avatar image. */
    private String profileImageId;

    /** This is the storage field for the user's background description. */
    private String profileBackground;

    /** This is the storage field for the timezone of user area. */
    private int timeZone;

    /** This is the storage field for the email authentication status. */
    private boolean enabled;

    /** This is the storage field for the user's operability. */
    private boolean locked;

    private WorkSpace personalWorkSpace;
    private Set<WorkSpace> workspaces;
    private Set<Project> projects;

    public void addWorkSpace(WorkSpace workSpace) {
        if(CollectionUtils.isEmpty(this.workspaces))
            this.workspaces = new HashSet<>();
        this.workspaces.add(workSpace);
    }

    public void addProject(Project project) {
        if(CollectionUtils.isEmpty(this.projects))
            this.projects = new HashSet<>();
        this.projects.add(project);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class WorkSpace {
        /** The unique identifier for the WorkSpace. */
        private String id;

        /** The name of the WorkSpace. */
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Project {
        /** The unique identifier for the Project. */
        private String id;

        /** The name of the Project. */
        private String name;
    }
}
