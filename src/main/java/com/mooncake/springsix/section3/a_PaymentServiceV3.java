package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class a_PaymentServiceV3 {

    /*
     기존 V2의 문제점
     > method 분리는 되었지만, 계속 바뀌어야 하는 부분이 있음
     > 가령, 외부에서 getExRate 를 사용하려고 하는데, 환율 정보 가져오는 곳이 따로 있다고 URL 만 바꾸고 싶다고 한다는 등..
     */

    /*
     이제는 상속을 사용하여, "환율 가져오는 방식"은 Client 단에서 Customize 할 수 있게끔 제공한다
     - 자신들의 환율 DB 에서 가져온다는 등..
     - 소스코드는 주지 않고, 컴파일된 a_PaymentServiceV3 클래스 파일만 넘겨준다
     - 그리고 이 클래스를 상속 받도록 외부 Client 는 사용하면, 자신이 원하는 곳에서 환율 정보를 가져올 수 있다

     상속을 통한 확장은 실제 디자인 패턴 - Factory Method, Template 등이 상속을 통해 확장을 지원
     >> 하지만 상속을 통한 확장은 한계가 존재!! ex 기술적인 한계 (Java 는 다중 상속 불가)
     */

    public Payment prepareV3(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        BigDecimal exRate = getExRate(currency);  // "환율 가져오기 주석 삭제" - getExRate 라고 적혀있다

        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }

    abstract BigDecimal getExRate(String currency) throws IOException;
}