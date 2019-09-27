package com.tina.hashina.tinaausbuy.module;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = -6452119412121738557L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull
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
    private MeasureUnit measureUnit;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @JsonCreator
    public Product(@JsonProperty("proNameEng") String proNameEng,
                   @JsonProperty("proNameChn") String proNameChn,
                   @JsonProperty("priceAud") double priceAud,
                   @JsonProperty("priceRmb") double priceRmb,
                   @JsonProperty("measureValue") double measureValue,
                   @JsonProperty("measureUnit") MeasureUnit measureUnit) {
        this.proNameEng = proNameEng;
        this.proNameChn = proNameChn;
        this.priceAud = Money.of(CurrencyUnit.AUD, priceAud);
        this.priceRmb = Money.of(CurrencyUnit.of("CNY"), priceRmb);
        this.measureValue = measureValue;
        this.measureUnit = measureUnit;
    }

    public double getPriceAud() {
        return this.priceAud.getAmount().floatValue();
    }

    public double getPriceRmb() {
        return this.priceRmb.getAmount().floatValue();
    }

    public void setPriceAud(double priceAud) {
        this.priceAud = Money.of(CurrencyUnit.AUD, priceAud);
    }

    public void setPriceRmb(double priceRmb) {
        this.priceRmb = Money.of(CurrencyUnit.of("CNY"), priceRmb);
    }

    @Override
    public String toString() {
        if (getProductId() != null) {
            return "Product Id: " + getProductId().toString()
                    + " EngName: " + getProNameEng()
                    + " Price: " + getPriceRmb();
        } else {
            return " EngName: " + getProNameEng()
                    + " Price: " + getPriceRmb();
        }
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
