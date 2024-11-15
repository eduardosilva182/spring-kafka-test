package br.com.edu.kafkatest;

import br.com.edu.kafkatest.service.SpringKafkaMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.adapter.FilteringMessageListenerAdapter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafka
public class SpringKafkaTestApplication {

	/**
	 * Configurações básicas da aplicação
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaTestApplication.class, args);
	}

	/**
	 * Instancia um bean factory padrão de consumidores utilizando as configurações
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> kafkaConsumerConfigs = new HashMap<>();
		kafkaConsumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server:9092");
		kafkaConsumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaConsumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaConsumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id-teste");
		return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfigs);
	}

	/**
	 * Instancia um Listener padrão para consumo de mensagens via interface MessageListener.
	 * Verificar o uso na classe "KafkaMessageListener"
	 */
	@Bean
	public KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer() {
		var kafkaMessageListener = new SpringKafkaMessageListener();
		var kafkaFilter = new KafkaFilter();

		var messageListenerWithFilter = new FilteringMessageListenerAdapter<String, String>(kafkaMessageListener, kafkaFilter);

		ContainerProperties containerProperties = new ContainerProperties("test-topic");
		containerProperties.setMessageListener(messageListenerWithFilter);
        return new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
	}

	/**
	 * Instancia um Listener concorrente para múltiplos consumos de mensagens em partições (via concurrency) através da anotação "@KafkaListener".
	 * Verificar o uso na classe "KafkaListenerAnnotation"
	 */
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		var concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		concurrentKafkaListenerContainerFactory.setConcurrency(2);
		concurrentKafkaListenerContainerFactory.setReplyTemplate(kafkaTemplate());
		return concurrentKafkaListenerContainerFactory;
	}

	/**
	 * Instancia um bean factory para produtores de mensagens kafka utilizando as configurações
	 */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> kafkaProducerConfigs = new HashMap<>();
		kafkaProducerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server:9092");
		kafkaProducerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProducerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfigs);
	}

	/**
	 * Instancia um Kafka Template para produção de mensagens.
	 * Verificar o uso na classe "KafkaProducerService"
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
