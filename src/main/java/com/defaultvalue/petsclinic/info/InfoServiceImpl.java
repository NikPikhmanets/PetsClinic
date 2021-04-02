package com.defaultvalue.petsclinic.info;

import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public String getMsg() {
        return "Hello ";
    }
}
