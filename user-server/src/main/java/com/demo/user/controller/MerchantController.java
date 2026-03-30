package com.demo.user.controller;

import com.demo.user.entity.Merchant;
import com.demo.user.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/merchant")
@Validated
public class MerchantController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

    @Autowired
    private MerchantService merchantService;

    // 根据ID查询商家：GET /merchant/1
    @GetMapping("/{id}")
    public Merchant getMerchantById(@PathVariable Long id) {
        logger.info("查询商家，id: {}", id);
        return merchantService.getMerchantById(id);
    }

    // 查询所有商家：GET /merchant/list
    @GetMapping("/list")
    public List<Merchant> getAllMerchants() {
        logger.info("查询所有商家列表");
        return merchantService.getAllMerchants();
    }

    // 新增商家：POST /merchant
    @PostMapping
    public String addMerchant(@Valid @RequestBody Merchant merchant) {
        logger.info("新增商家，merchant: {}", merchant);
        boolean result = merchantService.addMerchant(merchant);
        return result ? "商家新增成功" : "商家新增失败";
    }

    // 更新商家：PUT /merchant
    @PutMapping
    public String updateMerchant(@Valid @RequestBody Merchant merchant) {
        logger.info("更新商家，merchant: {}", merchant);
        boolean result = merchantService.updateMerchant(merchant);
        return result ? "商家更新成功" : "商家更新失败";
    }

    // 删除商家：DELETE /merchant/1
    @DeleteMapping("/{id}")
    public String deleteMerchant(@PathVariable Long id) {
        logger.info("删除商家，id: {}", id);
        boolean result = merchantService.deleteMerchant(id);
        return result ? "商家删除成功" : "商家删除失败";
    }
}
