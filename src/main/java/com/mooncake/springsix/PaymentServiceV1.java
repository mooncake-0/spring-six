package com.mooncake.springsix;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentServiceV1 {

    /*
     prepare 함수는 Prepare 를 준비한다 (해외 제품 주문하는 상황)
     @ orderId - 주문번호
     @ currency - 제품의 통화 - 보통 "USD" 사용
     @ foreignCurrencyAmount - 해당 통화로 얼마인지
     - returns : Payment - 외부 환율을 통해 KRW 으로 얼마인지, 언제까지 유효한지에 대한 Payment 객체 반환
     */
    public Payment prepareV1(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        // 1- 제품에 대한 정보는 Client 로부터 전달되어야 한다
        // 2- 환율에 대한 정보는 어딘가에서 가져와야 한다
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency); // 외부 API 활용
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Byte 를 Char 로 변경하기 위한 IO Reader 적용
        // 사람이 읽기 편하게 가져오기 위한 BufferedIO Reader 적용 (한줄 한줄) - 실무에서 꽤 많이 쓴다고 함
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resp = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();

        // 신기한 문법의 record 확인!
        ExRateData recordExRates = mapper.readValue(resp, ExRateData.class);
        BigDecimal exRate = recordExRates.rates().get("KRW"); // 원화 대비 달러 환율을 가져온다

        // 실제 결제할 금액을 계산하는 곳
        BigDecimal wonConvertedAmount = foreignCurrencyAmount.multiply(exRate); // 달러로된 금액을 원화로 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30); // 해당 환율 적용은 30분동안 유효하다

        // - 금액과 유효시간을 을 logic 으로 직접 계산해줘야 한다
        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, wonConvertedAmount, validUntil);
    }

    public static void main(String[] args) throws IOException {
        PaymentServiceV1 service = new PaymentServiceV1();
        Payment payment = service.prepareV1(100L, "USD", BigDecimal.valueOf(50.7)); // 50.7 USD 짜리 제품에 대한 구매 처리 중

        System.out.println(payment);
    }

}