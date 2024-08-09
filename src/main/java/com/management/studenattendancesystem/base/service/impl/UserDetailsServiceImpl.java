package com.management.studenattendancesystem.base.service.impl;

import com.management.studenattendancesystem.base.repository.UserRepository;
import com.management.studenattendancesystem.base.rest.model.UserDetailsCred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmailAndStatusActive(email)
                .map(UserDetailsCred::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username :" + email));
    }
}
