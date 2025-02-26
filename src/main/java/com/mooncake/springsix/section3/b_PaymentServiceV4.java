package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class b_PaymentServiceV4 {

    /*
     상속을 통한 확장은 한계가 존재한다
     > 클래스를 분리한다 - "다른 클래스에서 환율 정보를 가져오자"
     > 각자 자신이 하는 일에만 충실해 보인다
     */

    /*
     한계
     - 만약 ExRateProvider 를 바꾸고 싶다면 어떻게 해야 하는가? 단순히 클래스 분리도 지속 변경을 요구하게 된다
     - 가령, SimpleExRateProvider 로 바꾸려면.. 의존하는 Class, 구현체, method 도 모두 바꿔야 한다
     ---- V4 에서는 SimpleExRateProvider 랑 WebApiProvider 랑 아예 다른 클래스기 떄문이다 (일부러 함수명도 다르게 함)
     */
    private final b_WebApiExRateProvider exRateProvider; // 의존하고 있는 대상이 어떤지도 확실히 보인다

    public b_PaymentServiceV4() {
        this.exRateProvider = new b_WebApiExRateProvider();
    }

    public Payment prepareV4(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        // 타 클래스의 기능을 사용한다
        BigDecimal exRate = exRateProvider.getWebExRate(currency);  // "환율 가져오기 주석 삭제" - getExRate 라고 적혀있다

        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }
}