package com.mooncake.springsix.section3;

import java.io.IOException;
import java.math.BigDecimal;

// 또다른 구현체, 고정 환율 반환
public class a_SimpleExRatePaymentService extends a_PaymentServiceV3 {
    @Override
    BigDecimal getExRate(String currency) throws IOException {
        if(currency.equals("USD")) return BigDecimal.valueOf(1000);
        throw new IllegalArgumentException("지원되지 않는 통화입니다");
    }
}
