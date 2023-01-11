package com.muates.addressservice.repository;

import com.muates.addressservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(
            value = "SELECT COUNT(*) FROM addresses WHERE member_id = :memberId",
            nativeQuery = true
    )
    int findAddressCountByMemberId(@Param(value = "memberId") Long memberId);

    List<Address> findAddressesByMemberId(Long memberId);
}
