package com.jorshbg.authhub.modules.permissions;

import com.jorshbg.authhub.modules.role_permissions.RolePermission;
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
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(unique = true)
    @NotNull
    @Size(max = 20)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "permission")
    private List<RolePermission> roles;

}
