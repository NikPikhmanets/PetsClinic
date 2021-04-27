package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.UserAlreadyExistException;
import com.defaultvalue.petsclinic.registration.RegistrationForm;
import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.dto.UserShortInfoDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User saveUser(RegistrationForm registrationForm) throws UserAlreadyExistException;

    UserDTO getUserDTO();

    Page<UserShortInfoDTO> getPageableUsers(int page);

    Page<DoctorDTO> getPageableDoctors(int page);
}
