package com.incometaxcalculator.data.io.format;

public final class TxtFormatter implements FileFormatter {

  public static String FILE_EXTENSION = ".txt";

  private static final String LINE_FILE_STYLE = "%s: %s";
  private static final String START_TAG = "%s:";

  public String formatLine(String keyword, String line) {
    return String.format(LINE_FILE_STYLE, keyword, line);
  }

  public String formatStartTag(String keyword) {
    return String.format(START_TAG, keyword);
  }
}
