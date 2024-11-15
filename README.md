# Resumo
Projeto pessoal de testes para estudos sobre o framework 'spring-kafka'.

## Conteúdo

### SpringKafkaTestController 
Endpoint REST que disponibiliza a geração de mensagens para o tópico "test-topic-annotation".

### SpringKafkaProducerService
Produtor de mensagens Kafka via KafkaTemplate.
KafkaTemplate configurado no método "kafkaTemplate" da classe "SpringKafkaTestApplication".
Produz mensagens para o tópico "test-topic-annotation".

### SpringKafkaMessageListener
Consumidor de tópico Kafka via implementação da interface "MessageListener". 
Configurado no método "kafkaMessageListenerContainer" da classe "SpringKafkaTestApplication".
Consome mensagens do tópico "test-topic".

### SpringKafkaListenerAnnotation
Consumidor de tópico Kafka via anotação "@KafkaListener".
Configurado no método "kafkaListenerContainerFactory" da classe "SpringKafkaTestApplication".
Consome mensagens do tópico "test-topic-annotation".

## Funcionamento
Possui um arquivo "docker-compose.yaml" com as configurações para execução da aplicação.

- Cria uma network chamada "network-spring-kafka-test" no modo 'bridge';
- Inicializa um container "kafka-server" rodando o Kafka;
  - Imagem utilizada: bitnami/kafka:latest
  - Porta de exposição: 9094
  - Configuração do protocolo de segurança: PLAINTEXT
  - Criação automática de tópicos: Desabilitada
- Inicializa um segundo container para realizar a configuração do Kafka;
  - Cria o tópico "test-topic" com 3 partições;
  - Cria o tópico "test-topic-annotation" com 3 partições;
- Inicializa o container desta aplicação "spring-kafka-test-app";
  - Instância 3 réplicas

## Como Executar
1. `docker-compose build`
2. `docker-compose up`


