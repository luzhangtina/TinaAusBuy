package com.tina.hashina.tinaausbuy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.hashina.tinaausbuy.module.Client;
import com.tina.hashina.tinaausbuy.service.ClientService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Test
    public void getClients_shouldReturnEmptyWhenNoClient() throws Exception {
        when(clientService.findAllClient()).thenReturn(null);
        MvcResult mvcResult = this.mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getClients_shouldReturnClientsWhenHasClient() throws Exception {
        Client client1 = new Client("client01", "", "", "");
        Client client2 = new Client("client02", "", "", "");
        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.findAllClient()).thenReturn(clients);

        this.mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].userName").value("client01"))
                .andExpect(jsonPath("$.[1].userName").value("client02"));
    }

    @Test
    public void getClientById_shouldReturnNoContentWhenClientNotExist() throws Exception {
        when(clientService.findClientById(notNull())).thenReturn(null);
        this.mockMvc.perform(get("/clients/{userId}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getClientById_shouldReturnClientWhenClientExist() throws Exception {
        when(clientService.findClientById(notNull())).thenReturn(new Client("client01",
                "", "", ""));

        this.mockMvc.perform(get("/clients/{userId}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("client01"));
    }

    @Test
    public void addClient_shouldReturnClient() throws Exception {
        Client client = new Client("client01", "", "", "");

        when(clientService.createClient(notNull())).thenReturn(client);

        this.mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("client01"));
    }

    @Test
    public void addClient_shouldReturnBadRequestWhenUserNameIsNull() throws Exception {
        Client client = new Client();

        this.mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addClient_shouldReturnBadRequestWhenRequestBodyIsNull() throws Exception {
        this.mockMvc.perform(post("/clients")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addClient_shouldReturn422WhenFailedCreateClient() throws Exception {
        Client client = new Client("client01", "", "", "");

        when(clientService.createClient(notNull())).thenReturn(null);

        this.mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateClient_shouldReturn200() throws Exception {
        Client client = new Client("client01",
                "testWeChatId",
                "testAliPayId",
                "12345689");

        when(clientService.updateClient(notNull(), notNull())).thenReturn(client);

        this.mockMvc.perform(put("/clients/{userId}", 1)
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("client01"))
                .andExpect(jsonPath("$.weChatId").value("testWeChatId"))
                .andExpect(jsonPath("$.aliPayId").value("testAliPayId"))
                .andExpect(jsonPath("$.phoneNumber").value("12345689"));
    }

    @Test
    public void updateClient_shouldReturnNoContentWhenFailedUpdateClient() throws Exception {
        when(clientService.updateClient(notNull(), notNull())).thenReturn(null);

        this.mockMvc.perform(put("/clients/{userId}", 1)
                .content(objectMapper.writeValueAsString(new Client("client01",
                        "", "", "")))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
