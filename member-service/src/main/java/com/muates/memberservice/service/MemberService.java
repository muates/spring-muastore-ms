package com.muates.memberservice.service;

import com.muates.memberservice.model.dto.request.MemberCreateRequest;
import com.muates.memberservice.model.dto.request.MemberUpdateRequest;
import com.muates.memberservice.model.entity.Member;

public interface MemberService {
    void createMember(Long userId, MemberCreateRequest request);
    Member updateMember(Long memberId, MemberUpdateRequest request);
    Member getMember(Long memberId);
    void deleteMember(Long memberId);
}
