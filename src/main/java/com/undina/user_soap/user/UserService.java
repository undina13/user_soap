package com.undina.user_soap.user;


import com.undina.user_soap.security.JWTToken;
import com.undina.user_soap.security.JWTUtil;
import com.undina.user_soap.user.dto.LoginRequest;
import com.undina.user_soap.user.dto.LoginRequestUpdate;
import com.undina.user_soap.validation.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final JpaUserRepository userRepository;
    private final JpaRoleRepository roleRepository;
    private final JWTUtil jwtUtil;


    public UserService(JpaUserRepository jpaUserRepository, JpaRoleRepository roleRepository, JWTUtil jwtUtil) {
        this.userRepository = jpaUserRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    public JWTToken login(LoginRequest loginRequest) {
        User user = getUserInfo(loginRequest.getLogin());
        String token = jwtUtil.generateToken(user.getLogin());
        return new JWTToken(user.getLogin(), token);
    }

    public JWTToken signup(LoginRequest loginRequest) {
        List<Role>roles = roleRepository.getAllByIdIn(loginRequest.getRoles());
        User user = new User(loginRequest.getLogin(), loginRequest.getPassword(), loginRequest.getName(), roles);

        User registratedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(registratedUser.getLogin());
        return new JWTToken(registratedUser.getLogin(), token);
    }

    public User getUserInfo(String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new ApplicationException(HttpStatus.NOT_FOUND, "Not found"));
    }

    public User update(LoginRequestUpdate loginRequestUpdate){
        User user = userRepository.findByLogin(loginRequestUpdate.getLogin()).orElseThrow(() ->
                new ApplicationException(HttpStatus.NOT_FOUND, "Not found"));
        if(loginRequestUpdate.getName()!=null){
            user.setName(loginRequestUpdate.getName());
        }
        if(loginRequestUpdate.getPassword()!=null){
            user.setPassword(loginRequestUpdate.getPassword());
        }
        if(loginRequestUpdate.getRoles()!=null){
            user.setRoles(roleRepository.getAllByIdIn(loginRequestUpdate.getRoles()));
        }
     return  userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

public void deleteUser(String login){
    User user = userRepository.findByLogin(login).orElseThrow(() ->
            new ApplicationException(HttpStatus.NOT_FOUND, "Not found"));
      userRepository.delete(user);
}

}
