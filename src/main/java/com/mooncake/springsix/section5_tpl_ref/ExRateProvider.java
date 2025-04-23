package com.mooncake.springsix.section5_tpl_ref;

import com.mooncake.springsix.section3.last_versions.LastExRateProvider;

import java.math.BigDecimal;

// 예외를 누수를 제거한 ExRateProvider (Last 에서 한단계 더 발전)
// Section 5 에서 리펙토링 하기 위해 사용된다
public interface ExRateProvider extends LastExRateProvider {
    BigDecimal getExRate(String currency);
}
