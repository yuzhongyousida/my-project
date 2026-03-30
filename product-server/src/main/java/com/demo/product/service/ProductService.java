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
    Product getProductById(@Param("id") Long id);

    List<Product> getAllProducts();

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Long id);

    List<Product> queryProducts(ProductQuery query);

    int countProducts(ProductQuery query);

}
