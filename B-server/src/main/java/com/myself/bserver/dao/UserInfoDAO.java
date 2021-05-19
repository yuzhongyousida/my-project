package com.myself.bserver.dao;

import com.myself.bserver.dao.base.BaseDao;
import com.myself.bserver.dto.UserInfoDTO;
import org.springframework.stereotype.Repository;

@Repository("userInfoDAO")
public class UserInfoDAO extends BaseDao {

    public UserInfoDAO(){
        super("com.myself.bserver.dao.UserInfoDAO");
    }

    public int deleteByPrimaryKey(Long id){
        return super.delete("deleteByPrimaryKey", id);
    }

    public int insertSelective(UserInfoDTO record){
        return super.insert("insertSelective", record);
    }

    public UserInfoDTO selectByPrimaryKey(Long id){
        return super.get("selectByPrimaryKey", id);
    }

    public int updateByPrimaryKeySelective(UserInfoDTO record){
        return super.update("updateByPrimaryKeySelective", record);
    }
}