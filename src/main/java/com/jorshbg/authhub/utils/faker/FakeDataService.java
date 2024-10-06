package com.jorshbg.authhub.utils.faker;

import com.jorshbg.authhub.modules.permissions.IPermissionRepository;
import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.role_permissions.IRolePermissionRepository;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.role_permissions.RolePermissionsKey;
import com.jorshbg.authhub.modules.roles.IRoleRepository;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.user_roles.IUserRoleRepository;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import com.jorshbg.authhub.modules.user_roles.UserRolesKey;
import com.jorshbg.authhub.modules.users.IUserRepository;
import com.jorshbg.authhub.modules.users.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Profile("dev")
@Service
@AllArgsConstructor
public class FakeDataService {

    private final JdbcTemplate jdbcTemplate;

    private final IRoleRepository roleRepository;

    private final IUserRepository userRepository;

    private final IPermissionRepository permissionRepository;

    private final IUserRoleRepository userRoleRepository;

    private final IRolePermissionRepository rolePermissionRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostConstruct
    public void init() {
        jdbcTemplate.execute("DELETE FROM role_has_permissions");
        jdbcTemplate.execute("DELETE FROM user_has_roles");
        jdbcTemplate.execute("DELETE FROM permissions");
        jdbcTemplate.execute("DELETE FROM roles");
        jdbcTemplate.execute("DELETE FROM users");

        jdbcTemplate.execute("TRUNCATE TABLE role_has_permissions RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE user_has_roles RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE permissions RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE roles RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");

        String[] permissions = {"create", "read", "update", "delete"};
        List<PermissionEntity> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            var storedEPermission = this.permissionRepository.save(new PermissionEntity(UUID.randomUUID(), permission, null));
            permissionList.add(storedEPermission);
        }

        String[] roles = {"ADMIN", "STUDENT", "TEACHER", "EMPLOYEE"};
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String role : roles) {
            var storedRole = this.roleRepository.save(new RoleEntity(UUID.randomUUID(), role, null, null));
            roleEntities.add(storedRole);
        }

        String[] users = {"admin", "some_student", "some_teacher", "some_employee"};
        List<UserEntity> userEntities = new ArrayList<>();
        Random random = new Random();
        for (String user : users) {
            var storedUser = this.userRepository.save(new UserEntity(UUID.randomUUID(),"EXP","EXP", user,"null", user + "@email.com", "ASDPMDS" + String.valueOf(random.nextInt(44444444)), passwordEncoder.encode("password"),true, null, null));
            userEntities.add(storedUser);
        }

        for (int i = 0; i < roleEntities.size(); i++) {
            var userRoleKey = new UserRolesKey(userEntities.get(i).getId(), roleEntities.get(i).getId());
            this.userRoleRepository.save(new UserRole(userRoleKey, roleEntities.get(i), userEntities.get(i)));
        }

        for (int i = 0; i < permissionList.size(); i++) {
            var rolePermissionKey = new RolePermissionsKey(roleEntities.get(i).getId(), permissionList.get(i).getId());
            this.rolePermissionRepository.save(new RolePermission(rolePermissionKey, roleEntities.get(i), permissionList.get(i)));
        }
    }

}
