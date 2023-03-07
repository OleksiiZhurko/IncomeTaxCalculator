package com.incometaxcalculator.data.management;

import com.incometaxcalculator.data.io.FileReader;
import com.incometaxcalculator.data.io.FileWriter;
import com.incometaxcalculator.data.io.TXTFileReader;
import com.incometaxcalculator.data.io.TXTInfoWriter;
import com.incometaxcalculator.data.io.TXTLogWriter;
import com.incometaxcalculator.data.io.XMLFileReader;
import com.incometaxcalculator.data.io.XMLInfoWriter;
import com.incometaxcalculator.data.io.XMLLogWriter;
import com.incometaxcalculator.data.io.format.TxtFormatter;
import com.incometaxcalculator.data.io.format.XmlFormatter;
import com.incometaxcalculator.exceptions.WrongFileEndingException;
import com.incometaxcalculator.exceptions.WrongFileFormatException;
import com.incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.io.File;
import java.io.IOException;

import static com.incometaxcalculator.data.management.TaxpayerManager.taxpayerHashMap;
///////////////////////////////////////////////////////////////////////////

public class TaxpayerFactory {
    public void createTaxpayerFactory(
        String fullName,
        int taxRegistrationNumber,
        String status,
        float income
    ) throws WrongTaxpayerStatusException {
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

            if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
                new TXTInfoWriter().generateFile(taxRegistrationNumber);
            }
        } else {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        }
    }
    public FileWriter saveLogFileFactory(String fileFormat) throws WrongFileFormatException {
        if (fileFormat.equals(TxtFormatter.FILE_EXTENSION)) {
            return new TXTLogWriter();
        } else if (fileFormat.equals(XmlFormatter.FILE_EXTENSION)) {
            return new XMLLogWriter();
        }
        throw new WrongFileFormatException();
    }
    public FileReader loadTaxpayerFactory(String fileName) throws WrongFileEndingException {
        if (fileName.endsWith(TxtFormatter.FILE_EXTENSION)) {
            return new TXTFileReader();
        } else if (fileName.endsWith(XmlFormatter.FILE_EXTENSION)) {
            return new XMLFileReader();
        }
        throw new WrongFileEndingException();
    }
}
