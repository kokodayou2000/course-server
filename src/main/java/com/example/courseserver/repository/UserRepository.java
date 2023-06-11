package com.example.courseserver.repository;

import com.example.courseserver.entity.Doc;
import com.example.courseserver.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    @Query(value = "{}",fields = "{'docs': 1}")
    List<User> findAllByOnlyDocs();



}

