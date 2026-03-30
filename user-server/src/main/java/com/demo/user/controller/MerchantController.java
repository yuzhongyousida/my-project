package com.demo.user.controller;

import com.demo.user.entity.Merchant;
import com.demo.user.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    // 根据ID查询商家：GET /merchant/1
    @GetMapping("/{id}")
    public Merchant getMerchantById(@PathVariable Long id) {
        return merchantService.getMerchantById(id);
    }

    // 查询所有商家：GET /merchant/list
    @GetMapping("/list")
    public List<Merchant> getAllMerchants() {
        return merchantService.getAllMerchants();
    }

    // 新增商家：POST /merchant
    @PostMapping
    public String addMerchant(@RequestBody Merchant merchant) {
        boolean result = merchantService.addMerchant(merchant);
        return result ? "商家新增成功" : "商家新增失败";
    }

    // 更新商家：PUT /merchant
    @PutMapping
    public String updateMerchant(@RequestBody Merchant merchant) {
        boolean result = merchantService.updateMerchant(merchant);
        return result ? "商家更新成功" : "商家更新失败";
    }

    // 删除商家：DELETE /merchant/1
    @DeleteMapping("/{id}")
    public String deleteMerchant(@PathVariable Long id) {
        boolean result = merchantService.deleteMerchant(id);
        return result ? "商家删除成功" : "商家删除失败";
    }
}
