package com.example.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(Integer userId) {
        super(406,"用户'" + userId +"'不存在");
    }
}
