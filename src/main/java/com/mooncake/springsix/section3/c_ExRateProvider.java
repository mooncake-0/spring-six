package com.mooncake.springsix.section3;

import java.io.IOException;
import java.math.BigDecimal;

public interface c_ExRateProvider {
    BigDecimal getExRate(String currency) throws IOException;

}
