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
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="orderHead")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHead implements Serializable {
    private static final long serialVersionUID = -462462010262900116L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;
    private Date bookDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderHeadState state;
    private String cooperator;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "RMB")})
    private Money paymentAmount;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "RMB")})
    private Money postage;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "RMB")})
    private Money productsAmount;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "RMB")})
    private Money profit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_userId")
    private Client client;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @OneToMany(
            mappedBy = "orderHead",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderHead )) return false;
        return orderNumber == ((OrderHead) o).getOrderNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderNumber);
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
    }
}
