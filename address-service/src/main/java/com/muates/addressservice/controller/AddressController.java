package com.muates.addressservice.controller;

import com.muates.addressservice.converter.AddressConverter;
import com.muates.addressservice.model.dto.request.AddressAddRequest;
import com.muates.addressservice.model.dto.request.AddressUpdateRequest;
import com.muates.addressservice.model.dto.response.AddressResponse;
import com.muates.addressservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static com.muates.addressservice.controller.endpoint.AddressEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AddressController {

    private final AddressService addressService;

    @PostMapping(ADD_ADDRESS)
    public AddressResponse addAddress(@PathVariable Long memberId,
                                      @RequestBody @Valid AddressAddRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ADD_ADDRESS).toUriString());
        return ResponseEntity.created(uri)
                .body(AddressConverter.convertToResponse(addressService.addAddress(memberId, request)))
                .getBody();
    }

    @GetMapping(GET_ADDRESS)
    public AddressResponse getAddress(@PathVariable Long addressId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(GET_ADDRESS).toUriString());
        return ResponseEntity.created(uri)
                .body(AddressConverter.convertToResponse(addressService.getAddress(addressId)))
                .getBody();
    }

    @PutMapping(UPDATE_ADDRESS)
    public AddressResponse updateAddress(@PathVariable Long addressId,
                                         @RequestBody @Valid AddressUpdateRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(UPDATE_ADDRESS).toUriString());
        return ResponseEntity.created(uri)
                .body(AddressConverter.convertToResponse(addressService.updateAddress(addressId, request)))
                .getBody();
    }

    @GetMapping(GET_ADDRESSES_BY_MEMBER_ID)
    public List<AddressResponse> getAddressesByMemberId(@PathVariable Long memberId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(UPDATE_ADDRESS).toUriString());
        return ResponseEntity.created(uri)
                .body(AddressConverter.convertToResponse(addressService.getAddressesByMemberId(memberId)))
                .getBody();
    }

    @DeleteMapping(DELETE_ADDRESS)
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }
}
