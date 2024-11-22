package com.jorshbg.authhub.relationship;

import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.role_permissions.IRolePermissionRepository;
import com.jorshbg.authhub.modules.role_permissions.RolePermission;
import com.jorshbg.authhub.modules.role_permissions.RolePermissionService;
import com.jorshbg.authhub.modules.role_permissions.RolePermissionsKey;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PermissionRolesTests {

    @Mock
    private IRolePermissionRepository relationRepository;

    @InjectMocks
    private RolePermissionService relationService;

    UUID roleId;
    UUID permissionId;
    RolePermissionsKey id;
    RolePermission relation;

    Random random;


    @BeforeEach
    void setUp() {
        random = new Random();
        roleId = UUID.randomUUID();
        permissionId = UUID.randomUUID();
        id = new RolePermissionsKey(roleId, permissionId);
        relation = new RolePermission(
                id,
                new RoleEntity(roleId, "user", null, null),
                new PermissionEntity(permissionId, "write", null)
        );
    }

    @Test
    void testAppendPermissionToRoleThrowAnException(){

        when(this.relationRepository.findById(id)).thenReturn(Optional.of(relation));

        assertThrows(AuthHubException.class, () -> this.relationService.store(roleId, permissionId));
    }

    @Test
    void testAppendPermissionToRole(){

        when(this.relationRepository.findById(id)).thenReturn(null);
        when(this.relationRepository.save(relation)).thenReturn(relation);

        var res = this.relationService.store(roleId, permissionId);

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if(res.getBody() != null) {
            assertThat(res.getBody().data()).isNotNull();
            assertThat(res.getBody().data()).isInstanceOf(RolePermission.class);
            assertThat(res.getBody().data().getPermission().getName()).isEqualTo("write");
            assertThat(res.getBody().data().getRole().getName()).isEqualTo("user");
        }
    }

    @Test
    void testRemovePermissionToRoleThrowAnException(){

        when(this.relationRepository.findById(id)).thenReturn(null);

        assertThrows(AuthHubException.class, () -> this.relationService.removePermission(permissionId));
    }

    @Test
    void testRemovePermissionToRole(){

        when(this.relationRepository.findById(id)).thenReturn(Optional.of(relation));

        var res = this.relationService.removePermission(permissionId);

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if(res.getBody() != null) {
            assertThat(res.getBody().data()).isNotNull();
            assertThat(res.getBody().data()).isInstanceOf(RolePermission.class);
            assertThat(res.getBody().data().getPermission().getName()).isEqualTo("write");
        }

    }

    @Test
    void testListPermissionInRole(){
        var rl = this.relation.getRole();
        var relationlist = new ArrayList<RolePermission>();
        for (int i = 0; i < 20; i++) {
            var pr = new PermissionEntity(UUID.randomUUID(), "nuevo" + random.nextInt(), null);
            relationlist.add(new RolePermission(new RolePermissionsKey(rl.getId(), pr.getId()), rl, pr));
        }

        when(this.relationRepository.findAll()).thenReturn(relationlist);

        var res = this.relationService.getPermissions(rl.getId());

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if(res.getBody() != null) assertThat(res.getBody().size()).isEqualTo(20);
    }

}
