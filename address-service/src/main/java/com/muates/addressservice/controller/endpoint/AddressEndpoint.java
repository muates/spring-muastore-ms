package com.muates.addressservice.controller.endpoint;

public class AddressEndpoint {
    public static final String PATH = "/address";
    public static final String API = "/api";
    public static final String V1 = "/v1";

    public static final String ADD_ADDRESS = API + PATH + V1 + "/add/{memberId}";
    public static final String GET_ADDRESS = API + PATH + V1 + "/get/{addressId}";
    public static final String UPDATE_ADDRESS = API + PATH + V1 + "/update/{addressId}";
    public static final String GET_ADDRESSES_BY_MEMBER_ID = API + PATH + V1 + "/get/member/{memberId}";
    public static final String DELETE_ADDRESS = API + PATH + V1 + "/delete/{addressId}";
}
