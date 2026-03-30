package com.demo.product.mapper;

import com.demo.product.entity.Product;
import com.demo.product.entity.ProductQuery;
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

    int updateProduct(Product product);

    int deleteProduct(Long id);

    List<Product> selectProductsByCondition(ProductQuery query);

    int countProductsByCondition(ProductQuery query);

}
