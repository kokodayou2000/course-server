package com.example.courseserver.service.impl;

import com.example.courseserver.common.constant.HttpStatus;
import com.example.courseserver.common.page.TableDataInfo;
import com.example.courseserver.domain.AjaxResult;
import com.example.courseserver.entity.Doc;
import com.example.courseserver.entity.User;
import com.example.courseserver.entity.vo.UserVO;
import com.example.courseserver.repository.DocRepository;
import com.example.courseserver.repository.UserRepository;
import com.example.courseserver.service.IUserService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AjaxResult login(String phone, String pwd) {

        if (phone== null && pwd == null){
            return AjaxResult.error("用户名或密码不能为空");
        }
        Query query = new Query(Criteria
                .where("phone").is(phone)
                .and("pwd").is(pwd)
        );
        User one = mongoTemplate.findOne(query, User.class);
        if (one == null){
            return AjaxResult.error("用户名或密码错误");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(one,userVO);
        return AjaxResult.success(userVO);
    }


}
