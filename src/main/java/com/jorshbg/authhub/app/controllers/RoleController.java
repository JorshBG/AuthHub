package com.jorshbg.authhub.app.controllers;

import com.jorshbg.authhub.modules.roles.IRoleRepository;
import com.jorshbg.authhub.modules.roles.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private IRoleRepository repo;

    @GetMapping
    public ResponseEntity<List<RoleEntity>> get(){
        log.info("Initializing 'get' endpoint");
        return ResponseEntity.ok(repo.findAll());
    }

}
