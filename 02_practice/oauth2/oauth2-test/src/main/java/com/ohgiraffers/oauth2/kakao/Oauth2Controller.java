package com.ohgiraffers.oauth2.kakao;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class Oauth2Controller {

// https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}

    @Autowired
    private static KakaoAPI kakaoAPI;

    @GetMapping("/login/oauth2/kakao")
//    @ResponseBody
    public String kakaoLogin(@Value("${kakao.api_key}") String apikey, Model model,
                             @RequestParam String code, HttpServletRequest request) {
        String accessToken = kakaoAPI.getAccessToken(code);

        Map<String, Object> userInfo = kakaoAPI.getUserInfo(accessToken);

        model.addAttribute("apikey", apikey);

        return "redirect:/result";
    }

    @GetMapping("/result")
    public String kakaoLogin(Model model,
                             HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();


        System.out.println("parameters = " + parameters);
        return "main/result";
    }

    @PostMapping("/login/oauth2/kakao")
    @ResponseBody
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String,Object> map) {
        System.out.println("map = " + map);
//        System.out.println("request = " + request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8")));
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/login/oauth2/kakao")
//    public String kakaoLogin(String code) {
//
//        System.out.println("request = " + request);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8")));
//        return null;
//    }
}
