package com.demo.product.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class Product {
    private Long id;          // 主键ID
    private Long merchantId;  // 商家ID
    
    @NotBlank(message = "商品名称不能为空")
    private String name;      // 商品名称
    
    @NotNull(message = "商品价格不能为空")
    @Positive(message = "商品价格必须大于0")
    @Digits(integer = 8, fraction = 2, message = "商品价格格式不正确，最多8位整数和2位小数")
    private BigDecimal price; // 商品价格
    
    @PositiveOrZero(message = "库存不能为负数")
    private Integer stock;    // 库存
    private Integer isDeleted; // 逻辑删除标记：0-未删除，1-已删除


    /**
     * @Description 构造方法
     * @Author Peter
     * @Date 2026-03-30
     */
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @Description 商品信息字符串表示
     * @Author Peter
     * @Date 2026-03-30
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", merchantId=" + merchantId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
