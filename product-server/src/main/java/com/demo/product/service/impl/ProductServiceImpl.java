package com.demo.product.service.impl;

import com.demo.product.entity.Product;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName ProductServiceImpl
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Long id) {
        return productMapper.selectProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAllProducts();
    }

    @Override
    public boolean addProduct(Product product) {
        return productMapper.insertProduct(product) > 0;
    }
}
