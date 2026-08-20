package com.AbnerTest.ecommerce_test.core;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "productId")
public class Products{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<OrderItems> orderItems = new ArrayList<>();

    public Products(Categories category, String name, String description, BigDecimal price, Integer stock, String imageUrl) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
