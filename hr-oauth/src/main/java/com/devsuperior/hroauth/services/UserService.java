package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public User findByEmail(String email){
        User user = userFeignClient.findByEmail(email).getBody();
        if (user == null){
            log.error("Email nao encontrado: " + email);
            throw new IllegalArgumentException("Email nao encontrado");
        }
        log.info("Email found: " + email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFeignClient.findByEmail(username).getBody();
        if (user == null){
            log.error("Email nao encontrado: " + username);
            throw new IllegalArgumentException("Email nao encontrado");
        }
        log.info("Email found: " + username);
        return user;
    }
}
