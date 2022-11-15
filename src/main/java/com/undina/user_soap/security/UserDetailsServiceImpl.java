package com.undina.user_soap.security;


import com.undina.user_soap.user.JpaUserRepository;
import com.undina.user_soap.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final JpaUserRepository repository;

    public UserDetailsServiceImpl(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByLogin(email).orElseThrow(() ->
            new UsernameNotFoundException("User doesn't exists"));
        return new SecurityUser(user);
    }


}
