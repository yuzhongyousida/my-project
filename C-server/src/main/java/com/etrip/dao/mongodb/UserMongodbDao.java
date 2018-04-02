package com.etrip.dao.mongodb;

import com.etrip.dao.base.BaseMongoTemplate;
import com.etrip.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
@Repository("userMongodbDao")
public class UserMongodbDao extends BaseMongoTemplate{

    /**
     * 新增
     * @param user
     */
    public void insert(User user){
        DBObject object = new BasicDBObject();
        object.put("userName", user.getUserName());
        object.put("password", user.getPassword());
        super.insert(object,"user");
    }

    /**
     * 按照名称查询
     * @param userName
     */
    public List<User> selectListByUserName(String userName){
        Query query = new Query();
        Criteria criteria = new Criteria("userName");
        criteria.is(userName);
        query.addCriteria(criteria);
        return super.getByQuery(User.class, query, "user");
    }

    /**
     * 更新
     * @param user
     */
    public void updateByUserName(User user){
        if(user==null || StringUtils.isEmpty(user.getUserName())){
            return;
        }

        Query query = new Query();
        Criteria criteria = new Criteria("userName");
        criteria.is(user.getUserName());
        query.addCriteria(criteria);

        Update update = new Update();
        update.set("password", user.getPassword());

        super.updateByQuery(query, update, "user");
    }

    /**
     * 删除
     * @param user
     */
    public void deleteByUserName(User user){
        if(user==null || StringUtils.isEmpty(user.getUserName())){
            return;
        }
        String userName = user.getUserName();//姓名

        Query query = new Query();
        Criteria criteria = new Criteria("userName");
        criteria.is(userName);
        query.addCriteria(criteria);

        super.removeByQuery(query,"user");
    }

}
