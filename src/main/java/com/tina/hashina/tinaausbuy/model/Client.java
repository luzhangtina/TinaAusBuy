package com.tina.hashina.tinaausbuy.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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

    @NotNull
    private String userName;
    private String weChatId;
    private String aliPayId;
    private String phoneNumber;

    public Client(String userName, String weChatId, String aliPayId, String phoneNumber) {
        this.userName = userName;
        this.weChatId = weChatId;
        this.aliPayId = aliPayId;
        this.phoneNumber = phoneNumber;
    }

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @Override
    public String toString() {
        String userInfo = "UserId: " + getUserId().toString() + " UserName: " + getUserName();
        return userInfo;
    }
}
