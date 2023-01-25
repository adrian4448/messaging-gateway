package com.br.adrianmorais.annotations;

import com.br.adrianmorais.annotations.infra.rabbitmq.ConnectionProvider;
import com.br.adrianmorais.annotations.infra.rabbitmq.MessageHandler;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ExecuteMessageEndPoint {

    public static void executeEndPoint(Delivery delivery, Class classToVerify) throws InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
            var methodsAnnotated = Arrays.stream(classToVerify
                    .getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(MessageEndPoint.class))
                    .toList();


            if (methodsAnnotated.isEmpty()) {
                System.out.println("Nao existe nenhuma implementacao para este end-point");
            }

            var endpoint = delivery.getProperties().getHeaders().get("END_POINT").toString();

            var methodToHandle = methodsAnnotated
                    .stream()
                    .filter(method -> method.getName().equals(endpoint))
                    .findFirst();

            if (methodToHandle.isEmpty()) {
                throw new IllegalArgumentException("Dont have end-point implemented");
            }

            var returnedValue = methodToHandle
                    .get()
                    .invoke(classToVerify.newInstance(), new String(delivery.getBody(), StandardCharsets.UTF_8));

            new MessageHandler().sendCallbackMessage(delivery, endpoint, returnedValue.toString());
    }

}
