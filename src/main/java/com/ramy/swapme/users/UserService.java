package com.ramy.swapme.users;

import com.ramy.swapme.role.RoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final MapperFacade mapper;

    @Autowired
    public UserService(UserRepository userRepository, MapperFacade mapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        encoder = new BCryptPasswordEncoder();
    }


    public UserDTO returnUserById(Long userId) {
        //from userEntity to userDto
        UserEntity userEntity = userRepository.getOne(userId);
        UserDTO userDTO = mapper.map(userEntity, UserDTO.class);
        return userDTO;


    }




    public UserDTO registerUser(UserDTO userDTO) {

        //mapping from UserDTO to UserEntity
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);

        //take the password from the form and encode it
        String secret = "{bcrypt}" + encoder.encode(userEntity.getPassword());
        userEntity.setPassword(secret);
        userEntity.setConfirmPassword(secret);

//      //assign a role to this user
        userEntity.addRole(roleService.findByName("ROLE_USER"));

        //save user
        userRepository.save(userEntity);

        return userDTO;
    }


}
