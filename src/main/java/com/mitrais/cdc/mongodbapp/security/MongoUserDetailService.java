package com.mitrais.cdc.mongodbapp.security;

import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoUserDetailService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.info("Find username data for user:"+s);
        User user = userRepository.findByUsername(s);
        log.info("User Role :"+ user.getRole());

        return new MongoUserDetails(user);
    }
}
