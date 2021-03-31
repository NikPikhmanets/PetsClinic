package com.defaultvalue.petsclinic.info;

import org.springframework.security.access.annotation.Secured;

public interface IInfoService {
    @Secured("authenticated")
    public String getMsg();
}
