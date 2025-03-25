package com.mooncake.springsix.section4;

import com.mooncake.springsix.section3.c_ExRateProvider;
import com.mooncake.springsix.section3.e_PaymentServiceV6;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/*
 이 Configuration 상황대로 Test 할 곳에게 Spring Container 등록을 해주는 방식임을 이해
 */
@Configuration
public class TestObjectFactory {

    @Bean
    public e_PaymentServiceV6 paymentService() {
        return new e_PaymentServiceV6(exRateProvider());
    }

    @Bean
    public c_ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(100));
    }

}
