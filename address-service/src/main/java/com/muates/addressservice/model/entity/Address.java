package com.muates.addressservice.model.entity;

import com.muates.commonservice.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static com.muates.addressservice.model.entity.schema.AddressSchema.*;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ADDRESS_NAME)
    private String addressName;

    @Column(name = COUNTRY)
    private String country;

    @Column(name = CITY)
    private String city;

    @Column(name = DISTRICT)
    private String district;

    @Column(name = NEIGHBOURHOOD)
    private String neighbourhood;

    @Column(name = STREET)
    private String street;

    @Column(name = ADDRESS)
    private String address;

    @Column(name = USER_ID)
    private Long memberId;

    @Column(name = DEFAULT_ADDRESS)
    private Boolean defaultAddress = false;
}
