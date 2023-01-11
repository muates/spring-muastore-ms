package com.muates.addressservice.service.impl;

import com.muates.addressservice.converter.AddressConverter;
import com.muates.addressservice.model.dto.request.AddressAddRequest;
import com.muates.addressservice.model.dto.request.AddressUpdateRequest;
import com.muates.addressservice.model.entity.Address;
import com.muates.addressservice.repository.AddressRepository;
import com.muates.addressservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    @Override
    public Address addAddress(Long memberId, AddressAddRequest request) {
        int addressCount = addressRepository.findAddressCountByMemberId(memberId);

        if (addressCount > 2) {
            log.error("Member has address count full, memberId: {}", memberId);
            throw new RuntimeException("Member has address count full");
        }

        Address address = AddressConverter.convertToAddress(memberId, request);

        if (addressCount == 0)
            address.setDefaultAddress(true);

        return addressRepository.save(address);
    }

    @Override
    public Address getAddress(Long addressId) {
        Optional<Address> optAddress = addressRepository.findById(addressId);

        if (optAddress.isEmpty()) {
            log.error("Address does not exist, addressId: {}", addressId);
            throw new RuntimeException("Address does not exist");
        }

        return optAddress.get();
    }

    @Override
    public Address updateAddress(Long addressId, AddressUpdateRequest request) {
        Address address = getAddress(addressId);

        if (request.getAddressName() != null) address.setAddressName(request.getAddressName());
        if (request.getCountry() != null) address.setCountry(request.getCountry());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getDistrict() != null) address.setDistrict(request.getDistrict());
        if (request.getNeighbourhood() != null) address.setNeighbourhood(request.getNeighbourhood());
        if (request.getStreet() != null) address.setStreet(request.getStreet());
        if (request.getAddress() != null) address.setAddress(request.getAddress());

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddressesByMemberId(Long memberId) {
        return addressRepository.findAddressesByMemberId(memberId);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = getAddress(addressId);
        addressRepository.delete(address);
    }
}
