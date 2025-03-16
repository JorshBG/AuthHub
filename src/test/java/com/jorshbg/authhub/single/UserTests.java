package com.jorshbg.authhub.single;

import com.jorshbg.authhub.app.dtos.UserDetailsDto;
import com.jorshbg.authhub.app.dtos.UserDto;
import com.jorshbg.authhub.modules.users.IUserRepository;
import com.jorshbg.authhub.modules.users.UserEntity;
import com.jorshbg.authhub.modules.users.UserService;
import com.jorshbg.authhub.system.exceptions.AuthHubException;
import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import com.jorshbg.authhub.utils.request_params.UserGetParams;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTests {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private UserService service;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtProvider jwt;

    private Random random = new Random();

    @Test
    void testUpdateUser(){
        UUID id = UUID.randomUUID();
        UserEntity currentUser = new UserEntity(id, "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        UserEntity updatedUser = new UserEntity(id, "Jordi2", "Yair2", "jorshbg2", "", "jordi2@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        var dto = new UserDetailsDto("Jordi2", "Yair2", "jorshbg2", "", "jordi2@gmail.com", "7721446962");

        when(this.repository.findById(id)).thenReturn(Optional.of(currentUser));
        when(this.repository.save(currentUser)).thenReturn(updatedUser);

        var res = this.service.update(id, dto);

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().data()).isNotNull();
        assertThat(res.getBody().metadata()).isNotNull();
        assertThat(res.getBody().data().getId()).isEqualTo(id);
        assertThat(res.getBody().data().getFirstName()).isEqualTo("Jordi2");
        assertThat(res.getBody().data().getUsername()).isEqualTo("jorshbg2");
    }

    @Test
    void testFetchAllUserAsPage(){
        var list = new ArrayList<UserEntity>();
        for (int i = 0; i < 50; i++) {
            list.add(new UserEntity(UUID.randomUUID(), "Jordi" + random.nextInt(3), "Yair" + random.nextInt(3), "jorshbg" + random.nextInt(3), "" + random.nextInt(3),"jr" + random.nextInt(3) + "@gmail.com", "772144696" + random.nextInt(1), "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null));
        }
        var pageNumber = 0;
        var size = 10;
        var orderBy = "id";
        var direction = Sort.Direction.ASC;
        var pgAble = PageRequest.of(pageNumber, size);
        Page<UserEntity> page = new PageImpl<>(list);
        when(this.repository.findAll(pgAble)).thenReturn(page);

        var params = new UserGetParams();

        var res = this.service.getAllAsPage(params, pageNumber, size, orderBy, direction);
//
//        assertThat(res).isNotNull();
//        assertThat(res.getBody()).isNotNull();
//        assertThat(res.getBody().data()).isNotNull();
//        assertThat(res.getBody().metadata()).isNotNull();
//        assertThat(res.getBody().data().length).isEqualTo(size);
//        assertThat(res.getBody().metadata().getPage()).isEqualTo(pageNumber);
//        assertThat(res.getBody().metadata().getCurrentItems()).isEqualTo(50);
//        assertThat(res.getBody().metadata().getHasNext()).isEqualTo(true);
//        assertThat(res.getBody().metadata().getHasPrevious()).isEqualTo(false);
    }

    @Test
    void testFetchAllUserAsList(){
        var list = new ArrayList<UserEntity>();
//        for (int i = 0; i < 50; i++) {
//            list.add(new UserEntity(UUID.randomUUID(), "Jordi" + random.nextInt(3), "Yair" + random.nextInt(3), "jorshbg" + random.nextInt(3), "" + random.nextInt(3),"jr" + random.nextInt(3) + "@gmail.com", "772144696" + random.nextInt(1), "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null));
//        }
//        when(this.repository.findAll()).thenReturn(list);
//
//        var params = new UserGetParams();
//        var res = this.service.getAllAsList(params);
//
//        assertThat(res).isNotNull();
//        assertThat(res.getBody()).isNotNull();
//        if(res.getBody() != null) assertThat(res.getBody().size()).isEqualTo(50);
    }

    @Test
    void testUpdateUserThrowAException(){
        UUID id = UUID.randomUUID();
        UserEntity currentUser = new UserEntity(id, "Jordi", "Yair", "jorshbg", "", "jordi@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        UserEntity updatedUser = new UserEntity(id, "Jordi2", "Yair2", "jorshbg2", "", "jordi2@gmail.com", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);
        var dto = new UserDetailsDto("Jordi2", "Yair2", "jorshbg2", "", "jordi2@gmail.com", "7721446962");

        when(this.repository.findById(id)).thenReturn(Optional.of(currentUser));
        when(this.repository.save(currentUser)).thenThrow(new AuthenticationException());

        assertThrows(AuthHubException.class, () -> this.service.update(id, dto));
    }

    @Test
    void testChangePasswordThrowAException(){
        UserEntity user = new UserEntity();
        when(this.repository.findByEmailAndStatus("jordi@itsoeh.edu.mx", true)).thenThrow(AuthHubException.class);
        when(this.repository.save(user)).thenReturn(user);

        assertThrows(AuthHubException.class, () -> this.service.attemptChangePassword());
    }

    @Test
    void testChangePassword(){
        UserEntity user = new UserEntity(UUID.randomUUID(), "Jordi", "Yair", "jorshbg", "", "jordi@itsoeh.edu.mx", "7721446962", "$2a$10$Ycqs7ZUbKN1I1D/hARiQYOxGgqPx36yXGsIdqXWC6VYlwp0vuDCqS", true, null, null);

        when(this.repository.findByEmailAndStatus("jordi@itsoeh.edu.mx", true)).thenReturn(Optional.of(user));
        when(this.repository.save(user)).thenReturn(user);

        var firstRes = this.service.attemptChangePassword();

        assertThat(firstRes).isNotNull();
        assertThat(firstRes.getBody()).isNotNull();
        if(firstRes.getBody() != null) assertThat(firstRes.getBody().token()).isNotNull();

        String tokenEmail = jwt.getEmailToken(user.getUsername());
        var secondRes = this.service.changePassword(tokenEmail);

        assertThat(secondRes).isNotNull();
        assertThat(secondRes.getBody()).isNotNull();
        if(secondRes.getBody() != null) {
            assertThat(secondRes.getBody().data()).isNotNull();
            assertThat(secondRes.getBody().data().getUsername()).isEqualTo("jorshbg");
            assertThat(secondRes.getBody().data().getEmail()).isEqualTo("jordi@itsoeh.edu.mx");
            assertThat(secondRes.getBody().metadata()).isNotNull();
        }
    }

    @Test
    void testRegisterUser(){
        UserEntity user = new UserEntity(UUID.randomUUID(), "Jordi2", "Yair2", "jorshbg2", "", "jordi@itsoeh.edu.mx", "7721446962", "$2a$10$58hJfwErSVJ8JYU4V07sMOYLoJTw/ALfIcQ0rsXJK8uvYIwQsUbn.", true, null, null);
        var dto = new UserDto("Jordi2", "Yair2", "jorshbg2", "", "jordi@itsoeh.edu.mx", "7721446962", "password");
        when(this.repository.findByEmailAndStatus("jordi@itsoeh.edu.mx", true)).thenReturn(Optional.of(user));
        when(this.repository.save(user)).thenReturn(user);

        var res = this.service.store(dto);

        assertThat(res).isNotNull();
        assertThat(res.getBody()).isNotNull();
        if(res.getBody() != null) {
            assertThat(res.getBody().data()).isNotNull();
            assertThat(res.getBody().data().getUsername()).isEqualTo("jorshbg2");
            assertThat(res.getBody().data().getEmail()).isEqualTo("jordi@itsoeh.edu.mx");
            assertThat(res.getBody().metadata()).isNotNull();
        }
    }

    @Test
    void testRegisterUserThrowAException(){
        UserEntity user = new UserEntity(UUID.randomUUID(), "Jordi2", "Yair2", "jorshbg2", "", "jordi@itsoeh.edu.mx", "7721446962", "$2a$10$58hJfwErSVJ8JYU4V07sMOYLoJTw/ALfIcQ0rsXJK8uvYIwQsUbn.", true, null, null);
        var dto = new UserDto("Jordi2", "Yair2", "jorshbg2", "", "jordi@itsoeh.edu.mx", "7721446962", "password");
        when(this.repository.findByEmailAndStatus("jordi@itsoeh.edu.mx", true)).thenReturn(Optional.of(user));
        when(this.repository.save(user)).thenThrow(AuthHubException.class);

        assertThrows(AuthHubException.class, () -> this.service.store(dto));
    }

    @Test
    void testMe(){
        UserEntity user = new UserEntity(UUID.randomUUID(), "Jordi2", "Yair2", "jordi", "", "jordi@itsoeh.edu.mx", "7721446962", "$2a$10$58hJfwErSVJ8JYU4V07sMOYLoJTw/ALfIcQ0rsXJK8uvYIwQsUbn.", true, null, null);
        String token = "ey5a8nspa1ldkas";
        when(this.request.getHeader("Authorization")).thenReturn("Bearer" + token);
        when(this.jwt.getSubject(token)).thenReturn("jordi");
        when(this.repository.findByUsernameAndStatus("jordi", true)).thenReturn(Optional.of(user));

        var res = this.service.me();

        assertThat(res).isNotNull();
    }

}
