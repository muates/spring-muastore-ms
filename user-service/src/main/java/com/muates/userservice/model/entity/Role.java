package com.muates.userservice.model.entity;

import com.muates.commonservice.model.entity.BaseEntity;
import com.muates.userservice.model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static com.muates.userservice.model.entity.schema.RoleSchema.*;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ROLE_NAME)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    //Todo: role için oto ekleme ayarlanacak
}
