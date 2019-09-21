package com.tina.hashina.tinaausbuy.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String proNameEng;
    private String proNameChn;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "AUD")})
    private Money priceAud;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money priceRmb;

    private double measureValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasureUnit measureUnit;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
    }

    @Override
    public String toString() {
        return "Product Id: " + getProductId().toString()
                + " EngName: " + getProNameEng()
                + " Price: " + getPriceRmb().getAmount().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.measureValue, measureValue) == 0 &&
                productId.equals(product.productId) &&
                proNameEng.equals(product.proNameEng) &&
                Objects.equals(proNameChn, product.proNameChn) &&
                measureUnit == product.measureUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, proNameEng, proNameChn, measureValue, measureUnit);
    }
}
