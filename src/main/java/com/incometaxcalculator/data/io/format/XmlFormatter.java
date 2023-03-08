package com.incometaxcalculator.data.io.format;

import static com.incometaxcalculator.data.io.FileReader.EMPTY;
import static com.incometaxcalculator.data.io.FileReader.SPACE;

public final class XmlFormatter implements FileFormatter {

  public static String FILE_EXTENSION = ".xml";

  private static final String LINE_FILE_STYLE = "<%s> %s </%s>";
  private static final String START_TAG = "<%s>";
  private static final String END_TAG = "</%s>";

  public String formatLine(String keyword, String line) {
    return String.format(LINE_FILE_STYLE, formatStartTag(keyword), line, formatStartTag(keyword));
  }

  public String formatStartTag(String keyword) {
    return String.format(START_TAG, removeSpaces(keyword));
  }

  public String formatEndTag(String keyword) {
    return String.format(END_TAG, removeSpaces(keyword));
  }
  
  private String removeSpaces(String line) {
    return line.replaceAll(SPACE, EMPTY);
  }
}
