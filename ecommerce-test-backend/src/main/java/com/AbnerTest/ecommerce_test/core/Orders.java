package com.AbnerTest.ecommerce_test.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "orderId")
@SQLDelete(sql = "UPDATE orders SET active = false, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("active = true")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean active = true;
    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItems> orderItems = new ArrayList<>();

    public Orders(Users user, OrderStatus status, BigDecimal total) {
        this.user = user;
        this.status = status;
        this.total = total;
    }

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
