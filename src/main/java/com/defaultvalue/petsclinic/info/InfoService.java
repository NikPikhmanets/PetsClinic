package com.defaultvalue.petsclinic.info;

import org.springframework.stereotype.Service;

@Service
public class InfoService implements IInfoService {
    @Override
    public String getMsg() {
        return "Hello ";
    }
}
