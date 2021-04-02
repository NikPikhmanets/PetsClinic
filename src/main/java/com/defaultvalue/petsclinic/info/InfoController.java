package com.defaultvalue.petsclinic.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InfoController {

    private final InfoService service;

    @Autowired
    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping("/info")
    public List<String> userInfo(Authentication authentication) {
        return getCollect(authentication);
    }

    private List<String> getCollect(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> String.format("%s %s", service.getMsg(), authentication.getName() + ", You have " + role))
                .collect(Collectors.toList());
    }
}
