package com.incometaxcalculator.data.io.format;

public final class TxtFormatter implements FileFormatter {

  public static String FILE_EXTENSION = ".txt";

  private static final String LINE_FILE_STYLE = "%s: %s";

  public String formatLine(String keyWord, String line) {
    return String.format(LINE_FILE_STYLE, keyWord, line);
  }
}
