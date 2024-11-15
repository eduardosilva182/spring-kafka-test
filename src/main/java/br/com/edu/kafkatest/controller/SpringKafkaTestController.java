package br.com.edu.kafkatest.controller;

import br.com.edu.kafkatest.dto.MessageToBeSentDTO;
import br.com.edu.kafkatest.service.SpringKafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/kafka-producer")
public class SpringKafkaTestController {

    private final SpringKafkaProducerService springKafkaProducerService;
    @PostMapping
    public ResponseEntity<String> publishKafkaMessage(@RequestBody MessageToBeSentDTO messageToBeSentDTO) {
        springKafkaProducerService.enviarMensagem(messageToBeSentDTO.key(), messageToBeSentDTO.message());
        return ResponseEntity.ok("Mensagem enviada com sucesso.");
    }

}
