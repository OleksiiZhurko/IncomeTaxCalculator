package com.incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);

    incomeComparisonArray[0] = 30390;
    incomeComparisonArray[1] = 90000;
    incomeComparisonArray[2] = 122110;
    incomeComparisonArray[3] = 203390;

    returnVarArray[0] = 0.0535 * income;
    returnVarArray[1] = 1625.87 + 0.0705 * (income - 30390);
    returnVarArray[2] = 5828.38 + 0.0705 * (income - 90000);
    returnVarArray[3] = 8092.13 + 0.0785 * (income - 122110);
    returnVarArray[4] = 14472.61 + 0.0985 * (income - 203390);
  }

  /*public double calculateBasicTax() {
    if (income < 30390) {
      return 0.0535 * income;
    } else if (income < 90000) {
      return 1625.87 + 0.0705 * (income - 30390);
    } else if (income < 122110) {
      return 5828.38 + 0.0705 * (income - 90000);
    } else if (income < 203390) {
      return 8092.13 + 0.0785 * (income - 122110);
    } else {
      return 14472.61 + 0.0985 * (income - 203390);
    }
  }*/

}
