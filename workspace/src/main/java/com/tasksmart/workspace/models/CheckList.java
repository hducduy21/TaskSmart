package com.tasksmart.workspace.models;

import lombok.*;

/**
 * Represents a CheckList in the Card.
 * A CheckList has an id, name, and a checked status.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckList{

    @Builder.Default
    private String id = java.util.UUID.randomUUID().toString();
    /**
     * The name of the CheckList.
     */
    private String name;

    /**
     * The checked status of the CheckList.
     */

    @Builder.Default
    private boolean checked = false;
}
