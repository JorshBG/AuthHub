package com.jorshbg.authhub.single;

import com.jorshbg.authhub.app.dtos.PermissionDto;
import com.jorshbg.authhub.modules.permissions.IPermissionRepository;
import com.jorshbg.authhub.modules.permissions.PermissionEntity;
import com.jorshbg.authhub.modules.permissions.PermissionService;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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
public class PermissionTests {

    @Mock
    private IPermissionRepository repository;

    @InjectMocks
    private PermissionService service;

    @Test
    void testFindAll(){
        List<PermissionEntity> permissions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            permissions.add(new PermissionEntity());
        }

        given(this.repository.findAll()).
            willReturn(permissions);

        var res = this.service.getAll();

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody()).isInstanceOf(List.class);
        assertThat(res.getBody()).hasSize(10);
    }

    @Test
    void testCreate() throws URISyntaxException {
        var dto = new PermissionDto("USER");
        PermissionEntity permission = new PermissionEntity(null, "USER", null);

        given(this.repository.save(permission)).willReturn(permission);

        var res = this.service.create(dto, new URI("/si/s"));

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().data()).isInstanceOf(PermissionEntity.class);
    }

    @Test
    void testUpdate() {
        var id = UUID.randomUUID();
        var dto = new PermissionDto("USER");
        PermissionEntity updated = new PermissionEntity(id, "USER", null);
        PermissionEntity toUpdate = new PermissionEntity(id, "ADMIN", null);

        given(this.repository.save(updated)).willReturn(updated);

        given(this.repository.findById(id)).willReturn(Optional.of(toUpdate));

        given(this.repository.findByName("USER")).willReturn(Optional.of(toUpdate));

        var response = this.service.update(id, dto);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(PermissionEntity.class);
        assertThat(response.getBody().data().getName()).isEqualTo("USER");
    }

    @Test
    void testDelete() {
        var id = UUID.randomUUID();

        PermissionEntity toDelete = new PermissionEntity(id, "USER", null);

        given(this.repository.findById(id)).willReturn(Optional.of(toDelete));

        var response = this.service.delete(id);

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data()).isInstanceOf(PermissionEntity.class);
    }

    @Test
    void testFindAllThrowANotFoundException() {
        given(this.repository.findAll()).willThrow(AuthHubException.class);

        assertThrows(AuthHubException.class, () -> this.service.getAll());
    }

    @Test
    void testCreateThrowAnException() {
        var dto = new PermissionDto("USER");
        PermissionEntity permission = new PermissionEntity(null, "USER", null);
        PermissionEntity permission1 = new PermissionEntity(UUID.randomUUID(), "USER", null);

        given(this.repository.save(permission)).willReturn(permission);

        given(this.repository.findByName("USER")).willReturn(Optional.of(permission1));

        assertThrows(AuthHubException.class, () -> this.service.create(dto, new URI("/si/s")));
    }

    @Test
    void testUpdateThrowAnException() {
        var id = UUID.randomUUID();
        var dto = new PermissionDto("USER");
        PermissionEntity updated = new PermissionEntity(id, "USER", null);
        PermissionEntity toUpdate = new PermissionEntity(UUID.randomUUID(), "ADMIN", null);

        given(this.repository.save(updated)).willReturn(updated);

        given(this.repository.findById(id)).willReturn(Optional.of(updated));

        given(this.repository.findByName("USER")).willReturn(Optional.of(toUpdate));

        assertThrows(AuthHubException.class, () -> this.service.update(id, dto));
    }

}
