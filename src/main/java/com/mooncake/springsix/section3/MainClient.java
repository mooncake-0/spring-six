package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;

import java.io.IOException;
import java.math.BigDecimal;

public class MainClient {

    public static void main(String[] args) throws IOException {

        a_PaymentServiceV3 service = new a_WebApiExRatePaymentService(); // 이번에 사용할 구현체
        a_PaymentServiceV3 simpleExRateService = new a_SimpleExRatePaymentService(); // 이번에 사용할 구현체
        Payment payment = service.prepareV3(100L, "USD", BigDecimal.valueOf(50.7)); // 50.7 USD 짜리 제품에 대한 구매 처리 중
        Payment payment2 = simpleExRateService.prepareV3(100L, "USD", BigDecimal.valueOf(50.7)); // 50.7 USD 짜리 제품에 대한 구매 처리 중

        System.out.println(payment);
        System.out.println(payment2);
        System.out.println("V3 -----------------------");

        //
        b_PaymentServiceV4 serviceV4 = new b_PaymentServiceV4();
        Payment payment4 = serviceV4.prepareV4(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment4);
        System.out.println("V4 -----------------------");

        e_PaymentServiceV6 serviceV6 = new e_PaymentServiceV6(new c_WebApiExRateProvider()); // Client 쪽에서 사용할 것을 결정할 수 있다
        Payment payment6 = serviceV6.prepareV6(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment6);
        System.out.println("V6 -----------------------");



    }
}
