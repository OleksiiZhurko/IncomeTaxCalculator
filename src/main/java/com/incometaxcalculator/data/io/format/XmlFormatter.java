package com.incometaxcalculator.data.io.format;

public final class XmlFormatter implements FileFormatter {

  public static String FILE_EXTENSION = ".xml";

  private static final String LINE_FILE_STYLE = "<%s> %s </%s>";

  public String formatLine(String keyWord, String line) {
    return String.format(LINE_FILE_STYLE, keyWord, line, keyWord);
  }
}
