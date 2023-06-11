package com.example.courseserver.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document("docs")
public class Doc {

    @Indexed(unique = true)
    @Field("_id")
    private String id;
    @Field("title")
    private String title;
    @Field("content")
    private String content;
    @Field("writer_id")
    private String writerId;
    @Field("lastEditTime")
    private Date lastEditTime;
    @Field("isDelete")
    private Boolean isDelete;

}
