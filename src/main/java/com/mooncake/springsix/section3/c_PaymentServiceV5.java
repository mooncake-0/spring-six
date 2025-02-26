package com.mooncake.springsix.section3;

import com.mooncake.springsix.Payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class c_PaymentServiceV5 {

    /*
    V5 는 Interface 를 사용한다
    > Interface 를 통해 의존 역할체와 사용 메서드를 고정시킬 수 있다
    > 구체적인 클래스를 넣어주는 부분은 어쨌든 둘 수밖에 없음
    > 디자인 패턴은 이 형태가 거의 70~80% 이다
     */

    /*
     Interface 는 코드 수정을 최소화할 수 있는 좋은 수단이지만, 현재 V3 만도 못하다는걸 알아야 함
     > 외부에서 AwsExRateProvider 를 만들었다고 하자
     > 그럼 PaymentService 단에서 의존성을 직접 변경해줘야 하기 때문에, 핵심 로직인 prepare() 함수가 노출되게 된다
     > 코드간 결합도가 높은 상황인 V5
     >> "어쨋든 ExRateProvider 를 어떤 것으로 쓸지는 어디선가에서는 말해줘야 한다" 는 점을 이해
     >> (***) 이 부분을 강사는 "관계 설정 책임" 이라고 정의한다

     > (***) "관계 설정 책임"을 Client 에게 맡긴다면 문제가 해결된다 (Spring 의 핵심)
     */
    private final c_ExRateProvider exRateProvider;

    public c_PaymentServiceV5() {
        this.exRateProvider = new c_SimpleExRateProvider(); // 구현체만 바꾸면 기능을 바꿀 수 있다
    }

    public Payment prepareV5(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        // 타 클래스의 기능을 사용한다
        BigDecimal exRate = exRateProvider.getExRate(currency);  // "환율 가져오기 주석 삭제" - getExRate 라고 적혀있다

        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }
}