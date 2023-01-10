package com.muates.memberservice.controller.endpoint;

public class MemberEndpoint {
    public static final String PATH = "/member";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String CREATE_MEMBER = API + PATH + V1 + "/create/{userId}";
    public static final String UPDATE_MEMBER = API + PATH + V1 + "/update/{memberId}";
    public static final String GET_MEMBER = API + PATH + V1 + "/get/{memberId}";
    public static final String DELETE_MEMBER = API + PATH + V1 + "/delete/{memberId}";
}
