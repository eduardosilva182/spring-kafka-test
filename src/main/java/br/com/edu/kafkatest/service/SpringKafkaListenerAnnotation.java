package br.com.edu.kafkatest.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class SpringKafkaListenerAnnotation {

    @KafkaListener(id = "listener1", topics = "test-topic-annotation", autoStartup = "true", concurrency = "1", groupId = "groupIdAnotado", clientIdPrefix = "prefix", filter = "kafkaFilter")
    @SendTo("test-topic")
    public String listen(ConsumerRecord<String, String> record, @Header(KafkaHeaders.GROUP_ID) String groupId) {
        System.out.println(String.format("Mensagem lida a partir do kafka consumer de id %s: %s", groupId, record.value()));
        return "Nova mensagem encaminhada para o tópico";
    }

}
