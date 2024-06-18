package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Schema(description = "회원정보 관련 DTO")
public class UserDTO {

    @Schema(description = "회원번호 (PK)")
    private int no;
    @Schema(description = "회원 아이디")
    private String id;
    @Schema(description = "회원 비밀번호")
    private String pwd;
    @Schema(description = "회원 이름", example = "김윤재")
    private String name;
    @Schema(description = "회원 등록 일시")
    private LocalDate enrollDate;
}
