package com.etrip.service.impl;

import com.etrip.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/6
 */
@Service("testService")
public class TestServiceImpl implements TestService {


    @Override
    public Integer test(String str, Long l, List<String> list) {

        long start = System.currentTimeMillis();

        long end = System.currentTimeMillis();

        return 1;
    }
}
