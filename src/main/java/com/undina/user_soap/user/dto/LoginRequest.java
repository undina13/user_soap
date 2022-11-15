package com.undina.user_soap.user.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@ToString
public class LoginRequest {

    @NotBlank(message = "Login can't be blank")
    @NotNull(message = "Login can't be null")
    private String login;
    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;
    @NotBlank(message = "Password can't be blank")
    private String name;
    private Set<Integer> roles;

}
