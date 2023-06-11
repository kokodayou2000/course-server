package com.example.courseserver;

import com.alibaba.fastjson.JSON;
import com.example.courseserver.entity.User;
import com.example.courseserver.entity.UserTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
@SpringBootTest
public class MongoTemplateTest {
    @Autowired
    MongoTemplate mongoTemplate;



    private static final String COLLECTION_NAME = "user";

    @Test
    public void getCollection() {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        log.info(JSON.toJSONString(collectionNames));
    }


    /**
     * 创建集合
     */
    @Test
    public void createCollection() {
        boolean exists = mongoTemplate.collectionExists(COLLECTION_NAME);
        if (exists) {
            log.info("集合已经存在");
            return;
        }
        CollectionOptions collectionOptions = CollectionOptions.empty()
                .size(6142800) // 为固定集合指定一个最大值，即字节数
                .maxDocuments(10000) // 指定固定集合中包含文档的最大数量。
                //.capped() 如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。
                .collation(Collation.of(Locale.CHINA.getLanguage()));// 定制中文排序规则
        mongoTemplate.createCollection(COLLECTION_NAME, collectionOptions);
    }

    /**
     * 插入文档
     */
    @Test
    public void insert() {
        UserTest user = new UserTest();
        user.setId(1);
        user.setUsername("张三");
        user.setPhone("131000281912");
        user.setNickname("小三");
        user.setSex("男");
        user.setLastModified(new Date());
        mongoTemplate.insert(user,COLLECTION_NAME);

        UserTest user1 = new UserTest();
        user1.setId(2);
        user1.setUsername("李四");
        user1.setPhone("132000281912");
        user1.setNickname("小四");
        user1.setSex("男");
        user1.setLastModified(new Date());
        mongoTemplate.insert(user1,COLLECTION_NAME);
    }

    static class T{

        public Integer _id;

        public String username;

        public String phone;

        public String sex;

        public String nickname;
        public List docs;
        public Date lastModified;
    }

    /**
     * 查询文档
     */
    @Test
    public void findAll() {
        List all = mongoTemplate.findAll(T.class, "userTest");

        log.info(JSON.toJSONString(all));
    }

    /**
     * 分页条件查询文档
     */
    @Test
    public void findByCondition() {
        // 条件 跟sql写法类似
        Query query = new Query(Criteria
                .where("phone").is("131000281912")
                .and("id").gte(1)
                .and("username").in("张三", "李四"));

        // 排序
        query.with(Sort.by(Sort.Order.asc("username")));

        // 分页
        Pageable pageable = PageRequest.of(0, 10);
        query.with(pageable);

        List<UserTest> users = mongoTemplate.find(query, UserTest.class, COLLECTION_NAME);
        long count = mongoTemplate.count(query, User.class);

        Page<UserTest> page = PageableExecutionUtils.getPage(users, pageable, () -> count);
        log.info(JSON.toJSONString(page));
    }

    @Test
    public void getCount() {
        Query query = new Query(Criteria
                .where("phone").is("131000281912")
                .and("id").gte(1)
                .and("username").in("张三", "李四"));

        long count = mongoTemplate.count(query, UserTest.class, COLLECTION_NAME);
        log.info(JSON.toJSONString(count));
    }


    /**
     * 更新文档
     */
    @Test
    public void update() {
        UserTest user = new UserTest();
        user.setId(2);
        user.setUsername("李四");
        user.setPhone("131000281922");
        user.setNickname("小四");
        user.setSex("女");
        user.setLastModified(new Date());
        mongoTemplate.save(user, COLLECTION_NAME);
    }

    /**
     * 删除文档
     */
    @Test
    public void deleteById() {
        mongoTemplate.remove(new Query(), UserTest.class, "userTest");
    }

    /**
     * 聚合
     */
    @Test
    public void aggregate() {
        // count
        Aggregation countAggregation = Aggregation.newAggregation(
                count().as("count")
        );

        AggregationResults countAggregate = mongoTemplate.aggregate(countAggregation, UserTest.class, HashMap.class);
        log.info(JSON.toJSONString(countAggregate));

        // sum
        Aggregation sumAggregation = Aggregation.newAggregation(
                match(Criteria.where("sex").in("男", "女")),
                group().max("id").as("sum")
        );

        AggregationResults sumAggregate = mongoTemplate.aggregate(sumAggregation, UserTest.class, HashMap.class);
        log.info(JSON.toJSONString(sumAggregate));

        // max
        Aggregation maxAggregation = Aggregation.newAggregation(
                group().max("id").as("max")
        );

        AggregationResults maxAggregate = mongoTemplate.aggregate(maxAggregation, UserTest.class, HashMap.class);
        log.info(JSON.toJSONString(maxAggregate));
    }



}
