package com.demo.product.service.impl;

import com.demo.product.entity.Product;
import com.demo.product.entity.ProductQuery;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public boolean addProduct(Product product) {
        return productMapper.insertProduct(product) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProduct(Product product) {
        return productMapper.updateProduct(product) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProduct(Long id) {
        return productMapper.deleteProduct(id) > 0;
    }

    @Override
    public List<Product> queryProducts(ProductQuery query) {
        return productMapper.selectProductsByCondition(query);
    }

    @Override
    public int countProducts(ProductQuery query) {
        return productMapper.countProductsByCondition(query);
    }
}
