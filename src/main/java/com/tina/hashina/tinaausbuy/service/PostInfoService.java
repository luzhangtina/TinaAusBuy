package com.tina.hashina.tinaausbuy.service;

import com.tina.hashina.tinaausbuy.module.Client;
import com.tina.hashina.tinaausbuy.module.PostInfo;
import com.tina.hashina.tinaausbuy.repository.PostInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostInfoService {
    @Autowired
    PostInfoRepository postInfoRepository;

    public PostInfo createPostInfo(Client client,
                               String name,
                               String address,
                               String phoneNumber) {

        PostInfo postInfo = PostInfo.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .client(client)
                .build();
        PostInfo savedPostInfo = postInfoRepository.save(postInfo);
        log.info("New post info: {}", savedPostInfo);
        return savedPostInfo;
    }

    public Boolean deletePostInfo(Long id) {
        Optional<PostInfo> postInfo = postInfoRepository.findById(id);
        if (postInfo.isPresent()) {
            log.info("Post Info is deleted: {}", postInfo.get());
            postInfoRepository.deleteById(id);
            return true;
        }

        log.warn("Post Info does not exist: {}", id);
        return false;
    }

    public Boolean updatePostName(PostInfo postInfo, String name) {
        if (name == null || name.length() <= 0) {
            log.warn("Name is invalid");
            return false;
        }

        postInfo.setName(name);
        postInfoRepository.save(postInfo);
        log.info("Updated Post Info: {}", postInfo);
        return true;
    }

    public Boolean updatePostAddress(PostInfo postInfo, String address) {
        if (address == null || address.length() <= 0) {
            log.warn("Address is invalid");
            return false;
        }

        postInfo.setAddress(address);
        postInfoRepository.save(postInfo);
        log.info("Updated Post Info: {}", postInfo);
        return true;
    }

    public Boolean updatePostPhoneNumber(PostInfo postInfo, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() <= 0) {
            log.warn("PhoneNumber is invalid");
            return false;
        }

        postInfo.setPhoneNumber(phoneNumber);
        postInfoRepository.save(postInfo);
        log.info("Updated Post Info: {}", postInfo);
        return true;
    }

    public List<PostInfo> findAllPostInfoByUserId(Long userId) {
        List<PostInfo> postInfos = postInfoRepository
                .findByClient_UserIdOrderByAddress(userId);
        postInfos.forEach(postInfo -> log.info("Post Info Found: {} for UserId: {}",
                postInfo, userId));
        return postInfos;
    }

    public List<PostInfo> findAllPostInfoByUserIdAndPostName(Long userId, String name) {
        List<PostInfo> postInfos = postInfoRepository
                .findByClient_UserIdAndNameOrderByAddress(userId, name);
        postInfos.forEach(postInfo ->
                log.info("Post Info Found: {} for UserId: {} and name: {}",
                postInfo, userId, name));
        return postInfos;
    }
}
