package com.jorshbg.authhub.modules.roles;

import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank
    @Size(max = 15)
    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<UserRole> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    private List<RolePermission> permissions;

}
