package com.mooncake.springsix.section5_tpl_ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooncake.springsix.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.*;
import java.util.stream.Collectors;


// WebApiExRateProvider 를 리펙토링한다
// * 리펙토링 -> 코드의 결과 변경 없이 구조를 바꾸는 작업을 말한다
// * 기본은 항상 함수 추출이다
public class WebApiExRateProviderV2 implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        // *************** 1- URI를 준비하고 예외처리를 위한 작업 ********************
        // 변경 - IOException 이 interface 에 누수됨. 해결 책임이 있는 곳에서 닫히도록 변경
        //     - URL 은 deprecated 되어 URI 로 변경
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // *************** 2 - API 실행 및 서버로 부터 받은 응답 처리 ********************
        String resp;
        try {

            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            // 자바 규칙이자 BufferedReader 의 AutoClosable 인터페이스
            // - try 블록을 나갈때 자동으로 close 함수를 호출해주도록 이렇게 짤 수 있다고 한다 (finally 쓰면 너무 길어짐)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                resp = br.lines().collect(Collectors.joining());
            }

        } catch (IOException e) { // 언체크 예외로 변경 (강사는 URI 만들거나, IO에 대한 예외가 발생하거나로 나눠서 함)
            throw new RuntimeException(e);
        }


        // *************** 3 - JSON 문자열을 파싱하고 필요한 환율정보를 추출 ********************
        ObjectMapper mapper = new ObjectMapper();

        try {
            ExRateData exRateData = mapper.readValue(resp, ExRateData.class);
            return exRateData.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
