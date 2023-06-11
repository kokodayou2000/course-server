package com.example.courseserver.entity.vo;

import com.example.courseserver.entity.Doc;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Data
public class UserVO {
    private String name;
    private String gender;
    private String phone;
    private Date lastLogin;
    private List<Doc> docs;
    private String token;
}
