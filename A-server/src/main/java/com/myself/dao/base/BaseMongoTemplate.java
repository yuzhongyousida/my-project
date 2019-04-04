package com.myself.dao.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */
public class BaseMongoTemplate {

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;



    /**
     * 添加对象
     * @param obj
     */
    protected void insert(Object obj, String collectionName) {
        if(obj==null || StringUtils.isEmpty(collectionName)){
            return ;
        }
        this.mongoTemplate.insert(obj, collectionName);

    }

    /**
     * 按条件删除一个对象
     * @param query
     */
    protected void removeByQuery(Query query, String collectionName) {
        if(query==null || StringUtils.isEmpty(collectionName)){
            return ;
        }
        mongoTemplate.remove(query, collectionName);
    }


    /**
     * 根据主键id返回对象
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T findById(Class<T> entityClass, String id, String collectionName){
        if(entityClass==null || StringUtils.isEmpty(id) || StringUtils.isEmpty(collectionName)){
            return null;
        }
        return mongoTemplate.findById(id, entityClass, collectionName);
    }


    /**
     * 根据条件修改对象
     * @param query
     */
    protected void updateByQuery(Query query, Update update, String collectionName) {
        if(query==null || update==null || StringUtils.isEmpty(collectionName)){
            return ;
        }
        this.mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 分页查询
     * @param entityClass
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param <T>
     * @return
     */
    protected <T> List<T> getPageByQuery(Class<T> entityClass, Query query, int pageNumber, int pageSize, String collectionName) {
        if(entityClass==null || query==null || pageNumber<=0 || pageSize<=0 || StringUtils.isEmpty(collectionName)){
            return null;
        }
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        return this.mongoTemplate.find(query, entityClass,collectionName);
    }

    /**
     * 根据条件查询列表
     * @param entityClass
     * @param query
     * @param collectionName
     * @param <T>
     * @return
     */
    protected <T> List<T> getByQuery(Class<T> entityClass, Query query, String collectionName) {
        if(entityClass==null || query==null || StringUtils.isEmpty(collectionName)){
            return null;
        }
        return this.mongoTemplate.find(query, entityClass,collectionName);
    }

    /**
     * 总数查询
     * @param entityClass
     * @param query
     * @param <T>
     * @return
     */
    protected <T> Long count(Class<T> entityClass, Query query) {
        if(entityClass==null || query==null){
            return null;
        }
        return this.mongoTemplate.count(query, entityClass);
    }

    /**
     * 批量插入
     * @param collection
     * @param <T>
     */
    protected <T> void addCollection(Collection<T> collection, String collectionName){
        if(CollectionUtils.isEmpty(collection) || StringUtils.isEmpty(collectionName)){
            return ;
        }
        this.mongoTemplate.insert(collection, collectionName);
    }
}
