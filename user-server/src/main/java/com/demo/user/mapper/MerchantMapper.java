package com.demo.user.mapper;

import com.demo.user.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MerchantMapper {
    Merchant selectMerchantById(@Param("id") Long id);

    List<Merchant> selectAllMerchants();

    int insertMerchant(Merchant merchant);

    int updateMerchant(Merchant merchant);

    int deleteMerchant(Long id);
}
