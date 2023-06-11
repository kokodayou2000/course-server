package com.example.courseserver.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document("user")
@Data
@Builder
public class User implements Serializable {

    @Indexed(unique = true)
    @Field("_id")
    private String id;
    @Field("name")
    private String name;
    @Field("gender")
    private String gender;
    @Field("phone")
    private String phone;
    @Field("pwd")
    private String pwd;
    @Field("lastLogin")
    private Date lastLogin;
    @Field("docs")
    private List<Doc> docs;
}
