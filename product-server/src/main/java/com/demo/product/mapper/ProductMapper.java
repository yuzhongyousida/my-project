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

    /**
     * @Description 根据ID查询商品
     * @Author Peter
     * @Date 2026-03-30
     */
    Product selectProductById(Long id);

    /**
     * @Description 查询所有商品
     * @Author Peter
     * @Date 2026-03-30
     */
    List<Product> selectAllProducts();

    /**
     * @Description 插入商品
     * @Author Peter
     * @Date 2026-03-30
     */
    int insertProduct(Product product);

    /**
     * @Description 更新商品
     * @Author Peter
     * @Date 2026-03-30
     */
    int updateProduct(Product product);

    /**
     * @Description 删除商品
     * @Author Peter
     * @Date 2026-03-30
     */
    int deleteProduct(Long id);

    /**
     * @Description 按条件查询商品
     * @Author Peter
     * @Date 2026-03-30
     */
    List<Product> selectProductsByCondition(ProductQuery query);

    /**
     * @Description 按条件统计商品数量
     * @Author Peter
     * @Date 2026-03-30
     */
    int countProductsByCondition(ProductQuery query);

}
