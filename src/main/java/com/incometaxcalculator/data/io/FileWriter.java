package com.incometaxcalculator.data.io;

import com.incometaxcalculator.data.management.Receipt;
import com.incometaxcalculator.data.management.Taxpayer;
import com.incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.util.HashMap;

public abstract class FileWriter {

  public abstract void generateFile(int taxRegistrationNumber) throws IOException;

  public Taxpayer getTaxpayer(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayer(taxRegistrationNumber);
  }

  public String getTaxpayerName(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerName(taxRegistrationNumber);
  }

  public String getTaxpayerIncome(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerIncome(taxRegistrationNumber);
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerStatus(taxRegistrationNumber);
  }

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
  }

  public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerTotalReceiptsGathered(taxRegistrationNumber);
  }

  public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, int kind) {
    return new TaxpayerManager().getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerTotalTax(taxRegistrationNumber);
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber) {
    return new TaxpayerManager().getTaxpayerBasicTax(taxRegistrationNumber);
  }

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
    return new TaxpayerManager().getReceiptHashMap(taxRegistrationNumber);
  }

  public int getReceiptId(Receipt receipt) {
    return receipt.getId();
  }

  public String getReceiptIssueDate(Receipt receipt) {
    return receipt.getIssueDate();
  }

  public String getReceiptKind(Receipt receipt) {
    return receipt.getKind();
  }

  public float getReceiptAmount(Receipt receipt) {
    return receipt.getAmount();
  }

  public String getCompanyName(Receipt receipt) {
    return receipt.getCompany().getName();
  }

  public String getCompanyCountry(Receipt receipt) {
    return receipt.getCompany().getCountry();
  }

  public String getCompanyCity(Receipt receipt) {
    return receipt.getCompany().getCity();
  }

  public String getCompanyStreet(Receipt receipt) {
    return receipt.getCompany().getStreet();
  }

  public int getCompanyNumber(Receipt receipt) {
    return receipt.getCompany().getNumber();
  }

}