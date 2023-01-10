package com.muates.memberservice.converter;

import com.muates.memberservice.model.dto.request.MemberCreateRequest;
import com.muates.memberservice.model.dto.response.MemberResponse;
import com.muates.memberservice.model.entity.Member;
import com.muates.memberservice.model.enums.Gender;

import java.util.Date;

public class MemberConverter {

    public static Member convertToMember(Long userId, MemberCreateRequest request) {
        if (request == null)
            return null;

        return Member.builder()
                .id(userId)
                .email(request.getEmail())
                .isActive(true)
                .createdDate(new Date())
                .build();
    }

    public static MemberResponse convertToResponse(Member member) {
        if (member == null)
            return null;

        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getFirstName() + " " + member.getLastName())
                .email(member.getEmail())
                .identity(member.getIdentity())
                .phone(member.getPhone())
                .gender(member.getGender().toString())
                .age(new Date().getYear() - member.getBirthDate().getYear())
                .build();
    }

    public static Gender convertToGender(String gender) {
        if (gender == null)
            return null;

        switch (gender) {
            case "MALE":
                return Gender.MALE;
            case "FEMALE":
                return Gender.FEMALE;
            default:
                return null;
        }
    }
}
