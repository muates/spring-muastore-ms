package com.muates.userservice.controller.endpoint;

public class RoleEndpoint {

    public static final String PATH = "/role";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String ADD_ROLE = API + PATH + V1 + "/add";
    public static final String UPDATE_ROLE = API + PATH + V1 + "/update/{roleId}";
    public static final String GET_ROLE = API + PATH + V1 + "/get/{roleId}";
    public static final String GET_ROLES = API + PATH + V1 + "/get";
    public static final String DELETE_ROLE = API + PATH + V1 + "/delete/{roleId}";
}
