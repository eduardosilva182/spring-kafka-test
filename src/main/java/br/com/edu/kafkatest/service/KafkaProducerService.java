package br.com.edu.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    public final KafkaTemplate<String, String> kafkaTemplate;

    public void enviarMensagem(String key, String message) {
        kafkaTemplate.send("test-topic-annotation", key, message);
    }

}
