package com.muates.memberserviceclient.client;

import com.muates.memberserviceclient.model.MemberCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "member-service")
public interface MemberServiceClient {

    @PostMapping("/api/member/v1/create/{userId}")
    void createMember(@PathVariable Long userId, @RequestBody MemberCreateRequest request);

    @DeleteMapping("/api/member/v1/delete/{memberId}")
    void deleteMember(@PathVariable Long memberId);
}
