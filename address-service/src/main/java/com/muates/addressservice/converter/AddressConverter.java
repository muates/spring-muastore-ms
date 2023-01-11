package com.muates.addressservice.converter;

import com.muates.addressservice.model.dto.request.AddressAddRequest;
import com.muates.addressservice.model.dto.response.AddressResponse;
import com.muates.addressservice.model.entity.Address;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddressConverter {

    public static Address convertToAddress(Long memberId, AddressAddRequest request) {
        if (request == null)
            return null;

        return Address.builder()
                .addressName(request.getAddressName())
                .country(request.getCountry())
                .city(request.getCity())
                .district(request.getDistrict())
                .neighbourhood(request.getNeighbourhood())
                .street(request.getStreet())
                .address(request.getAddress())
                .memberId(memberId)
                .defaultAddress(false)
                .createdDate(new Date())
                .build();
    }

    public static AddressResponse convertToResponse(Address address) {
        if (address == null)
            return null;

        return AddressResponse.builder()
                .id(address.getId())
                .addressName(address.getAddressName())
                .country(address.getCountry())
                .city(address.getCity())
                .district(address.getDistrict())
                .neighbourhood(address.getNeighbourhood())
                .street(address.getStreet())
                .address(address.getAddress())
                .memberId(address.getMemberId())
                .build();
    }

    public static List<AddressResponse> convertToResponse(List<Address> addresses) {
        if (addresses.isEmpty())
            return Collections.emptyList();

        return addresses.stream()
                .map(AddressConverter::convertToResponse)
                .collect(Collectors.toList());
    }
}
