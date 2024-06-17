package com.ohgiraffers.restapi.section02.reponseEntity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO {

    private int no;
    private String id;
    private String pwd;
    private String name;
    private LocalDateTime enrollDate;
}
