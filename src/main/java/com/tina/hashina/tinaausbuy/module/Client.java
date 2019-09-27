package com.tina.hashina.tinaausbuy.module;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Client implements Serializable {
    private static final long serialVersionUID = -8290475939114319785L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String weChatId;
    private String aliPayId;
    private String phoneNumber;

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

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PostInfo> postInfos = new ArrayList<>();

    public void addOrderHead(OrderHead orderHead) {
        orderHeads.add(orderHead);
    }

    public void removeOrderHead(OrderHead orderHead) {
        orderHeads.remove(orderHead);
    }

    public void addPostInfo(PostInfo postInfo) {
        postInfos.add(postInfo);
    }

    public void removePostInfo(PostInfo postInfo) {
        postInfos.remove(postInfo);
    }

    @Override
    public String toString() {
        String userInfo = "UserId: " + getUserId().toString() + " UserName: " + getUserName();
        return userInfo;
    }
}
