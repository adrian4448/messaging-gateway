package com.br.adrianm.messaginggateway.resource.api;

import com.br.adrianm.messaginggateway.infra.rabbitmq.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class HttpController {

    @Autowired
    private MessagePublisher messagePublisher;

    @PostMapping("/async/{queue}/{endpoint}")
    @ResponseStatus(HttpStatus.OK)
    public void sendAsyncMessage(@PathVariable("queue") String queue, @PathVariable("endpoint") String endpoint, @RequestBody Object body) {
        this.messagePublisher.publishAsyncMessage(body.toString(), queue, endpoint);
    }

    @PostMapping("/{queue}/{endpoint}")
    @ResponseStatus(HttpStatus.OK)
    public Object sendMessage(@PathVariable("queue") String queue, @PathVariable("endpoint") String endpoint, @RequestBody Object body) {
        return this.messagePublisher.publishMessage(body.toString(), queue, endpoint);
    }
}
