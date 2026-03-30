package com.demo.product.service;

import com.demo.product.entity.Product;
import com.demo.product.entity.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName ProductService
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
public interface ProductService {
    /**
     * @Description 根据ID获取商品
     * @Author Peter
     * @Date 2026-03-30
     */
    Product getProductById(@Param("id") Long id);

    /**
     * @Description 获取所有商品列表
     * @Author Peter
     * @Date 2026-03-30
     */
    List<Product> getAllProducts();

    /**
     * @Description 新增商品
     * @Author Peter
     * @Date 2026-03-30
     */
    boolean addProduct(Product product);

    /**
     * @Description 更新商品
     * @Author Peter
     * @Date 2026-03-30
     */
    boolean updateProduct(Product product);

    /**
     * @Description 删除商品
     * @Author Peter
     * @Date 2026-03-30
     */
    boolean deleteProduct(Long id);

    /**
     * @Description 多条件查询商品
     * @Author Peter
     * @Date 2026-03-30
     */
    List<Product> queryProducts(ProductQuery query);

    /**
     * @Description 统计商品数量
     * @Author Peter
     * @Date 2026-03-30
     */
    int countProducts(ProductQuery query);

}
