package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class e_PaymentServiceV6 {

    /*
    나머지는 동일하기 때문에 V5 의 것들을 사용한다 (interface, 구현체 등)
     */

    private final c_ExRateProvider exRateProvider;

    // 관계 설정 책임을 Client 에게 준다 -> exRateProvider 가 어떤 구현체인지는 PaymentService 가 아무 신경쓰지 않는다
    public e_PaymentServiceV6(c_ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepareV6(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        // 타 클래스의 기능을 사용한다
        BigDecimal exRate = exRateProvider.getExRate(currency);  // "환율 가져오기 주석 삭제" - getExRate 라고 적혀있다

        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }
}