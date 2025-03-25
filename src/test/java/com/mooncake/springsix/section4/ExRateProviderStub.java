package com.mooncake.springsix.section4;

import com.mooncake.springsix.section3.c_ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class ExRateProviderStub implements c_ExRateProvider {
    private BigDecimal exRate;

    public BigDecimal getExRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
    }

    public ExRateProviderStub(BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExRate(String currency) {

        return exRate;

    }
}