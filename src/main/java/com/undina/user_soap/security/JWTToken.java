package com.undina.user_soap.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTToken {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("access_token")
    private String accessToken;
}
