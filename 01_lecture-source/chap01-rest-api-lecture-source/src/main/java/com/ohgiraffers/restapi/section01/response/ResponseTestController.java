package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // => Controller + ResponseBody
@RequestMapping("/response")
public class ResponseTestController {

//     응답 test
    @GetMapping("/hello")
    public Map<String,Object> helloWorld() {

        Map<String,Object> hello = new HashMap<>();
        hello.put("message", "Hello World");
        hello.put("code", 200);
        hello.put("data", "Hello World");

        return hello;
    }


    // 기본 자료형 test
    @GetMapping("/random")
    public int getRandomNumber() {

        return (int) ((Math.random() * 10) + 1);
    }

    @GetMapping("/dto")
    public ResDTO responseDto() {
        ResDTO resDTO = new ResDTO(1, "Hello World");

        return resDTO;
    }

    // Object 타입 응답
    @GetMapping("/message")
    public Message getMessage() {

        return new Message(200,"정상 응답 완료!!!");
    }

    // List 타입 응답
    @GetMapping("/list")
    public List<String> getList() {
        return List.of("Hello World", "Hello World 2", "Hello World 3", "Hello World 4", "Hello World 5");
    }

    @GetMapping("/map")
    public Map<Integer, Object> getMap() {

        Map<Integer, Object> map = new HashMap<>();
        map.put(200,"정상 응답 완료 !!");
        map.put(404," 에러 !!");
        map.put(500,"서버 오류!");
        map.put(123123, new Message(1,"Hello World"));

        return map;
    }

    // image response
    // produces 설정을 해주지 않으면 이미지가 텍스트 형태로 전송된다.
    // produces 는 response header 의 content-type 에 대한 설정이다.
    @GetMapping(value = "/image", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getImage() throws IOException {

        return getClass().getResourceAsStream("/images/jjgleft.gif").readAllBytes();
    }

    // ResponseEntity 를 이용한 응답
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity() {


        // ok 200
        // create 201
        // not found 404 등등
        return ResponseEntity.ok(new Message(200,"Hello World"));
//        return ResponseEntity.notFound().build();
    }
}
