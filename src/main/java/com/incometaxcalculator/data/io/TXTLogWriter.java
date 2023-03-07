package com.incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import static com.incometaxcalculator.data.io.LogOptions.BASIC;
import static com.incometaxcalculator.data.io.LogOptions.ENTERTAINMENT;
import static com.incometaxcalculator.data.io.LogOptions.HEALTH;
import static com.incometaxcalculator.data.io.LogOptions.OTHER;
import static com.incometaxcalculator.data.io.LogOptions.TRAVEL;

public class TXTLogWriter extends FileWriter {

  public void generateFile(int taxRegistrationNumber) throws IOException {
    PrintWriter outputStream = new PrintWriter(
        new java.io.FileWriter(taxRegistrationNumber + "_LOG.txt"));
    outputStream.println("Name: " + getTaxpayerName(taxRegistrationNumber));
    outputStream.println("AFM: " + taxRegistrationNumber);
    outputStream.println("Income: " + getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println("Basic Tax: " + getTaxpayerBasicTax(taxRegistrationNumber));
    double taxOnReceipts = getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
    if (taxOnReceipts > 0) {
      outputStream.println("Tax Increase: " + taxOnReceipts);
    } else {
      outputStream.println("Tax Decrease: " + taxOnReceipts);
    }
    outputStream.println("Total Tax: " + getTaxpayerTotalTax(taxRegistrationNumber));
    outputStream.println(
        "TotalReceiptsGathered: " + getTaxpayerTotalReceiptsGathered(taxRegistrationNumber));
    outputStream.println("Entertainment: " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT.ordinal()));
    outputStream.println("Basic: " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC.ordinal()));
    outputStream.println("Travel: " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL.ordinal()));
    outputStream.println("Health: " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH.ordinal()));
    outputStream.println("Other: " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER.ordinal()));
    outputStream.close();
  }

}
