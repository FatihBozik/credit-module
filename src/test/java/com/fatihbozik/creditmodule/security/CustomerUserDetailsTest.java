package com.fatihbozik.creditmodule.security;

import com.fatihbozik.creditmodule.model.RoleEntity;
import com.fatihbozik.creditmodule.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerUserDetailsTest {
    private UserEntity userEntity;
    private CustomerUserDetails customerUserDetails;

    @BeforeEach
    void setUp() {
        RoleEntity role = new RoleEntity();
        role.setName("ROLE_USER");

        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("password");
        userEntity.setRoles(Set.of(role));
        userEntity.setEnabled(true);

        customerUserDetails = new CustomerUserDetails(userEntity);
    }

    @Test
    void getAuthorities_shouldReturnGrantedAuthorities() {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) customerUserDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.get(0).getAuthority());
    }

    @Test
    void getPassword_shouldReturnPassword() {
        assertEquals("password", customerUserDetails.getPassword());
    }

    @Test
    void getUsername_shouldReturnUsername() {
        assertEquals("testuser", customerUserDetails.getUsername());
    }

    @Test
    void isEnabled_shouldReturnTrue() {
        assertTrue(customerUserDetails.isEnabled());
    }
}
