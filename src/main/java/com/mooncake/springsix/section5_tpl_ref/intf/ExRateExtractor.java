package com.mooncake.springsix.section5_tpl_ref.intf;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal extract(String source) throws JsonProcessingException;
}
