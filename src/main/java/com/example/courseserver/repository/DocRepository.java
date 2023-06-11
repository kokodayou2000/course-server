package com.example.courseserver.repository;

import com.example.courseserver.entity.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocRepository extends MongoRepository<Doc,String> {

    List<Doc> findAllByWriterId(String id);

}
