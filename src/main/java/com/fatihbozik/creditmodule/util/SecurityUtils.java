package com.fatihbozik.creditmodule.util;

import com.fatihbozik.creditmodule.model.UserEntity;
import com.fatihbozik.creditmodule.security.CustomerUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {
    public static UserEntity getCurrentUser() {
        return getUserOptional().orElseThrow(() -> new IllegalArgumentException("User entity not found"));
    }

    public static Optional<UserEntity> getUserOptional() {
        return getUserDetailsOptional()
                .map(CustomerUserDetails::delegate);
    }

    public static Optional<CustomerUserDetails> getUserDetailsOptional() {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .filter(CustomerUserDetails.class::isInstance)
                .map(CustomerUserDetails.class::cast);
    }

    private static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication);
    }
}
