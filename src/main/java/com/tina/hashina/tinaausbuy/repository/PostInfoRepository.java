package com.tina.hashina.tinaausbuy.repository;

import com.tina.hashina.tinaausbuy.model.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {
    List<PostInfo> findByClient_UserIdOrderByAddress(Long userId);
    List<PostInfo> findByClient_UserIdAndNameOrderByAddress(Long userId, String name);
}
