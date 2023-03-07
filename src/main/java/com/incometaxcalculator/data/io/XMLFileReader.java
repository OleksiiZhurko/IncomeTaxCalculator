package com.incometaxcalculator.data.io;

import com.incometaxcalculator.exceptions.WrongFileFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class XMLFileReader extends FileReader {

  protected int checkForReceipt(BufferedReader inputStream)
      throws NumberFormatException, IOException {
    String line;
    while (!Objects.isNull((line = inputStream.readLine()))) {
      String[] values = line.split(SPACE, 3);
      if (values[0].equals("<ReceiptID>")) {
        return Integer.parseInt(values[1].trim());
      }
    }
    return NOT_FOUND_CODE;
  }

  protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
    return getValueOfField(fieldsLine, (line) -> {
      String[] valueWithTail = line.split(SPACE, 2);
      String[] valueReversed = new StringBuilder(valueWithTail[1]).reverse().toString().trim()
          .split(SPACE, 2);
      return new StringBuilder(valueReversed[1]).reverse().toString();
    });
  }

}
