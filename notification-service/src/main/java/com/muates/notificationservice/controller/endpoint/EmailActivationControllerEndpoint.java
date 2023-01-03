package com.muates.notificationservice.controller.endpoint;

public class EmailActivationControllerEndpoint {

    public static final String PATH = "/email";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String UPDATE_ENABLE = API + PATH + V1 + "/update/enable/{userId}";
    public static final String IS_ENABLE = API + PATH + V1 + "/enable/{userId}";
}
