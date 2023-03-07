package com.incometaxcalculator.data.management;

import com.incometaxcalculator.data.io.*;
import com.incometaxcalculator.exceptions.*;

import java.io.File;
import java.io.IOException;

import static com.incometaxcalculator.data.management.TaxpayerManager.taxpayerHashMap;
///////////////////////////////////////////////////////////////////////////

public class TaxpayerFactory {
    public void createTaxpayerFactory(String fullname, int taxRegistrationNumber, String status,
                                float income) throws WrongTaxpayerStatusException {
        if (status.equals("Married Filing Jointly")) {
            taxpayerHashMap.put(taxRegistrationNumber,
                    new MarriedFilingJointlyTaxpayer(fullname, taxRegistrationNumber, income));
        } else if (status.equals("Married Filing Separately")) {
            taxpayerHashMap.put(taxRegistrationNumber,
                    new MarriedFilingSeparatelyTaxpayer(fullname, taxRegistrationNumber, income));
        } else if (status.equals("Single")) {
            taxpayerHashMap.put(taxRegistrationNumber,
                    new SingleTaxpayer(fullname, taxRegistrationNumber, income));
        } else if (status.equals("Head of Household")) {
            taxpayerHashMap.put(taxRegistrationNumber,
                    new HeadOfHouseholdTaxpayer(fullname, taxRegistrationNumber, income));
        } else {
            throw new WrongTaxpayerStatusException();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //DEN BORO NA VRO TI THELEI NA VELTIOTHEI STO IF-ELSE LOGIC TOU updateFilesFactory.
    //////////////////////////////////////////////////////////////////////////////////////////////?
    public FileWriter updateFilesFactory(int taxRegistrationNumber) throws IOException {
        if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
            new XMLInfoWriter().generateFile(taxRegistrationNumber);
        } else {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
            return null;
        }
        if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        }
        return null;
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

        String ending[] = fileName.split("\\.");
        if (ending[1].equals("txt")) {
             return new TXTFileReader();
        } else if (ending[1].equals("xml")) {
            return new XMLFileReader();
        } else {
            throw new WrongFileEndingException();
        }
    }
}
