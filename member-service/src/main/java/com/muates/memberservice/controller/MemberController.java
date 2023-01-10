package com.muates.memberservice.controller;

import com.muates.memberservice.converter.MemberConverter;
import com.muates.memberservice.model.dto.request.MemberCreateRequest;
import com.muates.memberservice.model.dto.request.MemberUpdateRequest;
import com.muates.memberservice.model.dto.response.MemberResponse;
import com.muates.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static com.muates.memberservice.controller.endpoint.MemberEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    @PostMapping(CREATE_MEMBER)
    public void createMember(@PathVariable Long userId,
                             @RequestBody @Valid MemberCreateRequest request) {
        memberService.createMember(userId, request);
    }

    @PutMapping(UPDATE_MEMBER)
    public MemberResponse updateMember(@PathVariable Long memberId,
                                       @RequestBody MemberUpdateRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(UPDATE_MEMBER).toUriString());
        return ResponseEntity.created(uri)
                .body(MemberConverter.convertToResponse(memberService.updateMember(memberId, request)))
                .getBody();
    }

    @GetMapping(GET_MEMBER)
    public MemberResponse getMember(@PathVariable Long memberId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(GET_MEMBER).toUriString());
        return ResponseEntity.created(uri)
                .body(MemberConverter.convertToResponse(memberService.getMember(memberId)))
                .getBody();
    }

    @DeleteMapping(DELETE_MEMBER)
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
}
