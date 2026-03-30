package com.demo.user.service.impl;

import com.demo.user.entity.Merchant;
import com.demo.user.mapper.MerchantMapper;
import com.demo.user.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public Merchant getMerchantById(Long id) {
        return merchantMapper.selectMerchantById(id);
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantMapper.selectAllMerchants();
    }

    @Override
    public boolean addMerchant(Merchant merchant) {
        return merchantMapper.insertMerchant(merchant) > 0;
    }

    @Override
    public boolean updateMerchant(Merchant merchant) {
        return merchantMapper.updateMerchant(merchant) > 0;
    }

    @Override
    public boolean deleteMerchant(Long id) {
        return merchantMapper.deleteMerchant(id) > 0;
    }
}
