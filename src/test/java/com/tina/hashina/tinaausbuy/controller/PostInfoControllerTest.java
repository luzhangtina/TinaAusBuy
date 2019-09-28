package com.tina.hashina.tinaausbuy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.hashina.tinaausbuy.model.PostInfo;
import com.tina.hashina.tinaausbuy.service.PostInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostInfoController.class)
public class PostInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostInfoService postInfoService;

    @Test
    public void getPostInfosByUserId_shouldReturnPostList() throws Exception {
        when(postInfoService.findAllPostInfoByUserId(notNull()))
                .thenReturn(Arrays.asList(new PostInfo("test01", "address01", "1123456789"),
                        new PostInfo("test02", "address02", "1234567890")));

        this.mockMvc.perform(get("/clients/{userId}/addresses", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("test01"))
                .andExpect(jsonPath("$.[1].name").value("test02"));
    }

    @Test
    public void getPostInfosByUserId_shouldReturnNullList() throws Exception {
        when(postInfoService.findAllPostInfoByUserId(notNull()))
                .thenReturn(null);

        MvcResult mvcResult =  this.mockMvc.perform(get("/clients/{userId}/addresses", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getPostInfosByPostId_shouldReturnPostInfo() throws Exception {
        when(postInfoService.findPostInfoByPostId(notNull()))
                .thenReturn(new PostInfo("test01", "address01", "1123456789"));

        this.mockMvc.perform(get("/clients/addresses/{postId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test01"))
                .andExpect(jsonPath("$.address").value("address01"))
                .andExpect(jsonPath("$.phoneNumber").value("1123456789"));
    }

    @Test
    public void getPostInfosByPostId_shouldReturnNoContentWhenPostInfoNotExist() throws Exception {
        when(postInfoService.findPostInfoByPostId(notNull()))
                .thenReturn(null);

        this.mockMvc.perform(get("/clients/addresses/{postId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void addPostInfo_shouldReturnOK() throws Exception {
        PostInfo postInfo = new PostInfo("test01","address01", "124567890");
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test01"))
                .andExpect(jsonPath("$.address").value("address01"))
                .andExpect(jsonPath("$.phoneNumber").value("124567890"));
    }

    @Test
    public void addPostInfo_shouldReturnBadRequestWhenRequestBodyIsEmpty() throws Exception {
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(null);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addPostInfo_shouldReturnBadRequestWhenNameOrAddressOrPhoneIsNull() throws Exception {
        PostInfo postInfo = new PostInfo(null,"address01", "124567890");
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        postInfo = new PostInfo("test01",null, "124567890");
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        postInfo = new PostInfo("test01","address01", null);
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addPostInfo_shouldReturn422WhenAddPostInfoFailed() throws Exception {
        PostInfo postInfo = new PostInfo("test01","address01", "124567890");
        when(postInfoService.createPostInfo(notNull(), notNull())).thenReturn(null);

        this.mockMvc.perform(post("/clients/{userId}/addresses", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updatePostInfo_shouldReturnOK() throws Exception {
        PostInfo postInfo = new PostInfo("test01","address01", "124567890");
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test01"))
                .andExpect(jsonPath("$.address").value("address01"))
                .andExpect(jsonPath("$.phoneNumber").value("124567890"));
    }

    @Test
    public void updatePostInfo_shouldReturnBadRequestWhenRequestBodyIsEmpty() throws Exception {
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(null);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePostInfo_shouldReturnBadRequestWhenNameOrAddressOrPhoneIsNull() throws Exception {
        PostInfo postInfo = new PostInfo(null,"address01", "124567890");
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        postInfo = new PostInfo("test01",null, "124567890");
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        postInfo = new PostInfo("test01","address01", null);
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(postInfo);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePostInfo_shouldReturnNoContentWhenUpdateFailed() throws Exception {
        PostInfo postInfo = new PostInfo("test01","address01", "124567890");
        when(postInfoService.updatePostInfo(notNull(), notNull())).thenReturn(null);

        this.mockMvc.perform(put("/clients/addresses/{postId}", 1)
                .content(objectMapper.writeValueAsString(postInfo))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePostInfo_shouldReturnOK() throws Exception {
        when(postInfoService.deletePostInfo(notNull())).thenReturn(true);

        this.mockMvc.perform(delete("/clients/addresses/{postId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deletePostInfo_shouldReturnNoContentWhenDeleteFailed() throws Exception {
        when(postInfoService.deletePostInfo(notNull())).thenReturn(false);

        this.mockMvc.perform(delete("/clients/addresses/{postId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
