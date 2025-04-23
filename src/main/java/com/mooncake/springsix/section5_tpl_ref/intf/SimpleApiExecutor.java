package com.mooncake.springsix.section5_tpl_ref.intf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

// 기존 WebApiExRateProvider 에 있던 API 호출부를 간단하게 구현
public class SimpleApiExecutor implements ApiExecutor {
    @Override
    public String execute(URI uri) throws IOException {

        String resp;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        // 자바 규칙이자 BufferedReader 의 AutoClosable 인터페이스
        // - try 블록을 나갈때 자동으로 close 함수를 호출해주도록 이렇게 짤 수 있다고 한다 (finally 쓰면 너무 길어짐)
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            resp = br.lines().collect(Collectors.joining());
        }
        return resp;
    }
}
