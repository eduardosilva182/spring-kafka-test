package br.com.edu.kafkatest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

@Component("kafkaFilter")
public class KafkaFilter implements RecordFilterStrategy<String, String> {

    @Override
    public boolean filter(ConsumerRecord<String, String> consumerRecord) {
        return "mensagemDescarte".equals(consumerRecord.key());
    }

}