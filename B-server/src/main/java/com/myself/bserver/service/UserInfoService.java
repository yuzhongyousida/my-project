package com.myself.bserver.service;

import com.myself.bserver.dto.UserInfoDTO;

public interface UserInfoService {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserInfoDTO record);

    UserInfoDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfoDTO record);

}
