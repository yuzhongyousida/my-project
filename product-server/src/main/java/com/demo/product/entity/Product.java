package com.demo.product.entity;

/**
 * @FileName Product
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
public class Product {
    private Long id;          // 主键ID
    private String name;      // 商品名称
    private Double price;     // 商品价格
    private Integer stock;    // 库存


    // 构造方法
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
