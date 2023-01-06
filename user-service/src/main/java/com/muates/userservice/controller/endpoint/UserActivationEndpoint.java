package com.muates.userservice.controller.endpoint;

public class UserActivationEndpoint {
    public static final String PATH = "/user";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String ACTIVATE_USER = API + PATH + V1 + "/activation/{token}";
}
