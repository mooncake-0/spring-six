package com.mooncake.springsix.section5_tpl_ref;

import com.mooncake.springsix.Payment;
import com.mooncake.springsix.section3.last_versions.LastPaymentService;
import com.mooncake.springsix.section3.last_versions.LastWebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {

    public static void main(String[] args) throws IOException {

        // Template Callback 으로 리펙토링 들어가기 전 Section3 의 마지막 모습을 복습해본다
//        LastPaymentService service = new LastPaymentService(new LastWebApiExRateProvider());

        // 템플릿 콜백으로 적용된 V4 로 바꿔봐도, 정상 동작한다 -> 리펙토링 성공
        LastPaymentService service = new LastPaymentService(new WebApiExRateProviderV4());
        Payment payment = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment);
    }
}
