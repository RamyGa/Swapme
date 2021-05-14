package com.ramy.swapme.security;



import com.ramy.swapme.users.UserEntity;
import com.ramy.swapme.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override//will pass the email in from the form and return an authenticated user
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//email passed in from the form
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);//
        if(userEntity.isEmpty()){
            throw new UsernameNotFoundException(email);

        }
        return userEntity.get();//returns user if they exist
    }



}
