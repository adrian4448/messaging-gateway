package com.br.adrianm.messaginggateway.resource.api;

import com.br.adrianm.messaginggateway.infra.rabbitmq.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/{queueName}")
    public String createQueue(@PathVariable("queueName") String queueName) {
        queueService.createQueues(queueName);

        return "OK";
    }

    @DeleteMapping("/{queueName}")
    public String deleteQueue(@PathVariable("queueName") String queueName) {
        queueService.deleteQueues(queueName);

        return "OK";
    }
}
