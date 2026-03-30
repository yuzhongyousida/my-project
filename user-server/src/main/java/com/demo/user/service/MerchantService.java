package com.demo.user.service;

import com.demo.user.entity.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant getMerchantById(Long id);

    List<Merchant> getAllMerchants();

    boolean addMerchant(Merchant merchant);

    boolean updateMerchant(Merchant merchant);

    boolean deleteMerchant(Long id);
}
