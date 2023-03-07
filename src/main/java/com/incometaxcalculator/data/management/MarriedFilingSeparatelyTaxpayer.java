package com.incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

  public MarriedFilingSeparatelyTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);

    incomeComparisonArray[0] = 18040;
    incomeComparisonArray[1] = 71680;
    incomeComparisonArray[2] = 90000;
    incomeComparisonArray[3] = 127120;

    returnVarArray[0] = 0.0535 * income;
    returnVarArray[1] = 965.14 + 0.0705 * (income - 18040);
    returnVarArray[2] = 4746.76 + 0.0785 * (income - 71680);
    returnVarArray[3] = 6184.88 + 0.0785 * (income - 90000);
    returnVarArray[4] = 9098.80 + 0.0985 * (income - 127120);
  }

  /*public double calculateBasicTax() {
    if (income < 18040) {
      return 0.0535 * income;
    } else if (income < 71680) {
      return 965.14 + 0.0705 * (income - 18040);
    } else if (income < 90000) {
      return 4746.76 + 0.0785 * (income - 71680);
    } else if (income < 127120) {
      return 6184.88 + 0.0785 * (income - 90000);
    } else {
      return 9098.80 + 0.0985 * (income - 127120);
    }
  }*/

}