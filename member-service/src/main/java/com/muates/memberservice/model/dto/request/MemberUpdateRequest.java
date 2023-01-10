package com.muates.memberservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    private String firstName;
    private String lastName;
    private String identity;
    private String phone;
    private String gender;
    private Date birthDate;
}
