package com.incometaxcalculator.data.io;

import com.incometaxcalculator.data.management.TaxpayerManager;
import com.incometaxcalculator.exceptions.WrongFileFormatException;
import com.incometaxcalculator.exceptions.WrongReceiptDateException;
import com.incometaxcalculator.exceptions.WrongReceiptKindException;
import com.incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

public abstract class FileReader {

  public static int NOT_FOUND_CODE = -1;
  public static String SPACE = " ";
  public static String EMPTY = "";

  protected abstract int checkForReceipt(BufferedReader inputStream)
      throws NumberFormatException, IOException;

  protected abstract String getValueOfField(String fieldsLine) throws WrongFileFormatException;

  protected <R, T> R getValueOfField(T value, Function<T, R> function)
      throws WrongFileFormatException {
    if (Objects.isNull(value)) {
      throw new WrongFileFormatException();
    }
    try {
      return function.apply(value);
    } catch (NullPointerException e) {
      throw new WrongFileFormatException();
    }
  }

  public void readFile(String fileName) throws NumberFormatException, IOException,
      WrongTaxpayerStatusException, WrongFileFormatException, WrongReceiptKindException,
      WrongReceiptDateException {

    try (var inputStream = new BufferedReader(new java.io.FileReader(fileName))) {
      String fullName = getValueOfField(inputStream.readLine());
      int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
      String status = getValueOfField(inputStream.readLine());
      float income = Float.parseFloat(getValueOfField(inputStream.readLine()));
      createTaxpayer(fullName, taxRegistrationNumber, income, status);
      readReceipt(inputStream, taxRegistrationNumber);
    }
  }

  private void readReceipt(BufferedReader inputStream, int taxRegistrationNumber)
      throws WrongFileFormatException, IOException, WrongReceiptKindException,
      WrongReceiptDateException {
    int receiptId;
    while ((receiptId = checkForReceipt(inputStream)) >= 0) {
      String issueDate = getValueOfField(inputStream.readLine());
      String kind = getValueOfField(inputStream.readLine());
      float amount = Float.parseFloat(getValueOfField(inputStream.readLine()));
      String companyName = getValueOfField(inputStream.readLine());
      String country = getValueOfField(inputStream.readLine());
      String city = getValueOfField(inputStream.readLine());
      String street = getValueOfField(inputStream.readLine());
      int number = Integer.parseInt(getValueOfField(inputStream.readLine()));
      createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
          taxRegistrationNumber);
    }
  }

  private void createTaxpayer(
      String fullName,
      int taxRegistrationNumber,
      float income,
      String status
  ) throws WrongTaxpayerStatusException {
    new TaxpayerManager().createTaxpayer(fullName, taxRegistrationNumber, status, income);
  }

  private void createReceipt(
      int receiptId,
      String issueDate,
      float amount,
      String kind,
      String companyName,
      String country,
      String city,
      String street,
      int number,
      int taxRegistrationNumber
  ) throws WrongReceiptKindException, WrongReceiptDateException {
    new TaxpayerManager().createReceipt(
        receiptId,
        issueDate,
        amount,
        kind,
        companyName,
        country,
        city,
        street,
        number,
        taxRegistrationNumber
    );
  }

}