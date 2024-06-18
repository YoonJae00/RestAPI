package com.ohgiraffers.test01postmantest.menu;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseInfo {

    private String status;

    private String message;

    private Map<String,Object> info;
    
}
