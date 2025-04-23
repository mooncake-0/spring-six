package com.mooncake.springsix.section3.last_versions;

import java.io.IOException;
import java.math.BigDecimal;

// Section3 의 결과로 최종본들을 모아놓은 last 클래스들
public class LastSimpleExRateProvider implements LastExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if(currency.equals("USD")) return BigDecimal.valueOf(1000);
        throw new IllegalArgumentException("지원되지 않는 통화입니다");
    }
}
