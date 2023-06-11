package com.example.courseserver.controller;

import com.example.courseserver.domain.AjaxResult;
import com.example.courseserver.entity.vo.InsertVo;
import com.example.courseserver.entity.vo.LoginVO;
import com.example.courseserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    IUserService userService;


    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVO loginVO){
        return userService.login(loginVO.getPhone(),loginVO.getPwd());
    }


}
