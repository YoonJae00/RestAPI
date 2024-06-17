package com.ohgiraffers.restapi.section02.reponseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEntityController {

    /* 필기.
        ResponseEntity 란?
         결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
         HttpStatus, HttpHeaders, HttpBody 를 포함하고 있다.
     */

    private List<UserDTO> users;

    public ResponseEntityController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01","너구리", LocalDateTime.now()));
        users.add(new UserDTO(2,"user02","pass02","메롱", LocalDateTime.now()));
        users.add(new UserDTO(3,"user03","pass03","테스트", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);
        ResponseMessage message = new ResponseMessage(200, "조회성공", responseMap);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByUserNo(@PathVariable("userNo") int userNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<UserDTO> matchingUsers = users.stream()
                .filter(user -> user.getNo() == userNo)
                .collect(Collectors.toList());

        if (matchingUsers.isEmpty()) {
            ResponseMessage errorMessage = new ResponseMessage(404, "User not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(headers)
                    .body(errorMessage);
        } else {
            UserDTO foundUser = matchingUsers.get(0);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("user", foundUser);

            ResponseMessage responseMessage = new ResponseMessage(200, "조회성공", responseMap);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseMessage);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDTO newUser) {

        System.out.println("new user 들어오니?");
        int lastUserNo = users.get(users.size() -1).getNo();
        newUser.setNo(lastUserNo + 1);
        users.add(newUser);

        return ResponseEntity
                // 201 상태코드 -> 등록 관련 (생성)
                .created(URI.create("/entity/users/" + users.get(users.size() -1).getNo()) ).build();
    }

    // 수정
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDTO modifyInfo) {
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        System.out.println("foundUser = " + foundUser);
        return ResponseEntity.created(URI.create("/entity/users/" + userNo)).build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> deleteUser(@PathVariable int userNo) {
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }
}
