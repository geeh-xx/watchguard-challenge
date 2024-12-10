package com.watchguard.challange.provider;

import static org.slf4j.LoggerFactory.getLogger;

import com.watchguard.challange.service.ProcessMessageService;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumeProvider {

  private final Logger logger = getLogger(KafkaConsumeProvider.class);
  private final ProcessMessageService processMessageService;

  public KafkaConsumeProvider(ProcessMessageService processMessageService) {
    this.processMessageService = processMessageService;
  }

  @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void listen(String message) {
    logger.info("receive message: {}", message);
    processMessageService.addMessage(message);
  }

}
