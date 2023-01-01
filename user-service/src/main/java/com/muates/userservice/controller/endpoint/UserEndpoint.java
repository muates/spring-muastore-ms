package com.muates.userservice.controller.endpoint;

public class UserEndpoint {

    public static final String PATH = "/user";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String REGISTER = API + PATH + V1 + "/register";
    public static final String GET_USER = API + PATH + V1 + "/get/{userId}";
    public static final String CHANGE_PASSWORD = API + PATH + V1 + "/change/pw/{userId}";
    public static final String DELETE_USER = API + PATH + V1 + "/delete/{userId}";
}
