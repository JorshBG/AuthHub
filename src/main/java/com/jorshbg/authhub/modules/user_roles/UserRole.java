package com.jorshbg.authhub.modules.user_roles;

import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_has_roles")
public class UserRole {

    @EmbeddedId
    private UserRolesKey id;

    @ManyToOne
    private RoleEntity role;

    @ManyToOne
    private UserEntity user;
}
