package com.jorshbg.authhub.single;

import com.jorshbg.authhub.app.dtos.RoleDto;
import com.jorshbg.authhub.modules.roles.IRoleRepository;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.roles.RoleService;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class RoleTests {

    @Mock
    private IRoleRepository repository;

    @InjectMocks
    private RoleService service;

    private List<RoleEntity> roles;

    @BeforeEach
    void setUp() {
        roles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            roles.add(new RoleEntity());
        }
    }

    @Test
    void testGetAllRoles(){
        given(this.repository.findAll())
                .willReturn(this.roles);

        var response = this.service.getAll();

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isInstanceOf(List.class);
        assertThat(response.getBody()).hasSize(10);
    }

    @Test
    void testGetById(){
        var entity = new RoleEntity();

        given(this.repository.findById(entity.getId()))
                .willReturn(Optional.of(entity));

        var response = this.service.getById(entity.getId());

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(RoleEntity.class);
        assertThat(response.getBody().data().getId()).isEqualTo(entity.getId());
    }

    @Test
    void testGetByIdThrowANotFoundException(){

        var id = UUID.randomUUID();

        given(this.repository.findById(id))
                .willThrow(new AuthHubException(HttpStatus.NOT_FOUND, "Role not found"));

        assertThrows(AuthHubException.class, () -> this.service.getById(id));
    }

    @Test
    void testStore() throws URISyntaxException {
        var dto = new RoleDto("ADMIN");
        var entity = new RoleEntity(null, "ADMIN", null, null);

        given(this.repository.save(entity))
                .willReturn(entity);

        var response = this.service.create(dto, new URI("/api/v1/roles"));

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(RoleEntity.class);
        assertThat(response.getHeaders().containsKey("Location")).isTrue();
    }

    @Test
    void testStoreThrowAnExceptionForDuplicateRoleName() throws URISyntaxException {
        var dto = new RoleDto("ADMIN");
        var entity = new RoleEntity(UUID.randomUUID(), "ADMIN", null, null);

        given(this.repository.findByName(entity.getName()))
                .willReturn(Optional.of(entity));

        assertThrows(AuthHubException.class, () -> this.service.create(dto, new URI("/api/v1/roles")));
    }

    @Test
    void testUpdate() {
        var dto = new RoleDto("ADMIN");
        var entity = new RoleEntity(UUID.randomUUID(), "USER", null, null);
        var entity1 = new RoleEntity(entity.getId(), "ADMIN", null, null);

        given(this.repository.findById(entity.getId()))
                .willReturn(Optional.of(entity));

        given(this.repository.save(entity1))
                .willReturn(entity1);

        var response = this.service.update(entity.getId(), dto);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(RoleEntity.class);

    }

    @Test
    void testUpdateThrowAConflictException() {
        var dto = new RoleDto("ADMIN");
        var id = UUID.randomUUID();
        var entity = new RoleEntity(id, "USER", null, null);
        var exists = new RoleEntity(UUID.randomUUID(), "ADMIN", null, null);

        given(this.repository.findById(id))
                .willReturn(Optional.of(entity));

        given(this.repository.findByName(exists.getName()))
                .willReturn(Optional.of(exists));

        assertThrows(AuthHubException.class, () -> this.service.update(id, dto));
    }

    @Test
    void testDelete() {
        var entity = new RoleEntity(UUID.randomUUID(), "USER", null, null);

        given(this.repository.findById(entity.getId()))
                .willReturn(Optional.of(entity));

        var response = this.service.delete(entity.getId());
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(RoleEntity.class);
    }

    @Test
    void testDeleteThrowANotFoundException() {
        var entity = new RoleEntity(UUID.randomUUID(), "USER", null, null);

        given(this.repository.findById(entity.getId()))
                .willThrow(new AuthHubException(HttpStatus.NOT_FOUND, "No role found"));

        assertThrows(AuthHubException.class, () -> this.service.delete(entity.getId()));
    }
}
