package com.defaultvalue.petsclinic.user;

import com.defaultvalue.petsclinic.exceptions.UserAlreadyExistException;
import com.defaultvalue.petsclinic.exceptions.UserNotFoundException;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import com.defaultvalue.petsclinic.registration.RegistrationForm;
import com.defaultvalue.petsclinic.user.dto.DoctorDTO;
import com.defaultvalue.petsclinic.user.dto.ImmutableUserDTO;
import com.defaultvalue.petsclinic.user.dto.UserDTO;
import com.defaultvalue.petsclinic.user.dto.UserShortInfoDTO;
import com.defaultvalue.petsclinic.user.entity.User;
import com.defaultvalue.petsclinic.user.role.Role;
import com.defaultvalue.petsclinic.user.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.defaultvalue.petsclinic.issue.IssueServiceImpl.SIZE_PAGE;

@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User saveUser(RegistrationForm form) throws UserAlreadyExistException {
        if (userRepository.findByEmail(form.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email address: " + form.getEmail());
        }
        String cryptPassword = bCryptPasswordEncoder.encode(form.getPassword());
        Role role = roleRepository.findByName(ROLE_USER);

        User user = new User();
        user.setName(form.getFirstName());
        user.setSurname(form.getSurname());
        user.setEmail(form.getEmail());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setBirthday(form.getBirthday());
        user.setPassword(cryptPassword);
        user.setRoles(Collections.singleton(role));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public UserDTO getUserDTO() {
        long userDetailsId = getUserDetailsId();
        Optional<User> optionalUser = userRepository.findById(userDetailsId);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));

        return ImmutableUserDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .build();
    }

    @Override
    public Page<UserShortInfoDTO> getPageableUsers(int page) {
        Page<User> usersPage = userRepository.findAllBySpecialtiesIsNull(PageRequest.of(page, SIZE_PAGE, Sort.by("id").descending()));
        List<UserShortInfoDTO> listUserShortInfoDTO = new UserShortInfoDTO().getListUserShortInfoDTO(usersPage.getContent());

        return new PageImpl<>(listUserShortInfoDTO, usersPage.getPageable(), usersPage.getTotalElements());
    }

    @Override
    public Page<DoctorDTO> getPageableDoctors(int page) {
        Page<User> doctorsPage = userRepository.findAllBySpecialtiesIsNotNull(PageRequest.of(page, SIZE_PAGE, Sort.by("id").descending()));
        List<DoctorDTO> dtoList = new DoctorDTO().getListDoctorDTO(doctorsPage.getContent());

        return new PageImpl<>(dtoList, doctorsPage.getPageable(), doctorsPage.getTotalElements());
    }

    private long getUserDetailsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }
}
