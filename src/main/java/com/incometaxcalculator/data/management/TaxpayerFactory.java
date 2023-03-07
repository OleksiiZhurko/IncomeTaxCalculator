package com.incometaxcalculator.data.management;

import com.incometaxcalculator.data.io.*;
import com.incometaxcalculator.exceptions.*;

import java.io.File;
import java.io.IOException;

import static com.incometaxcalculator.data.management.TaxpayerManager.taxpayerHashMap;
///////////////////////////////////////////////////////////////////////////

public class TaxpayerFactory {
    public void createTaxpayerFactory(String fullName, int taxRegistrationNumber, String status,
                                float income) throws WrongTaxpayerStatusException {
        switch (status) {
            case "Married Filing Jointly":
                taxpayerHashMap.put(taxRegistrationNumber,
                    new MarriedFilingJointlyTaxpayer(fullName, taxRegistrationNumber, income));
                break;
            case "Married Filing Separately":
                taxpayerHashMap.put(taxRegistrationNumber,
                    new MarriedFilingSeparatelyTaxpayer(fullName, taxRegistrationNumber, income));
                break;
            case "Single":
                taxpayerHashMap.put(taxRegistrationNumber,
                    new SingleTaxpayer(fullName, taxRegistrationNumber, income));
                break;
            case "Head of Household":
                taxpayerHashMap.put(taxRegistrationNumber,
                    new HeadOfHouseholdTaxpayer(fullName, taxRegistrationNumber, income));
                break;
            default:
                throw new WrongTaxpayerStatusException();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //DEN BORO NA VRO TI THELEI NA VELTIOTHEI STO IF-ELSE LOGIC TOU updateFilesFactory.
    //////////////////////////////////////////////////////////////////////////////////////////////?
    public void updateFilesFactory(int taxRegistrationNumber) throws IOException {
        if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
            new XMLInfoWriter().generateFile(taxRegistrationNumber);
        } else {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
            return;
        }
        if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        }
    }
    public FileWriter saveLogFileFactory(String fileFormat)
            throws WrongFileFormatException {
        if (fileFormat.equals("txt")) {
            return new TXTLogWriter();
        }
        else if (fileFormat.equals("xml")) {
            return new XMLLogWriter();
        }
        else {
            throw new WrongFileFormatException();
        }
    }
    public FileReader loadTaxpayerFactory(String fileName)
            throws NumberFormatException, WrongFileEndingException {

        String[] ending = fileName.split("\\.");
        if (ending[1].equals("txt")) {
             return new TXTFileReader();
        } else if (ending[1].equals("xml")) {
            return new XMLFileReader();
        } else {
            throw new WrongFileEndingException();
        }
    }
}
