package com.example.demo.service;

import com.example.demo.domain.client.Client;
import com.example.demo.domain.client.ClientBuilder;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.client.ClientNotFoundException;
import com.example.demo.service.client.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void shouldGetClient() throws ClientNotFoundException {
        Client testClient = new ClientBuilder()
                .defaultClient()
                .build();
        given(clientRepository.findById(testClient.getId())).willReturn(Optional.of(testClient));

        Client result = clientService.getClient(testClient.getId());

        assertEquals(result, testClient);
    }

    @Test
    void shouldThrowClientNotFound(){
        Client testClient = new ClientBuilder()
                .defaultClient()
                .build();

        given(clientRepository.findById(testClient.getId())).willReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () ->{
           clientService.getClient(testClient.getId());
        });
    }

}
