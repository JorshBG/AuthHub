package com.jorshbg.authhub.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class FormController {

    @GetMapping("login")
    public String viewLogin(Model model) {
        log.info("viewLogin");
        return "login";
    }

}
