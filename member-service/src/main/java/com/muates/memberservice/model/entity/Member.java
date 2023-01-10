package com.muates.memberservice.model.entity;

import com.muates.commonservice.model.entity.BaseEntity;
import com.muates.memberservice.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.Date;

import static com.muates.memberservice.model.entity.schema.MemberSchema.*;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Member extends BaseEntity {

    @Id
    private Long id;

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @Column(name = EMAIL, unique = true)
    private String email;

    @Column(name = IDENTITY)
    private String identity;

    @Column(name = PHONE)
    private String phone;

    @Column(name = GENDER)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = BIRTH_DATE)
    private Date birthDate;

    @Column(name = IS_ACTIVE)
    private Boolean isActive = false;
}
