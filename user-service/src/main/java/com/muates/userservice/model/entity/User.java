package com.muates.userservice.model.entity;

import com.muates.commonservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.muates.userservice.model.entity.schema.UserSchema.*;

@Entity
@Table(name = TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = USERNAME, unique = true)
    private String username;

    @Column(name = EMAIL, unique = true)
    private String email;

    @Column(name = PASSWORD)
    private String password;

    @Column(name = ENABLED)
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = USER_ROLE_TABLE,
            joinColumns = {@JoinColumn(name = USER_ID, referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = ROLE_ID, referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();
}
