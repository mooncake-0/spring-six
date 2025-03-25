package com.mooncake.springsix.section4;

import com.mooncake.springsix.Payment;
import com.mooncake.springsix.section3.c_WebApiExRateProvider;
import com.mooncake.springsix.section3.e_PaymentServiceV6;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class PaymentServiceTest {// 과연 이딴 Test 가지고 PaymentService 가 잘 테스트 되었다! 라고 말할 수 있을까??

    @Test
    void prepareTestV1() throws Exception {

        e_PaymentServiceV6 paymentService = new e_PaymentServiceV6(new c_WebApiExRateProvider());
        Payment payment = paymentService.prepareV6(1L, "USD", BigDecimal.TEN);

        // 우리가 해야 하는 Test 를 먼저 정의해본다
        // 환율 정보를 가져온다
//        Assertions.assertThat(payment.getExRate()).isEqualTo(); // 잘 넣어졌으면 됐는데, 그 값은 알 방법이 없음, 따라서 isEqualTo 는 불가
        assertThat(payment.getExRate()).isNotNull();

        // 원화 환산금액 계산 - 정확한 값을 계산해 볼 수 있다
        assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        // 원화환산금액의 유효시간 계산
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now()); // 현재 시간 이후긴 하다.. 정도로

    }

    // 이제 Stub 을 배우고 왔음 (md 참조)
    // Service 입장에선 ExRateProvider 가 어떻게 환율을 가져오는지 알바가 아니기 때문에 가능 -> PSA
    // 이제 인터넷이 안되거나 외부 환율 제공 서버가 중단되었어도 Test 는 신경쓰지 않고 PaymentService 가 수행하는 일을 test 할 수 있다
    // 제일 처음에 V1 처럼 모든 기능을 한 함수에 몰빵 한 함수에서는 이런 Test 가 나올 수 없다는 점도 느껴야 한다 (Test 가 어려우면 본 로직이 이상한것)
    @Test
    void prepareTestV2() throws Exception {

        e_PaymentServiceV6 paymentService = new e_PaymentServiceV6(new ExRateProviderStub(BigDecimal.valueOf(500)));

        Payment payment = paymentService.prepareV6(1L, "USD", BigDecimal.TEN);

        // 환율 정보를 가져온다
        assertThat(payment.getExRate()).isEqualTo(BigDecimal.valueOf(500));

        // 원화 환산금액 계산 - 정확한 값을 계산해 볼 수 있다
        // 혹시 의심될 수 있으니, getPayment 라는 static 함수를 만들어서 넣은 값 -> 나온 값을 더 Test 해봐도 된다 (난 안함)
        // 팁 **  참고로 BigDecimal 은 일반 equal 이 아니라 compareTo 를 사용해야 한다. 따라서 isEqualByComparingTo 라는 Assertions 에서 제공해주는걸 쓰면 됨
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5_000));

        // 원화환산금액의 유효시간 계산
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now()); // 현재 시간 이후긴 하다.. 정도로

    }
}
