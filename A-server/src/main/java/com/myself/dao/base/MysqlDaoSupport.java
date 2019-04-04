package com.myself.dao.base;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MysqlDaoSupport extends BaseDao {


	public MysqlDaoSupport(String namespaceName) {
		super(namespaceName);
	}
	
	
	@Autowired
	@Qualifier("sqlSessionTemplateMysql")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
