package com.br.adrianm.messaginggateway.infra.rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageReturnContext {
    private Object responseJson;
}
