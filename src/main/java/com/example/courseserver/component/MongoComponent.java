package com.example.courseserver.component;

import com.example.courseserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoComponent {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> retrieveDoc(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("deng"));
        System.out.println("start -- ");
        List<User> docs = mongoTemplate.find(query, User.class, "info");
        for (User doc : docs) {
            System.out.println("doc "+doc);
        }
        return docs;
    }

    /**
     * 判断是否存在
     * @return
     */
    public Boolean ifExist(String key,String val){
        Query query = new Query();
        query.addCriteria(Criteria.where(key).regex(val));
//        query.addCriteria(Criteria.where(key).gt(val));
        List<User> info = mongoTemplate.find(query, User.class, "info");
        if (info.size() == 0){
            return false;
        }
        return true;
    }


}
