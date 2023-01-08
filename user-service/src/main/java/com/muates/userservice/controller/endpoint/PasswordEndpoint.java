package com.muates.userservice.controller.endpoint;

public class PasswordEndpoint {
    public static final String PATH = "/password";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String CHANGE_PASSWORD = API + PATH + V1 + "/change/{userId}";
    public static final String UPDATE_PASSWORD = API + PATH + V1 + "/update";
    public static final String SEND_TOKEN = API + PATH + V1 + "/send/token/{email}";
}
