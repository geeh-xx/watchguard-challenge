package com.watchguard.challange.service;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.watchguard.challange.infrastructure.exceptions.BusinessException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProcessMessageServiceTest {

  private ProcessMessageService processMessageService;

  @BeforeEach
  void setUp(){
    processMessageService = new ProcessMessageService();
    setField(processMessageService, "maxMessages", 2);
    setField(processMessageService, "outputDirectory", "test-output");

  }

  @Test
  void testAddMessage() {
    processMessageService.addMessage("test message 1");
    List<String> buffer = (List<String>) getField(processMessageService, "buffer");
    assertEquals(1, buffer.size());
    assertEquals("test message 1", buffer.get(0));
  }

  @Test
  void testAddMessageTriggersDumpToFile()  throws IOException {
    var outputPath = get("test-output");
    if (!exists(outputPath)) {
      createDirectories(outputPath);
    }

    processMessageService.addMessage("test message 1");
    processMessageService.addMessage("test message 2");
    List<String> buffer = (List<String>) getField(processMessageService, "buffer");
    assertEquals(0, buffer.size());
  }

  @Test
  void testAddMessageThrowsBusinessException() {
    setField(processMessageService, "outputDirectory", "/invalid-directory");
    processMessageService.addMessage("test message 1");
    BusinessException exception = assertThrows(BusinessException.class, () -> processMessageService.addMessage("test message 2"));
    assertEquals("Failed to write messages to file", exception.getMessage());
  }

  @Test
  void testCheckAndDump() throws IOException {
    var outputPath = get("test-output");
    if (!exists(outputPath)) {
      createDirectories(outputPath);
    }

    processMessageService.addMessage("test message 1");
    processMessageService.checkAndDump();
    List<String> buffer = (List<String>) getField(processMessageService, "buffer");
    assertEquals(0, buffer.size());
  }

}