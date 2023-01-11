package com.muates.addressservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private Long id;
    private String addressName;
    private String country;
    private String city;
    private String district;
    private String neighbourhood;
    private String street;
    private String address;
    private Long memberId;
}
