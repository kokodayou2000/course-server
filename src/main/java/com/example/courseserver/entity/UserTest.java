package com.example.courseserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserTest {

    private Integer id;

    private String username;

    private String phone;

    private String sex;

    private String nickname;

    private Date lastModified;
}
