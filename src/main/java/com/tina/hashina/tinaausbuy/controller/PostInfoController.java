package com.tina.hashina.tinaausbuy.controller;

import com.tina.hashina.tinaausbuy.model.PostInfo;
import com.tina.hashina.tinaausbuy.service.PostInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class PostInfoController {
    private final PostInfoService postInfoService;

    @Autowired
    public PostInfoController(PostInfoService postInfoService) {
        this.postInfoService = postInfoService;
    }

    @GetMapping("/clients/{userId}/addresses")
    public ResponseEntity<List<PostInfo>> getAddressesByUserId(@PathVariable Long userId) {
        List<PostInfo> postInfos = postInfoService.findAllPostInfoByUserId(userId);
        return new ResponseEntity<>(postInfos, HttpStatus.OK);
    }

    @GetMapping("/clients/addresses/{postId}")
    public ResponseEntity<PostInfo> getAddressesByPostId(@PathVariable Long postId) {
        PostInfo postInfo = postInfoService.findPostInfoByPostId(postId);
        if (postInfo == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(postInfo, HttpStatus.OK);
    }

    @PostMapping("/clients/{userId}/addresses")
    public ResponseEntity<PostInfo> addAddress(@PathVariable Long userId,
                                               @Valid @RequestBody PostInfo postInfo) {
        PostInfo savedPostInfo = postInfoService.createPostInfo(userId, postInfo);
        if (savedPostInfo == null) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(savedPostInfo, HttpStatus.OK);
    }

    @PutMapping("/clients/addresses/{postId}")
    public ResponseEntity<PostInfo> updateAddress(@PathVariable Long postId,
                                                  @Valid @RequestBody PostInfo postInfo) {
        PostInfo savedPostInfo = postInfoService.updatePostInfo(postId, postInfo);
        if (savedPostInfo == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(savedPostInfo, HttpStatus.OK);
    }

    @DeleteMapping("/clients/addresses/{postId}")
    public ResponseEntity<HttpStatus> deleteAddress(@PathVariable Long postId) {
        boolean ret = postInfoService.deletePostInfo(postId);
        if (ret) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
