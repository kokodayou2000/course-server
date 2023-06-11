package com.example.courseserver.service;

import com.example.courseserver.domain.AjaxResult;
import com.example.courseserver.entity.Doc;

public interface IDocService {

    /**
     * 通过user_id得到与 user_id关联的文档
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param id 用户id
     * @return 包含doc的集合
     */
    AjaxResult queryPageById(int pageNum, int pageSize, String id);

    /**
     * 通过手机号获取到文档 已废弃
     */
    @Deprecated
    AjaxResult queryPageByPhone(int pageNum, int pageSize, String phone);


    AjaxResult queryAllDocById(String id);


    AjaxResult queryAllDocOld(String phone);

    AjaxResult insertDocs(String phone, String content);

    /**
     * 保存数据
     * @param id 文档id
     * @param content 文档内容
     * @return
     */
    AjaxResult updateDoc(String id, String content);


    /**
     * 新增文档
     * @param doc 文档
     * @return
     */
    AjaxResult save(Doc doc);

    /**
     * 根据文档id找到指定文档
     * @param userId 用户id
     * @return ajax数据
     */
    AjaxResult getOneDocByDocID(String userId);
}
