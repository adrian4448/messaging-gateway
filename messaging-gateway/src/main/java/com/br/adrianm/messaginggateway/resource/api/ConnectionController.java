package com.br.adrianm.messaginggateway.resource.api;

import com.br.adrianm.messaginggateway.infra.rabbitmq.impl.ConnectionBroker;
import com.br.adrianm.messaginggateway.resource.dto.SetConnectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    @Autowired
    private ConnectionBroker connectionBroker;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String setConnection(@RequestBody SetConnectionDTO dto) {
        this.connectionBroker.setConnection(dto.getHost(), dto.getUsername(), dto.getPassword());

        return "OK";
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String closeConnection() {
        this.connectionBroker.closeConnection();

        return "OK";
    }
}
