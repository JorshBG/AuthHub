package com.jorshbg.authhub.modules.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private List<UserRole> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
    @JsonIgnore
    private List<RolePermission> permissions;

}
