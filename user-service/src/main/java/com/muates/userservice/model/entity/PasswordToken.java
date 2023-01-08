package com.muates.userservice.model.entity;

import com.muates.commonservice.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static com.muates.userservice.model.entity.schema.PasswordTokenSchema.*;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PasswordToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TOKEN)
    private String token;

    @Column(name = USER_ID)
    private Long userId;

    @Column(name = ENABLE)
    private Boolean enable = true;
}
