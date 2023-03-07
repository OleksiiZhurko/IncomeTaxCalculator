package com.incometaxcalculator.data.management;

import com.incometaxcalculator.data.io.FileReader;
import com.incometaxcalculator.data.io.FileWriter;
import com.incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import com.incometaxcalculator.exceptions.WrongFileEndingException;
import com.incometaxcalculator.exceptions.WrongFileFormatException;
import com.incometaxcalculator.exceptions.WrongReceiptDateException;
import com.incometaxcalculator.exceptions.WrongReceiptKindException;
import com.incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.io.IOException;
import java.util.HashMap;

public class TaxpayerManager {
////////////////////////////////////////////////////////EKANA THN PRIVATE PROTECTED NA TO KSANADO
  protected static HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<>(0);//To ekana PROTECTED gia to factory!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
////////////////////////////////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  private static final HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<>(0);

  public void createTaxpayer(String fullName, int taxRegistrationNumber, String status,
      float income) throws WrongTaxpayerStatusException {

    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    taxpayerFactory.createTaxpayerFactory(fullName,taxRegistrationNumber,status,income);


    /*if (status.equals("Married Filing Jointly")) {
      taxpayerHashMap.put(taxRegistrationNumber,
          new MarriedFilingJointlyTaxpayer(fullName, taxRegistrationNumber, income));
    } else if (status.equals("Married Filing Separately")) {
      taxpayerHashMap.put(taxRegistrationNumber,
          new MarriedFilingSeparatelyTaxpayer(fullName, taxRegistrationNumber, income));
    } else if (status.equals("Single")) {
      taxpayerHashMap.put(taxRegistrationNumber,
          new SingleTaxpayer(fullName, taxRegistrationNumber, income));
    } else if (status.equals("Head of Household")) {
      taxpayerHashMap.put(taxRegistrationNumber,
          new HeadOfHouseholdTaxpayer(fullName, taxRegistrationNumber, income));
    } else {
      throw new WrongTaxpayerStatusException();
    }*/

  }

  public void createReceipt(int receiptId, String issueDate, float amount, String kind,
      String companyName, String country, String city, String street, int number,
      int taxRegistrationNumber) throws WrongReceiptKindException, WrongReceiptDateException {

    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind,
        new Company(companyName, country, city, street, number));
    taxpayerHashMap.get(taxRegistrationNumber).addReceipt(receipt);
    receiptOwnerTRN.put(receiptId, taxRegistrationNumber);
  }

  public void removeTaxpayer(int taxRegistrationNumber) {
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
    taxpayerHashMap.remove(taxRegistrationNumber);
    HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
    for (var entry : receiptsHashMap.entrySet()) {
      Receipt receipt = entry.getValue();
      receiptOwnerTRN.remove(receipt.getId());
    }
  }

  public void addReceipt(int receiptId, String issueDate, float amount, String kind,
      String companyName, String country, String city, String street, int number,
      int taxRegistrationNumber) throws IOException, WrongReceiptKindException,
      WrongReceiptDateException, ReceiptAlreadyExistsException {

    if (containsReceipt(receiptId)) {
      throw new ReceiptAlreadyExistsException();
    }
    createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number,
        taxRegistrationNumber);
    updateFiles(taxRegistrationNumber);
  }

  public void removeReceipt(int receiptId) throws IOException, WrongReceiptKindException {
    taxpayerHashMap.get(receiptOwnerTRN.get(receiptId)).removeReceipt(receiptId);
    updateFiles(receiptOwnerTRN.get(receiptId));
    receiptOwnerTRN.remove(receiptId);
  }

  private void updateFiles(int taxRegistrationNumber) throws IOException {
    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    taxpayerFactory.updateFilesFactory(taxRegistrationNumber);

    /*if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
      new XMLInfoWriter().generateFile(taxRegistrationNumber);
    } else {
      new TXTInfoWriter().generateFile(taxRegistrationNumber);
      return;
    }
    if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
      new TXTInfoWriter().generateFile(taxRegistrationNumber);
    }*/
  }

  public void saveLogFile(int taxRegistrationNumber, String fileFormat)
      throws IOException, WrongFileFormatException {

    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    FileWriter writer =  taxpayerFactory.saveLogFileFactory(fileFormat);
    writer.generateFile(taxRegistrationNumber);

    /*if (fileFormat.equals("txt")) {
      TXTLogWriter writer = new TXTLogWriter();
      writer.generateFile(taxRegistrationNumber);
    }
    else if (fileFormat.equals("xml")) {
      XMLLogWriter writer = new XMLLogWriter();
      writer.generateFile(taxRegistrationNumber);
    }
    else {
      throw new WrongFileFormatException();
    }*/
  }

  public boolean containsTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.containsKey(taxRegistrationNumber);
  }

  public boolean containsTaxpayer() {
    return !taxpayerHashMap.isEmpty();
  }

  public boolean containsReceipt(int id) {
    return receiptOwnerTRN.containsKey(id);
  }

  public Taxpayer getTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber);
  }

  public void loadTaxpayer(String fileName)
      throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
      WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {

    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    FileReader reader =  taxpayerFactory.loadTaxpayerFactory(fileName);
    reader.readFile(fileName);

    /*String ending[] = fileName.split("\\.");
    if (ending[1].equals("txt")) {
      FileReader reader = new TXTFileReader();
      reader.readFile(fileName);
    } else if (ending[1].equals("xml")) {
      FileReader reader = new XMLFileReader();
      reader.readFile(fileName);
    } else {
      throw new WrongFileEndingException();
    }*/
  }

  public String getTaxpayerName(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getFullName();
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingJointlyTaxpayer) {
      return "Married Filing Jointly";
    } else if (taxpayerHashMap
        .get(taxRegistrationNumber) instanceof MarriedFilingSeparatelyTaxpayer) {
      return "Married Filing Separately";
    } else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof SingleTaxpayer) {
      return "Single";
    } else {
      return "Head of Household";
    }
  }

  public String getTaxpayerIncome(int taxRegistrationNumber) {
    return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
  }

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
  }

  public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
  }

  public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, int kind) {
    return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
  }

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }

}