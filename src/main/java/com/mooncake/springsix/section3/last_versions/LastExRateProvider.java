package com.mooncake.springsix.section3.last_versions;

import java.io.IOException;
import java.math.BigDecimal;

public interface LastExRateProvider {
    BigDecimal getExRate(String currency) throws IOException;

}
