package com.mooncake.springsix.section4;

import com.mooncake.springsix.Payment;
import com.mooncake.springsix.section3.e_PaymentServiceV6;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

// 테스트 대상이 다른 오브젝트들과 많이 협력을 해서 일을 해야 되는 경우 (정확히 뭔지 모르겠음)
// 이런 경우 Spring DI 를 사용 (Spring 의 Container 를 사용)
// TestObjectFactory 를 통해 원하는 상황을 Setting 해서 Inject 해줄 수 있다
// DB 연동, 복잡한 작업 등등의 상황에서는 이런 원하는 Container 의 모습대로 Set 해준대로 Test 하는게 매우 편하기도 하다
//@ExtendWith(SpringExtension.class) // Test 에서 ContextConfiguration 쓸 경우 필수. 외우셈
//@ContextConfiguration(classes = TestObjectFactory.class)
public class PaymentServiceSpringTest {

    // @ContextConfiguration 으로 Container 구성정보를 구성해줬을 경우, Autowired 로 그냥 불러와줄 수 있다
//    @Autowired
//    private BeanFactory beanFactory;

//    @Autowired
//    private PaymentService pService; // 일반적으론 beanFactory 를 굳이 autowire 하지 않고 원하는 대상 class 를 가져온다

    @Test
    void testTesttest() throws Exception {

        // beanFactory 를 위에 annotation 들을 사용하면 안만들어도 된다
        // 아니면 바로 paymentService 를 꺼내와도 된다
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        e_PaymentServiceV6 pService = beanFactory.getBean(e_PaymentServiceV6.class);

        Payment payment = pService.prepareV6(1L, "USD", BigDecimal.TEN);

        // 항상 하던 test 들
        Assertions.assertThat(payment.getExRate()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(1000));
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now()); // 현재 시간 이후긴 하다.. 정도로


    }
}