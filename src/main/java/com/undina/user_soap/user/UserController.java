//package com.undina.user_soap.user;
//
//
//import com.undina.user_soap.security.SecurityUser;
//import com.undina.user_soap.user.dto.LoginRequest;
//import com.undina.user_soap.user.dto.LoginRequestUpdate;
//import com.undina.user_soap.validation.exceptions.ApplicationException;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.validation.Errors;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@RestController
//@Slf4j
//@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
//@Validated
//public class UserController {
//
//    static final String REST_URL = "/users";
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//
//
//
//    public UserController(UserService userService, AuthenticationManager authenticationManager) {
//        this.userService = userService;
//        this.authenticationManager = authenticationManager;
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest, Errors errors) {
//        log.info("authenticate {}", loginRequest);
//
//        UsernamePasswordAuthenticationToken authInputToken =
//                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
//        try {
//            authenticationManager.authenticate(authInputToken);
//        } catch (BadCredentialsException ex) {
//            throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
//        }
//        return ResponseEntity.ok(userService.login(loginRequest));
//    }
//
//
//    @PostMapping("/signup")
//    @Validated
//    public ResponseEntity<?> registerUser( @RequestBody @Valid LoginRequest loginRequest) {
//        log.info("register {}", loginRequest);
//               return new ResponseEntity<>(userService.signup(loginRequest), HttpStatus.CREATED);
//    }
//
//
//    @GetMapping("/me")
//    public ResponseEntity<?> me() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            Object principal = auth.getPrincipal();
//            if (principal instanceof SecurityUser) {
//                User user = ((SecurityUser) principal).getUser();
//                return ResponseEntity.ok(user);
//            }
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
//    }
//    @GetMapping("/all")
//    public ResponseEntity<?> getAll() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            Object principal = auth.getPrincipal();
//            if (principal instanceof SecurityUser) {
//
//                return ResponseEntity.ok(userService.getAllUsers());
//            }
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
//    }
//
//    @PatchMapping("")
//    public ResponseEntity<?> update(@RequestBody @Valid LoginRequestUpdate loginRequest) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            Object principal = auth.getPrincipal();
//            if (principal instanceof SecurityUser) {
//
//                return ResponseEntity.ok(userService.update(loginRequest));
//            }
//        }
//        throw new ApplicationException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
//    }
//
//
//}
