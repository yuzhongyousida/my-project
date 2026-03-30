package com.demo.product.controller;

import com.demo.product.entity.Product;
import com.demo.product.entity.ProductQuery;
import com.demo.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName ProductController
 * @Description 商品控制器
 * @Author WangTeng
 * @date 2026-03-23
 */
@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * @Description 根据ID查询商品
     * @Author Peter
     * @Date 2026-03-30
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        logger.info("查询商品，id: {}", id);
        return productService.getProductById(id);
    }

    /**
     * @Description 查询所有商品列表
     * @Author Peter
     * @Date 2026-03-30
     */
    @GetMapping("/list")
    public List<Product> getAllProducts() {
        logger.info("查询所有商品列表");
        return productService.getAllProducts();
    }

    /**
     * @Description 新增商品
     * @Author Peter
     * @Date 2026-03-30
     */
    @PostMapping
    public String addProduct(@Valid @RequestBody Product product) {
        logger.info("新增商品，product: {}", product);
        boolean result = productService.addProduct(product);
        return result ? "商品新增成功" : "商品新增失败";
    }

    /**
     * @Description 更新商品
     * @Author Peter
     * @Date 2026-03-30
     */
    @PutMapping
    public String updateProduct(@Valid @RequestBody Product product) {
        logger.info("更新商品，product: {}", product);
        boolean result = productService.updateProduct(product);
        return result ? "商品更新成功" : "商品更新失败";
    }

    /**
     * @Description 删除商品
     * @Author Peter
     * @Date 2026-03-30
     */
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        logger.info("删除商品，id: {}", id);
        boolean result = productService.deleteProduct(id);
        return result ? "商品删除成功" : "商品删除失败";
    }

    /**
     * @Description 多条件查询商品列表
     * @Author Peter
     * @Date 2026-03-30
     */
    @GetMapping("/query")
    public Map<String, Object> queryProducts(@Valid ProductQuery query) {
        logger.info("多条件查询商品，query: {}", query);
        List<Product> products = productService.queryProducts(query);
        int total = productService.countProducts(query);
        Map<String, Object> result = new HashMap<>();
        result.put("products", products);
        result.put("total", total);
        result.put("pageNum", query.getPageNum());
        result.put("pageSize", query.getPageSize());
        return result;
    }
}