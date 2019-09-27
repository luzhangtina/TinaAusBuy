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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        when(clientService.findClientId(notNull())).thenReturn(null);
        this.mockMvc.perform(get("/clients/{userId}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
