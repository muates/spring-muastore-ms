package com.muates.addressservice.service;

import com.muates.addressservice.model.dto.request.AddressAddRequest;
import com.muates.addressservice.model.dto.request.AddressUpdateRequest;
import com.muates.addressservice.model.entity.Address;

import java.util.List;

public interface AddressService {
    Address addAddress(Long memberId, AddressAddRequest request);
    Address getAddress(Long addressId);
    Address updateAddress(Long addressId, AddressUpdateRequest request);
    List<Address> getAddressesByMemberId(Long memberId);
    void deleteAddress(Long addressId);
}
