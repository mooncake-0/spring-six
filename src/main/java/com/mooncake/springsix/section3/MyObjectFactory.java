package com.mooncake.springsix.section3;

import org.springframework.context.annotation.Configuration;


// V8 에서의 변경
// 구성 정보를 확인하라고 전달하고, ObjectFactory 에게 @Configuration 임을 말해준다
// 코드를 바꾸지 않고, Spring 에게 "이 클래스는 Bean 을 구성하는 클래스야" 라고 전달한다
@Configuration
public class MyObjectFactory {

    public e_PaymentServiceV6 paymentService() {
        return new e_PaymentServiceV6(exRateProvider());
    }

    public c_ExRateProvider exRateProvider() {
//        return new LastWebApiExRateProvider(); // 현재 이걸 사용하도록 여기서 등록한다
        return new c_SimpleExRateProvider();
    }
}
