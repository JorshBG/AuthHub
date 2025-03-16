package com.jorshbg.authhub.app.controllers;

import com.jorshbg.authhub.app.dtos.RoleDto;
import com.jorshbg.authhub.modules.roles.IRoleRepository;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import com.jorshbg.authhub.modules.roles.RoleService;
import com.jorshbg.authhub.utils.responses.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleEntity>> get(){
        log.info("Initializing 'get' endpoint");
        return roleService.getAll();
    }

    @PostMapping
    public ResponseEntity<DataResponse<RoleEntity>> create(@Validated @RequestBody RoleDto role) throws URISyntaxException {
        log.info("Initializing 'create' endpoint");
        return this.roleService.create(role, new URI("/api/v1/roles"));
    }

}
