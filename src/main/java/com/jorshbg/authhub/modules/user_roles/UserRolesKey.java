package com.jorshbg.authhub.modules.user_roles;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

/**
 * Composed primary key for the relationship between users and roles
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserRolesKey implements Serializable {

    /**
     * The id of the user.
     * user_id will be the name in the table
     */
    @Column(name = "user_id")
    private UUID userId;

    /**
     * The id of the role.
     * role_id will be the name in the table
     */
    @Column(name = "role_id")
    private UUID roleId;

}
