package com.muates.addressservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressAddRequest {

    @NotBlank
    private String addressName;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String neighbourhood;

    @NotBlank
    private String street;

    private String address;
}
