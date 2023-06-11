package com.example.courseserver.service.impl;

import com.example.courseserver.common.constant.HttpStatus;
import com.example.courseserver.common.page.TableDataInfo;
import com.example.courseserver.domain.AjaxResult;
import com.example.courseserver.entity.Doc;
import com.example.courseserver.entity.User;
import com.example.courseserver.entity.vo.UserVO;
import com.example.courseserver.repository.DocRepository;
import com.example.courseserver.repository.UserRepository;
import com.example.courseserver.service.IDocService;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@Slf4j
public class DocServiceImpl implements IDocService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DocRepository docRepository;


    @Override
    public AjaxResult queryPageById(int pageNum, int pageSize, String id) {
        if (id == null){
            return AjaxResult.error("获取失败");
        }
        // 通过id获取doc列表
        List<Doc> allByWriterId = docRepository.findAllByWriterId(id);
        for (Doc o : allByWriterId) {
            System.out.println(o);
        }
        // 如何保证不越界呢
        int totals = allByWriterId.size();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = (pageNum) * pageSize;

        if ((startIndex < 0 || startIndex > totals) ){
            return AjaxResult.error("没有此页");
        }
        if (endIndex > totals){
            endIndex = totals;
        }

        List<Doc> subList = allByWriterId.subList(startIndex, endIndex);
        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setTotal(totals);
        tableDataInfo.setRows(subList);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询全部数据成功");
        return AjaxResult.success("success",tableDataInfo);

    }



    @Override
    public AjaxResult queryPageByPhone(int pageNum, int pageSize, String phone) {
        if (phone== null){
            return AjaxResult.error("查询失败");
        }
        Query query = new Query(Criteria
                .where("phone").is(phone)
        );

        User one = mongoTemplate.findOne(query, User.class);

        if (one == null){
            return AjaxResult.error("手机号错误");
        }

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setTotal(one.getDocs().size());

        List<Doc> docs = one.getDocs();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = (pageNum) * pageSize;

        List<Doc> subList = docs.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        tableDataInfo.setRows(subList);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询数据成功");
        return AjaxResult.success("success",tableDataInfo);
    }



    @Override
    public AjaxResult queryAllDocById(String id) {

        List<Doc> allByWriterId = docRepository.findAllByWriterId(id);
        for (Doc o : allByWriterId) {
            System.out.println(o);
        }

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setTotal(allByWriterId.size());
        tableDataInfo.setRows(allByWriterId);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询全部数据成功");
        return AjaxResult.success("success",tableDataInfo);
    }

    @Override
    public AjaxResult queryAllDocOld(String phone) {
        if (phone== null){
            return AjaxResult.error("查询失败");
        }
        Query query = new Query(Criteria
                .where("phone").is(phone)
        );

        User one = mongoTemplate.findOne(query, User.class);
        if (one == null){
            return AjaxResult.error("手机号错误");
        }


        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setTotal(one.getDocs().size());

        List<Doc> docs = one.getDocs();
        tableDataInfo.setRows(docs);
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询全部数据成功");
        return AjaxResult.success("success",tableDataInfo);
    }

    @Override
    public AjaxResult insertDocs(String phone, String content) {

        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withIgnorePaths("lastLogin")
//                .withIgnoreCase("phone")
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatcher::startsWith)
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatcher::endsWith);

        User u = User.builder()
                .phone(phone).build();

        Example<User> example = Example.of(u, matcher);

        User all = userRepository.findOne(example).get();
        System.out.println("find One"+all);

        Doc doc = new Doc();
        doc.setTitle(all.getDocs().get(0).getTitle());
        doc.setContent(content);
        doc.setLastEditTime(new Date());
        ArrayList<Doc> docs = new ArrayList<>();
        docs.add(doc);
        all.setDocs(docs);

        Query query = new Query(Criteria.where("_id").is("1"));
        Update update = new Update().set("docs", docs);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User.class);
        System.out.println(updateResult.getUpsertedId());

        return AjaxResult.success("更新成功");
    }

    @Override
    public AjaxResult updateDoc(String id, String content) {
        Optional<Doc> byId = docRepository.findById(id);
        Doc doc = byId.get();
        if (doc == null){
            AjaxResult.error("保存失败");
        }
        doc.setContent(content);
        doc.setLastEditTime(new Date());
        // 使用mongodb来执行
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("content", content);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Doc.class);
        log.info(updateResult.toString());
        return AjaxResult.success("保存成功");
    }

    @Override
    public AjaxResult save(Doc doc) {

        Doc save = docRepository.save(doc);
        return AjaxResult.success(save);
    }
}
