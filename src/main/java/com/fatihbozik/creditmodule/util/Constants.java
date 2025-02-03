package com.fatihbozik.creditmodule.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
    public static final Set<Integer> ALLOWED_INSTALLMENTS = Set.of(6, 9, 12, 24);
    public static final int INSTALLMENT_PAYMENT_WINDOW_MONTHS = 3;
    public static final BigDecimal DISCOUNT_PENALTY_RATE = BigDecimal.valueOf(0.001);
}
