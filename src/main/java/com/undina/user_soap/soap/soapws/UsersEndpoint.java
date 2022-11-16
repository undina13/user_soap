package com.undina.user_soap.soap.soapws;

import com.undina.user_soap.user.Role;
import com.undina.user_soap.user.User;
import com.undina.user_soap.user.UserService;
import com.undina.user_soap.user.dto.LoginRequest;
import com.undina.user_soap.user.dto.LoginRequestUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Endpoint
public class UsersEndpoint {
    private static final String NAMESPACE_URI = "http://com.undina.user_soap/soap/soapws";

    private UserService userService;

    @Autowired
    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getAllUsers() {
        GetUsersResponse response = new GetUsersResponse();
        List<UserDetails> userDetailsList = new ArrayList<>();
        List<User> userList = userService.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            UserDetails ob = new UserDetails();
            User user = userList.get(i);
            ob.setLogin(user.getLogin());
            ob.setName(user.getName());
            userDetailsList.add(ob);
        }
        response.getUserDetails().addAll(userDetailsList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserDetailsByLoginRequest")
    @ResponsePayload
    public GetUserDetailsByLoginResponse getUser(@RequestPayload GetUserDetailsByLoginRequest request) {
        GetUserDetailsByLoginResponse response = new GetUserDetailsByLoginResponse();
        UserDetails userDetails = new UserDetails();
        User user = userService.getUserInfo(request.getLogin());
        userDetails.setLogin(user.getLogin());
        userDetails.setName(user.getName());
        List<Role> roleSet = user.getRoles();
        for (Role r : roleSet) {
            RoleDetails roleDetails = new RoleDetails();
            roleDetails.setId(r.getId());
            roleDetails.setName(r.getName());
            userDetails.getRoles().add(roleDetails);
        }
        response.setUserDetails(userDetails);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserDetailsRequest")
    @ResponsePayload
    public DeleteUserDetailsResponse deleteUser(@RequestPayload DeleteUserDetailsRequest request) {
        User user = userService.getUserInfo(request.getLogin());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null) {
            serviceStatus.setStatus("FALSE");
            serviceStatus.setMessage("Content Not Available");
        } else {
            userService.deleteUser(request.getLogin());
            serviceStatus.setStatus("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteUserDetailsResponse response = new DeleteUserDetailsResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserDetailsRequest")
    @ResponsePayload
    public AddUserDetailsResponse addUser(@RequestPayload AddUserDetailsRequest request) {
        AddUserDetailsResponse response = new AddUserDetailsResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        UserDetails userDetails = new UserDetails();
        userDetails.setLogin(request.getLogin());
        userDetails.setName(request.getName());
        userDetails.setPassword(request.getPassword());

        List<String> stringListException = verifyForm(userDetails);

        if (stringListException.isEmpty()) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLogin(request.getLogin());
            loginRequest.setName(request.getName());
            loginRequest.setPassword(request.getPassword());
            List<Integer> listRolesIds = request.getRolesIds();
            loginRequest.setRoles(listRolesIds);
            userService.signup(loginRequest);
            response.setUserDetails(userDetails);
            serviceStatus.setStatus("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        } else {
            for (String s : stringListException) {
                ErrorSOAP errorSOAP = new ErrorSOAP();
                errorSOAP.setMessage(s);
                serviceStatus.getError().add(errorSOAP);
            }
            serviceStatus.setStatus("FALSE");
            serviceStatus.setMessage("Received Data Verification Error. Added Failed");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserDetailsRequest")
    @ResponsePayload
    public UpdateUserDetailsResponse updateUser(@RequestPayload UpdateUserDetailsRequest request) {
        LoginRequestUpdate loginRequestUpdate = new LoginRequestUpdate();
        ServiceStatus serviceStatus = new ServiceStatus();
        UserDetails userDetails = new UserDetails();
        userDetails.setLogin(request.getLogin());
        userDetails.setName(request.getName());
        userDetails.setPassword(request.getPassword());
        List<String> stringListException = verifyForm(userDetails);
        if (stringListException.isEmpty()) {
            loginRequestUpdate.setLogin(request.getLogin());
            loginRequestUpdate.setName(request.getName());
            loginRequestUpdate.setPassword(request.getPassword());
            List<Integer> rolesIds = request.getRolesIds();
            loginRequestUpdate.setRoles(rolesIds);
            userService.update(loginRequestUpdate);
            serviceStatus.setStatus("SUCCESS");
            serviceStatus.setMessage("Content Updated Successfully");

        } else {
            for (String s : stringListException) {
                ErrorSOAP errorSOAP = new ErrorSOAP();
                errorSOAP.setMessage(s);
                serviceStatus.getError().add(errorSOAP);
            }
            serviceStatus.setStatus("FALSE");
            serviceStatus.setMessage("Received Data Verification Error. Updated Failed");
        }

        UpdateUserDetailsResponse response = new UpdateUserDetailsResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    public List<String> verifyPassword(String password) {

        List<String> stringListException = new ArrayList<>();
        if (!Pattern.matches(".*[0-9].*", password)) {
            stringListException.add("Пароль должен содержать минимум 1 цифру");
        }
        if (!Pattern.matches(".*[A-ZА-ЯЁ].*", password)) {
            stringListException.add("Пароль должен содержать минимум 1 букву в заглавном регистре");
        }
        return stringListException;
    }

    public List<String> verifyForm(UserDetails userDetails) {
        List<String> stringListException = new ArrayList<>();
        if (userDetails.getLogin().isEmpty() || userDetails.getLogin() == null) {
            stringListException.add("поле Login обязательно к заполнению");
        }
        if (userDetails.getName().isEmpty() || userDetails.getName() == null) {
            stringListException.add("поле Username обязательно к заполнению");
        }
        if (userDetails.getPassword().isEmpty() || userDetails.getPassword() == null) {
            stringListException.add("поле Password обязательно к заполнению");
        } else {
            List<String> passwordException = verifyPassword(userDetails.getPassword());
            if (!passwordException.isEmpty()) {
                stringListException.addAll(passwordException);
            }
        }
        return stringListException;
    }

}
