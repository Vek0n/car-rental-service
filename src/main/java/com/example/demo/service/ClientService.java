package com.example.demo.service;

import com.example.demo.domain.client.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.InvalidPathException;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientService (ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client getClient (long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
}
