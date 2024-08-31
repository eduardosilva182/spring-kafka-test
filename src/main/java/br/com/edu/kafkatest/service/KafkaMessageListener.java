package br.com.edu.kafkatest.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@RequiredArgsConstructor
public class KafkaMessageListener implements MessageListener<String, String> {

    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("Mensagem consumida através do listener: " + consumerRecord.value());
    }

}
