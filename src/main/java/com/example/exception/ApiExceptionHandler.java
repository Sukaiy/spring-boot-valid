package com.example.exception;

import com.example.model.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

/**
 * Api异常处理
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    // 对表单验证时抛出的 MethodArgumentNotValidException 异常做统一处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        if (!errors.isEmpty()) {
            // 只显示第一个错误信息
            ErrorBody body = new ErrorBody(HttpStatus.BAD_REQUEST.value(), errors.get(0).getDefaultMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("valid error", HttpStatus.BAD_REQUEST);
    }

    // 业务异常的默认处理方式
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessException(BusinessException e) {
        ErrorBody body = new ErrorBody(e.getCode(), e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 代码异常的处理方式
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> defaultHandler(Exception e) {
        ErrorBody body = new ErrorBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
