package com.demo.product.mapper;

import com.demo.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ProductMapper
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
@Mapper
public interface ProductMapper {

    Product selectProductById(Long id);

    List<Product> selectAllProducts();

    int insertProduct(Product product);


}
