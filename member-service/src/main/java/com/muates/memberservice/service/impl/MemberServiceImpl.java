package com.muates.memberservice.service.impl;

import com.muates.memberservice.converter.MemberConverter;
import com.muates.memberservice.model.dto.request.MemberCreateRequest;
import com.muates.memberservice.model.dto.request.MemberUpdateRequest;
import com.muates.memberservice.model.entity.Member;
import com.muates.memberservice.repository.MemberRepository;
import com.muates.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    @Override
    public void createMember(Long userId, MemberCreateRequest request) {
        Member member = memberRepository.save(MemberConverter.convertToMember(userId, request));
        log.info("Member created, memberId: {}", member.getId());
    }

    @Override
    public Member updateMember(Long memberId, MemberUpdateRequest request) {
        Member member = getMember(memberId);

        if (request.getFirstName() != null) member.setFirstName(request.getFirstName());
        if (request.getLastName() != null) member.setLastName(request.getLastName());
        if (request.getIdentity() != null) member.setIdentity(request.getIdentity());
        if (request.getPhone() != null) member.setPhone(request.getPhone());
        if (request.getGender() != null) member.setGender(MemberConverter.convertToGender(request.getGender()));
        if (request.getBirthDate() != null) member.setBirthDate(request.getBirthDate());

        return memberRepository.save(member);
    }

    @Override
    public Member getMember(Long memberId) {
        Optional<Member> optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty()) {
            log.error("Member does not found, memberId: {}", memberId);
            throw new RuntimeException("Member does not found");
        }

        return optMember.get();
    }

    @Override
    public void deleteMember(Long memberId) {
        Member member = getMember(memberId);
        memberRepository.delete(member);
        log.info("Member deleted, memberId: {}", memberId);
    }
}
