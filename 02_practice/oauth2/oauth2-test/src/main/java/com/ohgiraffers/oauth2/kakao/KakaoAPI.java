package com.ohgiraffers.oauth2.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoAPI {

    @Value("${kakao.api_key}")
    private String kakaoApiKey;


}
