package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        String code = "ERROR_CODE_0001";
        String description = "회원 정보 조회에 실패하셨습니다.";
        String detail = ex.getMessage();

        return new ResponseEntity<>(new ErrorResponse(code,description,detail),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidationException(MethodArgumentNotValidException ex) {
        String code = "";
        String description = "";
        String detail = "";

        // 유효성 검사에서 error 가 발생한다면?
        if(ex.getBindingResult().hasErrors()) {
            detail = ex.getBindingResult().getFieldError().getDefaultMessage(); // ex.getMessage() 와 동일한 의미
            System.out.println("detail = " + detail);

            // NotNull, Size, NotBlank ...
            String bindResultCode = ex.getBindingResult().getFieldError().getCode();
            System.out.println("bindResultCode = " + bindResultCode);
            switch (bindResultCode) {
                case "NotBlank" :
                    code = "ERROR_CODE_0002";
                    description = "필수 값이 누락되었습니다!!";
                    break;
                case "Size" :
                    code = "ERROR_CODE_0003";
                    description = "글자 수는 2 이상이어야 합니다.";
            }
        }
        return new ResponseEntity<>(new ErrorResponse(code,description,detail),HttpStatus.BAD_REQUEST);
    }
}
