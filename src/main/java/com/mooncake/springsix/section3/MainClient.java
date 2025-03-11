package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        System.out.println("V6 -- V5의 인터페이스가 적용되어 사용하는 함수단에서 필요한 구현체를 Client 에서 주입하는 모습 -----------------------");

        // ObjectFactory 가 정의해준 PaymentService 를 가져와서 사용해주는 방식으로 변경
        MyObjectFactory objectFactory = new MyObjectFactory();
        e_PaymentServiceV6 paymentServiceV7 = objectFactory.paymentService();
        Payment payment7 = paymentServiceV7.prepareV6(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment7);
        System.out.println("V7 MyObjectFactory 까지의 연계 -----------------------");

        /////////////////////////////////////////////
        // V8 --- 3-9 강의 : Spring 이 해주는 역할체를 사용
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(MyObjectFactory.class);
        e_PaymentServiceV6 paymentServiceV8 = beanFactory.getBean(e_PaymentServiceV6.class);
        Payment payment8 = paymentServiceV8.prepareV6(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment7);
        System.out.println("V8 Spring 개입 시작: Bean 등록 및 관리한 모습 -----------------------");


    }
}
