package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Relationship between roles and users
 * This class will generate a new table to store records about this relation
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_has_roles")
public class UserRole {

    /**
     * Composed key
     */
    @EmbeddedId
    private UserRolesKey id;

    /**
     * Mapped key to create a relation with the roles table
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    /**
     * Mapped key to create a relation with the users table
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
