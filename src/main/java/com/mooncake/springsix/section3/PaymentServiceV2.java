package com.mooncake.springsix.section3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooncake.springsix.ExRateData;
import com.mooncake.springsix.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentServiceV2 {

    /*
       코드를 리팩토링한다 (주석 또한 리펙토링 된 모습을 확인)
       - 주석은 기준이 있어야 함: 주석이 있어야 코드 이해에 도움이 된다 - 필요
                            주석이 없어도 코드 이해가 충분히 된다 -> 혹시 몰라 주석을 달기도 하는데, 이런 경우는 주석 없어야 함 - 불필요
                            코드가 별론데 주석을 통해 이해를 시킨다 - 리펙토링 필요
     */

    /*
     기존 V1 의 문제점
     > Java 기술적인 내용과 비즈니스 로직 수행 부분이 너무 섞여 있음
     > 기술적, 비즈니스적 내용이 언제든 변경될 수 있다고 보는게 SoC 의 시작 -> 하지만 둘의 변경 시점이 다름!! 이 부분들을 같이 두는건 좋지 않음
     > prepareV2 는 "method 추출" Refactoring 기법을 이용해서 코드를 개선한다
     > prepareV2 같은 경우 비즈니스 로직이 변경될 경우에는 본 함수만 바뀌고, 환율을 가져오는 부분이 바뀔 때는 getExRate 가 바뀐다
     */
    public Payment prepareV2(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        BigDecimal exRate = getExRate(currency);  // "환율 가져오기 주석 삭제" - getExRate 라고 적혀있다

        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }


    private BigDecimal getExRate(String currency) throws IOException {

        URL url = new URL("https://open.er-api.com/v6/latest/" + currency); // 외부 API 활용
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resp = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData recordExRates = mapper.readValue(resp, ExRateData.class);
        BigDecimal exRate = recordExRates.rates().get("KRW");

        return exRate;
    }


    public static void main(String[] args) throws IOException {
        PaymentServiceV2 service = new PaymentServiceV2();
        Payment payment = service.prepareV2(100L, "USD", BigDecimal.valueOf(50.7)); // 50.7 USD 짜리 제품에 대한 구매 처리 중

        System.out.println(payment);
    }

}