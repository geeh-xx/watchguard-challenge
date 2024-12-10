package com.watchguard.challange.provider;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerProvider {

  private final Logger logger = getLogger(KafkaProducerProvider.class);
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${spring.kafka.topic}")
  private String topic;

  public KafkaProducerProvider(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }
  @Scheduled(fixedRateString = "${message.config.produce-interval-ms}")
  public void sendMessage() {
    logger.info("send message");
    kafkaTemplate.send(topic, UUID.randomUUID().toString());
  }
}