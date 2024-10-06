package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_has_roles")
public class UserRole {

    public UserRole(final UserRolesKey id) {
        this.id = id;
    }

    @EmbeddedId
    private UserRolesKey id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
