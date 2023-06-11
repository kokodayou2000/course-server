package com.example.courseserver.service;

import com.example.courseserver.domain.AjaxResult;


public interface IUserService {
    /**
     * 登录
     * @param phone 手机号
     * @param pwd 密码
     * @return 用户信息
     */
    AjaxResult login(String phone, String pwd);



}
