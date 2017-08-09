package com.etrip.service;

import com.etrip.dto.SensitiveWordDto;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public interface SensitiveWordService {


    /**
     * 新增敏感词
     * @param sensitiveWordDto
     * @return
     */
    public int insertSensitiveWord(SensitiveWordDto sensitiveWordDto);

    /**
     * 查询敏感词结合
     * @return
     */
    public List<String> selectSensitiveWordList();


}
