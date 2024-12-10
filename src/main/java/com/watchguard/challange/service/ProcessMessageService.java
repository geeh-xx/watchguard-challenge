package com.watchguard.challange.service;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.CREATE;

import com.watchguard.challange.infrastructure.exceptions.BusinessException;
import com.watchguard.challange.infrastructure.exceptions.DirectoryException;
import java.nio.file.Path;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Service
public class ProcessMessageService {

  private final List<String> buffer = new ArrayList<>();

  @Value("${message.config.max-messages}")
  private int maxMessages;

  @Value("${message.config.output-directory}")
  private String outputDirectory;

  @PostConstruct
  public void init() {
    Path path = get(outputDirectory);
    if (!exists(path)) {
      try {
        createDirectories(path);
      } catch (IOException e) {
        throw new DirectoryException("Failed to create output directory: " + outputDirectory, e);
      }
    }
  }

  public synchronized void addMessage(String message) {
    buffer.add(message);
    if (buffer.size() >= maxMessages) {
      dumpToFile();
    }
  }

  @Scheduled(fixedRateString = "${message.config.dump-interval-ms}")
  public synchronized void checkAndDump() {
    if (!buffer.isEmpty()) {
      dumpToFile();
    }
  }

  private void dumpToFile() {
    String fileName = outputDirectory + "/messages_" + System.currentTimeMillis() + ".gz";
    try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
      newOutputStream(get(fileName), CREATE));
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (String message : buffer) {
        writer.write(message);
        writer.newLine();
      }
      buffer.clear();
    } catch (IOException e) {
      throw new BusinessException("Failed to write messages to file", e);
    }
  }
}
