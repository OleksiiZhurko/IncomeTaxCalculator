package com.incometaxcalculator.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterUtil {

  public static void saveToFile(String fileName, String content) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
      writer.println(content);
    }
  }
}
