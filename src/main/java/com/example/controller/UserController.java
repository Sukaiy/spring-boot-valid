package com.example.controller;

import com.example.exception.BusinessException;
import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.model.UserForm;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "用户", description = "用户")
@RequestMapping("/user")
@RestController
public class UserController {

    @ApiOperation(value = "登录", notes = "输入用户名和密码登录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class, responseContainer = "user"),
            @ApiResponse(code = 405, message = "用户名或者密码不存在")
    })
    @RequestMapping(value = "/login", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<User> login(@Valid @RequestBody UserForm form) {
        if (!form.getPassword().equalsIgnoreCase("000000")) {
            //使用默认的业务异常类，需要每次都填入消息
            throw new BusinessException(405,"用户名或者密码不存在");
//            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        User user = new User();
        user.setId(1L);
        user.setUsername(form.getUsername());
        user.setFirstName("小");
        user.setLastName("明");
        user.setEmail("xiaoming@mail.com");
        user.setUserStatus(1);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "OK",response = User.class,responseContainer = "user"),
            @ApiResponse(code = 406,message = "用户不存在")
    })
    @GetMapping(value = "/info/{userId}")
    public User getUserInfo(
            @ApiParam(name = "userId",value = "用户编码",type = "path",defaultValue = "1000")
            @PathVariable("userId") Integer userId){
        if(userId != 1000){
            //自定义异常类可以出入需要的参数然后在异常处理里面做统一的处理
            throw new UserNotFoundException(userId);
        }
        User user = new User();
        user.setId(1000L);
        user.setUsername("1000");
        user.setFirstName("小");
        user.setLastName("明");
        user.setEmail("xiaoming@mail.com");
        user.setUserStatus(1);
        return user;
    }
}
