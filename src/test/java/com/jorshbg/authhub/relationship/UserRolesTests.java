package com.jorshbg.authhub.relationship;

import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.user_roles.IUserRoleRepository;
import com.jorshbg.authhub.modules.user_roles.UserRole;
import com.jorshbg.authhub.modules.user_roles.UserRolesKey;
import com.jorshbg.authhub.modules.user_roles.UserRolesService;
import com.jorshbg.authhub.modules.users.UserEntity;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRolesTests {

    @Mock
    private IUserRoleRepository relationshipRepository;

    @InjectMocks
    private UserRolesService relationshipService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAssignRoleToUser() {
        UserEntity currentUser = new UserEntity(UUID.randomUUID(), "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        RoleEntity role = new RoleEntity(UUID.randomUUID(), "USER", null, null);

        when(this.relationshipRepository.findById(new UserRolesKey(currentUser.getId(), role.getId()))).thenReturn(null);

        var res = this.relationshipService.assignRole(currentUser.getId());

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if (res.getBody() != null) {
            assertThat(res.getBody().data()).isNotNull();
            assertThat(res.getBody().data().getUser().getEmail()).isEqualTo("jordi@gmail.com");
            assertThat(res.getBody().data().getRole().getName()).isEqualTo("USER");
        }
    }

    @Test
    void testAssignRoleToUserThrowAnException() {
        UserEntity currentUser = new UserEntity(UUID.randomUUID(), "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        RoleEntity role = new RoleEntity(UUID.randomUUID(), "USER", null, null);

        UserRole rel = new UserRole(new UserRolesKey(currentUser.getId(), role.getId()), role, currentUser);

        when(this.relationshipRepository.findById(new UserRolesKey(currentUser.getId(), role.getId()))).thenReturn(Optional.of(rel));

        assertThrows(AuthHubException.class, () -> this.relationshipService.assignRole(currentUser.getId()));
    }

    @Test
    void testRemoveRoleOfAUser() {
        UserEntity currentUser = new UserEntity(UUID.randomUUID(), "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        RoleEntity role = new RoleEntity(UUID.randomUUID(), "USER", null, null);

        UserRole rel = new UserRole(new UserRolesKey(currentUser.getId(), role.getId()), role, currentUser);

        when(this.relationshipRepository.findById(new UserRolesKey(currentUser.getId(), role.getId()))).thenReturn(Optional.of(rel));

        var res = this.relationshipService.removeRole(currentUser.getId());

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if (res.getBody() != null) {
            assertThat(res.getBody().data()).isNotNull();
            assertThat(res.getBody().data().getUser().getEmail()).isEqualTo("jordi@gmail.com");
            assertThat(res.getBody().data().getRole().getName()).isEqualTo("USER");
        }
    }

    @Test
    void testRemoveRoleToUserThrowAnException() {
        UserEntity currentUser = new UserEntity(UUID.randomUUID(), "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        RoleEntity role = new RoleEntity(UUID.randomUUID(), "USER", null, null);


        when(this.relationshipRepository.findById(new UserRolesKey(currentUser.getId(), role.getId()))).thenReturn(null);

        assertThrows(AuthHubException.class, () -> this.relationshipService.removeRole(currentUser.getId()));
    }

}
