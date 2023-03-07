package com.incometaxcalculator.data.io;

import com.incometaxcalculator.exceptions.WrongFileFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class TXTFileReader extends FileReader {

  protected int checkForReceipt(BufferedReader inputStream) throws NumberFormatException,
      IOException {
    String line;
    while (!Objects.isNull((line = inputStream.readLine()))) {
      String[] values = line.split(SPACE, 3);
      if (values[0].equals("Receipt")) {
        if (values[1].equals("ID:")) {
          return Integer.parseInt(values[2].trim());
        }
      }
    }
    return NOT_FOUND_CODE;
  }

  protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
    return getValueOfField(fieldsLine, (line) -> {
      String[] values = fieldsLine.split(SPACE, 2);
      return values[1].trim();
    });
  }

}