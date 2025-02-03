package com.fatihbozik.creditmodule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    @OneToOne(mappedBy = "user")
    private CustomerEntity customer;

    @JsonIgnore
    public void addRole(String roleName) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        RoleEntity role = new RoleEntity();
        role.setName(roleName);
        this.roles.add(role);
    }

    @JsonIgnore
    public boolean hasAdmin() {
        return this.roles.stream().anyMatch(role -> role.getName().equals("ADMIN"));
    }
}
