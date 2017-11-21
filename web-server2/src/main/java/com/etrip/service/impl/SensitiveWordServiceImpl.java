package com.etrip.service.impl;

import com.etrip.dao.mysql.SensitiveWordDao;
import com.etrip.dto.SensitiveWordDto;
import com.etrip.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService {


    @Autowired
    private SensitiveWordDao sensitiveWordDao;


    /**
     * 新增敏感词
     * @param sensitiveWordDto
     * @return
     */
    @Override
    public int insertSensitiveWord(SensitiveWordDto sensitiveWordDto) {
        return sensitiveWordDao.insertSensitiveWord(sensitiveWordDto);
    }

    /**
     * 查询敏感词结合
     * @return
     */
    @Override
    public List<String> selectSensitiveWordList() {
        return sensitiveWordDao.selectSensitiveWordList();
    }
}
