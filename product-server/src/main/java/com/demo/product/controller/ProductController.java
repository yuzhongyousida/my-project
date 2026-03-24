package com.demo.product.controller;

import com.demo.product.entity.Product;
import com.demo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ProductController
 * @Description 商品控制器
 * @Author WangTeng
 * @date 2026-03-23
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 根据ID查询商品：GET /product/1
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // 查询所有商品：GET /product/list
    @GetMapping("/list")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 新增商品：POST /product
    @PostMapping
    public String addProduct(@RequestBody Product product) {
        boolean result = productService.addProduct(product);
        return result ? "商品新增成功" : "商品新增失败";
    }
}