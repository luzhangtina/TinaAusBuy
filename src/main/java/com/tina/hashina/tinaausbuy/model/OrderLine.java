package com.tina.hashina.tinaausbuy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="orderLine")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine implements Serializable {
    private static final long serialVersionUID = -3234383809086524010L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineNumber;

    private String carrier;
    private String packageNumber;
    private Date shipDate;
    private Date receiptDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderHead_orderNumber")
    private OrderHead orderHead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_productId")
    private Product product;

    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderLineState state;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine )) return false;
        return orderLineNumber == ((OrderLine) o).getOrderLineNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderLineNumber);
    }
}
