package com.mooncake.springsix.section3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooncake.springsix.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/*
 Web API 를 활용해서 환율 정보를 가져온다
 */
public class b_WebApiExRateProvider {
    public BigDecimal getWebExRate(String currency) throws IOException {

        URL url = new URL("https://open.er-api.com/v6/latest/" + currency); // 외부 API 활용
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String resp = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData recordExRates = mapper.readValue(resp, ExRateData.class);
        BigDecimal exRate = recordExRates.rates().get("KRW");

        return exRate;
    }

}
