package com.mooncake.springsix;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private Long orderId; // 혼자할 때만 맘대로 하고, 협업할 때는 자바 OOP 에 충실해라
    private String currency; // 통화 (결제 금액의 기준이 되는 통화)
    private BigDecimal foreignCurrencyAmount; // currency 기준 얼마인지 // 소숫점 틀리면 안되는 경우 (ex: 돈관련) Double 쓰면 안됨
    private BigDecimal exRate; // 적용 환율
    private BigDecimal convertedAmount; // 환율이 적용된 KRW 가격
    private LocalDateTime validUntil; // 해당 환율로 유효한 시간을 정의

    public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}