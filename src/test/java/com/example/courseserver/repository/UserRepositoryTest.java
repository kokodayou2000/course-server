package com.example.courseserver.repository;

import com.alibaba.fastjson.JSON;
import com.example.courseserver.entity.Doc;
import com.example.courseserver.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.AutoConfigureDataNeo4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    /**
     * data
     */

    static Doc docOne = new Doc();
    static Doc docTwo = new Doc();
    static Doc docThree = new Doc();
    static List<Doc> docs = new ArrayList<>();
    static {
        docOne.setTitle("oneDoc");
        docOne.setContent("H1 oneContent");
        docOne.setLastEditTime(new Date());
        docs.add(docOne);

        docTwo.setTitle("twoDoc");
        docTwo.setContent("H1 twoContent");
        docTwo.setLastEditTime(new Date());
        docs.add(docTwo);

        docThree.setTitle("threeDoc");
        docThree.setContent("H1 threeDoc");
        docThree.setLastEditTime(new Date());
        docs.add(docThree);


    }
    static User one = User.builder()
            .id("1")
            .gender("one")
            .pwd("one")
            .name("one")
            .phone("one")
            .lastLogin(new Date())
            .docs(docs)
            .build();
    static User two = User.builder()
            .gender("two")
            .pwd("two")
            .name("two")
            .phone("two")
            .lastLogin(new Date())
            .docs(docs)
            .build();


    /**
     * 增加一个实例
     */
    @Test
    public void insertOne(){
        User res = userRepository.insert(one);
        log.info(res.toString());
    }

    /**
     * 增加多个实例
     */
    @Test
    public void insertMany(){
        ArrayList<User> users = new ArrayList<>();
        users.add(one);
        users.add(two);
        List<User> list = userRepository.insert(users);
        log.info(list.toString());
    }

    /**
     * 增加自定义id
     */
    @Test
    public void insertDefineId(){
        one.setName("insertById");
        User insert = userRepository.insert(one);
        log.info(insert.toString());
    }

    /**
     * 根据id删除
     */
    @Test
    public void deleteById(){
        userRepository.deleteById("aaaabbbbcccc");
    }

    /**
     * 清除集合
     */
    @Test
    public void deleteALl(){
        userRepository.deleteAll();
    }

    /**
     * 删除指定实例
     * 但是 要指定id
     */
    @Test
    public void deleteByEntry(){
        one.setId("aaaabbbbcccc");
        userRepository.delete(one);
    }

    /**
     * 修改指定属性
     * save 如果找不到就新增
     */
    @Test
    public void saveById(){
//        ArrayList<Doc> list = new ArrayList<>();
//        docOne.setContent("## saveById");
//        list.add(docOne);
//        one.setDocs(list);
        one.setName("sav");
        one.setId("2");
        userRepository.save(one);
    }


    /**
     * 查找全部
     */
    @Test
    public void findAll(){
        userRepository.findAll();
    }

    /**
     *
     */
    @Test
    public void find(){

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
//                .withIgnorePaths("lastLogin")
                .withIgnoreCase("phone")
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::endsWith);

        User u = User.builder()
                .name("save").build();

        Example<User> example = Example.of(u, matcher);
        List<User> all = userRepository.findAll(example);
        log.info(JSON.toJSONString(all));
    }

    @Test
    public void findALlByOnlyDocs(){
        List<User> allByOnlyDocs = userRepository.findAllByOnlyDocs();
        log.info(JSON.toJSONString(allByOnlyDocs));
    }



}