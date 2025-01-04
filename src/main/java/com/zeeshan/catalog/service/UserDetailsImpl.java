package com.zeeshan.catalog.service;

import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.getUserByUsername(username);
        if(user!=null){
            System.out.println("user "+user.getUsername()+", pass "+user.getPassword());
            return User.builder().username(user.getUsername())
                    .password((user.getPassword()))
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found: "+ username);
    }

}

