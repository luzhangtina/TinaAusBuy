package com.tina.hashina.tinaausbuy.module;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="client")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String weChatId;
    private String aliPayId;
    private String phoneNumber;
    private String address;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderHead> orderHeads = new ArrayList<>();

    public void addOrderHead(OrderHead orderHead) {
        orderHeads.add(orderHead);
    }

    public void removeOrderHead(OrderHead orderHead) {
        orderHeads.remove(orderHead);
    }
}
