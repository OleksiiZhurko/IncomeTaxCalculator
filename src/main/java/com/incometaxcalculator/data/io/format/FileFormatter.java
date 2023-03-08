package com.incometaxcalculator.data.io.format;

@FunctionalInterface
public interface FileFormatter {

  public static String FILE_EXTENSION = "";

  String formatLine(String keyword, String line);
}
