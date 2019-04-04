package com.myself.dao.mysql;

import com.myself.dao.base.MysqlDaoSupport;
import com.myself.dto.SensitiveWordDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */
@Repository("sensitiveWordDao")
public class SensitiveWordDao extends MysqlDaoSupport{


    public SensitiveWordDao() {
        super("com.etrip.dao.SensitiveWordMapper");
    }


    /**
     * 新增敏感词
     * @param sensitiveWordDto
     * @return
     */
    public int insertSensitiveWord(SensitiveWordDto sensitiveWordDto) {
        return super.insert("insert",sensitiveWordDto);
    }



    /**
     * 查询敏感词结合
     * @return
     */
    public List<String> selectSensitiveWordList(){
        return super.queryForList("selectSensitiveWordList");
    }
}
