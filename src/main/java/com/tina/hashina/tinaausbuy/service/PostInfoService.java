package com.tina.hashina.tinaausbuy.service;

import com.tina.hashina.tinaausbuy.model.Client;
import com.tina.hashina.tinaausbuy.model.PostInfo;
import com.tina.hashina.tinaausbuy.repository.ClientRepository;
import com.tina.hashina.tinaausbuy.repository.PostInfoRepository;
import javafx.geometry.Pos;
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

    @Autowired
    ClientService clientService;

    public PostInfo createPostInfo(Long userId, PostInfo postInfo) {
        if (userId == null || postInfo == null) {
            return null;
        }

        Client client = clientService.findClientById(userId);
        if (client == null) {
            return null;
        }

        postInfo.setClient(client);

        PostInfo savedPostInfo = postInfoRepository.save(postInfo);
        log.info("New post info: {}", savedPostInfo);
        return postInfo;
    }

    public Boolean deletePostInfo(Long postId) {
        if (postId == null) {
            return false;
        }

        Optional<PostInfo> postInfo = postInfoRepository.findById(postId);
        if (postInfo.isPresent()) {
            log.info("Post Info is deleted: {}", postInfo.get());
            postInfoRepository.deleteById(postId);
            return true;
        }

        log.warn("Post Info does not exist: {}", postId);
        return false;
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

    public PostInfo findPostInfoByPostId(Long postId) {
        Optional<PostInfo> postInfo = postInfoRepository.findById(postId);
        if (postInfo.isPresent()) {
            log.info("PostInfo Found: {}", postInfo.get());
            return postInfo.get();
        }

        return null;
    }

    public PostInfo updatePostInfo(Long postId, PostInfo postInfo) {
        if (postId == null || postInfo == null) {
            return null;
        }

        if (!postId.equals(postInfo.getId())) {
            return null;
        }

        Optional<PostInfo> postInfoInDb = postInfoRepository.findById(postId);
        if (!postInfoInDb.isPresent()) {
            return null;
        }

        PostInfo savedPostInfo = postInfoRepository.save(postInfo);
        log.info("Updated PostInfo: {}", savedPostInfo);
        return savedPostInfo;
    }
}
