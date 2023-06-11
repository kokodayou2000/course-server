package com.example.courseserver.controller;

import com.example.courseserver.domain.AjaxResult;
import com.example.courseserver.entity.Doc;
import com.example.courseserver.entity.vo.InsertVo;
import com.example.courseserver.service.IDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doc")
public class DocController {

    @Autowired
    IDocService docService;


    /**
     * 分页查询doc数据 by Id
     * @param pageNum 第几页
     * @param pageSize 每页多少数据
     * @param id 手机号，具有唯一性
     * @return 结果
     */
    @GetMapping("/pageList/{pageNum}/{pageSize}")
    public AjaxResult getDocsPageById(
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize,
            @RequestParam("id") String id
    ){
        return docService.queryPageById(pageNum,pageSize,id);
    }

    /**
     * 查询所有的doc数据
     * @param id 手机号，具有唯一性
     * @return 结果
     */
    @GetMapping("/getAll")
    public AjaxResult getAllDocs(
            @RequestParam("id") String id
    ){
        return docService.queryAllDocById(id);
    }

    /**
     * 查询指定文档id的数据
     * @param doc_Id 文档id
     * @return 结果
     */
    @GetMapping("/getOneDocByDocID")
    public AjaxResult getOneDocByUserIDAndDocID(
            @RequestParam("id") String doc_Id
    ){
        return docService.getOneDocByDocID(doc_Id);
    }




    /**
     * 插入数据
     */
    @PostMapping("/update")
    public AjaxResult insertDocs(
            @RequestBody InsertVo insertVo
    ){
        return docService.insertDocs(insertVo.getPhone(),insertVo.getContent());
    }

    @PostMapping("/updateDoc")
    public AjaxResult updateDoc(@RequestParam("id") String id,
                              @RequestParam("content") String content){
        return docService.updateDoc(id,content);
    }

    /**
     * 新增文档
     * @param doc 文档
     * @return 结果
     */
    @PostMapping("/save")
    public AjaxResult saveDoc(@RequestBody Doc doc){
        return docService.save(doc);
    }

}
