package com.br.adrianmorais.annotations;

import com.br.adrianmorais.annotations.infra.rabbitmq.MessageHandler;
import com.rabbitmq.client.Delivery;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ExecuteMessageEndPoint {

    public static void executeEndPoint(Delivery delivery, List<Class> classesToVerify) throws Exception {
        classesToVerify.forEach((classToVerify) -> {
            var methodsAnnotated = Arrays.stream(classToVerify
                            .getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(MessageEndPoint.class))
                    .toList();


            if (methodsAnnotated.isEmpty()) {
                System.out.println("Nao existe nenhuma implementacao para este end-point");
                return;
            }

            var endpoint = delivery.getProperties().getHeaders().get("END_POINT").toString();

            var methodToHandle = methodsAnnotated
                    .stream()
                    .filter(method -> method.getName().equals(endpoint))
                    .findFirst();

            if (methodToHandle.isEmpty()) {
                System.out.println("Dont have end-point implemented in class" + classToVerify.getName());
                return;
            }

            try {
                Object returnedValue = methodToHandle
                        .get()
                        .invoke(classToVerify.newInstance(), new String(delivery.getBody(), StandardCharsets.UTF_8));

                new MessageHandler().sendCallbackMessage(delivery, endpoint, returnedValue.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
