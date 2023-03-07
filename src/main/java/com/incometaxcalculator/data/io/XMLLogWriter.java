package com.incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import static com.incometaxcalculator.data.io.LogOptions.BASIC;
import static com.incometaxcalculator.data.io.LogOptions.ENTERTAINMENT;
import static com.incometaxcalculator.data.io.LogOptions.HEALTH;
import static com.incometaxcalculator.data.io.LogOptions.OTHER;
import static com.incometaxcalculator.data.io.LogOptions.TRAVEL;

public class XMLLogWriter extends FileWriter {

  public void generateFile(int taxRegistrationNumber) throws IOException {
    PrintWriter outputStream = new PrintWriter(
        new java.io.FileWriter(taxRegistrationNumber + "_LOG.xml"));
    outputStream.println("<Name> " + getTaxpayerName(taxRegistrationNumber) + " </Name>");
    outputStream.println("<AFM> " + taxRegistrationNumber + " </AFM>");
    outputStream.println("<Income> " + getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    outputStream
        .println("<BasicTax> " + getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>");
    double taxOnReceipts = getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber);
    if (taxOnReceipts > 0) {
      outputStream.println("<TaxIncrease> " + taxOnReceipts + " </TaxIncrease>");
    } else {
      outputStream.println("<TaxDecrease> " + taxOnReceipts + " </TaxDecrease>");
    }
    outputStream
        .println("<TotalTax> " + getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>");
    outputStream.println(
        "<Receipts> " + getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + " </Receipts>");
    outputStream.println("<Entertainment> " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT.ordinal()) +
        " </Entertainment>");
    outputStream.println("<Basic> " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC.ordinal()) + " </Basic>");
    outputStream.println("<Travel> " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL.ordinal()) + " </Travel>");
    outputStream.println("<Health> " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH.ordinal()) + " </Health>");
    outputStream.println("<Other> " +
        getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER.ordinal()) + " </Other>");
    outputStream.close();
  }

}
