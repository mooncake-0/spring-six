package com.mooncake.springsix.section5_tpl_ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooncake.springsix.ExRateData;
import com.mooncake.springsix.section5_tpl_ref.intf.ApiExecutor;
import com.mooncake.springsix.section5_tpl_ref.intf.ExRateExtractor;
import com.mooncake.springsix.section5_tpl_ref.intf.SimpleApiExecutor;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;


// ApiExecutor 역할체를 도입
// V3는 함수 추출한거라 V2랑 차이가 없음
// Client 는 가만히 있으면서 동적으로 구현체를 바꿀 수 있도록 OCP 를 지켜보면서 재사용성을 보장해보자
public class WebApiExRateProviderV4 implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        // 파라미터로 행위를 전달한다 -> Callback 패턴
        // Client 가 직접 수행할 행위 (콜백)를 정의하는 것 또한 특이점
        // 함수 자체를 람다로 전달하는 것도 가능하다 (익명 클래스 전달을 람다로 치환)
        return runApiForExRate(url, new SimpleApiExecutor(),
                resp -> {
                    ObjectMapper om = new ObjectMapper();
                    ExRateData data = om.readValue(resp, ExRateData.class);
                    return data.rates().get("KRW");
                });

        // 익명 클래스로도 가능
        // 구현하고 있는 메소드가 하나일 경우 Lambda 로 변경 가능한 것!
        // 콜백은 람다와 매우 잘 맞는다고 한다!
//        return runApiForExRate(url, new ApiExecutor() {
//            @Override
//            public String execute(URI uri) throws IOException {
//                return "ANONYMOUS_CLASS";
//            }
//        });
    }


    private static BigDecimal runApiForExRate(String url, ApiExecutor executor, ExRateExtractor extractor) {

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
            resp = executor.execute(uri);
        } catch (IOException e) { // 언체크 예외로 변경 (강사는 URI 만들거나, IO에 대한 예외가 발생하거나로 나눠서 함)
            throw new RuntimeException(e);
        }


        // *************** 3 - JSON 문자열을 파싱하고 필요한 환율정보를 추출 ********************
        try {
            return extractor.extract(resp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
