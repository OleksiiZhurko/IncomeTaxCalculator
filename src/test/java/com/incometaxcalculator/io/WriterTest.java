package com.incometaxcalculator.io;

import com.incometaxcalculator.data.management.Company;
import com.incometaxcalculator.data.management.Receipt;
import com.incometaxcalculator.data.management.Taxpayer;
import com.incometaxcalculator.data.management.TaxpayerManager;
import com.incometaxcalculator.exceptions.WrongFileEndingException;
import com.incometaxcalculator.exceptions.WrongFileFormatException;
import com.incometaxcalculator.exceptions.WrongReceiptDateException;
import com.incometaxcalculator.exceptions.WrongReceiptKindException;
import com.incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

  private static Path workingDir;
  private static String taxRegistrationNumberFile;
  private static HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<Integer, Taxpayer>(0);
  private static TaxpayerManager taxpayerManager = new TaxpayerManager();
  private static HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<Integer, Integer>(0);
  
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    workingDir = Path.of("", "C:\\Users\\nikib\\Downloads\\SoftDevII-ProjectMaterial-2023\\SoftDevII-ProjectMaterial-2023\\2023-IncomeTaxCalculatorProject");
  }
  
  @Test
  void existsTaxpayerManager() throws IOException {
    assertNotNull(taxpayerManager);
  }
  
  @Test
  void existsTaxpayerHashMap() throws IOException {
    assertNotNull(taxpayerHashMap);
  }
  
  @Test
  void loadInfoTXTFile() throws IOException, NumberFormatException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    System.out.println(taxpayerManager.containsTaxpayer());
    taxRegistrationNumberFile = "123456789_INFO.txt";
    taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
    assertEquals(true,taxpayerManager.containsTaxpayer());
    assertEquals(5,taxpayerManager.getTaxpayerTotalReceiptsGathered(123456789));
  }
  
  @Test
  void loadInfoXMLFile() throws IOException, NumberFormatException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    System.out.println(taxpayerManager.containsTaxpayer());
    taxRegistrationNumberFile = "130456094_INFO.xml";
    taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
    assertEquals(true,taxpayerManager.containsTaxpayer());
    assertEquals(2,taxpayerManager.getTaxpayerTotalReceiptsGathered(130456094));
  }
  
  @Test
  void saveLogTXTFile() throws IOException, NumberFormatException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    System.out.println(taxpayerManager.containsTaxpayer());
    taxRegistrationNumberFile = "123456789_INFO.txt";
    taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
    taxpayerManager.saveLogFile(123456789,"txt");
    Path file = workingDir.resolve("123456789_LOG.txt");
    String expected="Name: Apostolos Zarras\r\n" + 
        "AFM: 123456789\r\n" + 
        "Income: 22570.0\r\n" + 
        "Basic Tax: 1207.495\r\n" + 
        "Tax Increase: 48.2998\r\n" + 
        "Total Tax: 1255.7948\r\n" + 
        "TotalReceiptsGathered: 5\r\n" + 
        "Entertainment: 0.0\r\n" + 
        "Basic: 4801.0\r\n" + 
        "Travel: 100.0\r\n" + 
        "Health: 0.0\r\n" + 
        "Other: 1000.0\r\n";
    String content;
    System.out.printf("______________ expected string length: %d\n", expected.length());
    content = Files.readString(file);
    System.out.printf("______________ content string length: %d\n", content.length());
    long count = Files.lines(file).count();
    assertEquals(count,12);
    assertEquals(content,expected);
  }
  
  @Test
  void saveLogXMLFile() throws IOException, NumberFormatException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    System.out.println(taxpayerManager.containsTaxpayer());
    taxRegistrationNumberFile = "130456094_INFO.xml";
    taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
    taxpayerManager.saveLogFile(130456094,"xml");
    Path file = workingDir.resolve("130456094_LOG.xml");
    String expected="<Name> Nikos Zisis </Name>\r\n" + 
        "<AFM> 130456094 </AFM>\r\n" + 
        "<Income> 40000.0 </Income>\r\n" + 
        "<Basic Tax> 2400.44 </Basic Tax>\r\n" + 
        "<Tax Increase> 192.0352 </Tax Increase>\r\n" + 
        "<Total Tax> 2592.4752 </Total Tax>\r\n" + 
        "<TotalReceiptsGathered> 2 </TotalReceiptsGathered>\r\n" + 
        "<Entertainment> 0.0 </Entertainment>\r\n" + 
        "<Basic> 4000.0 </Basic>\r\n" + 
        "<Travel> 0.0 </Travel>\r\n" + 
        "<Health> 0.0 </Health>\r\n" + 
        "<Other> 2000.0 </Other>\r\n";
    String content;
    content = Files.readString(file);
    long count = Files.lines(file).count();
    assertEquals(count,12);
    assertEquals(content,expected);
  }
  
  @Test
  void addReceiptUpdateWriter() throws IOException, WrongFileFormatException, WrongReceiptKindException, NumberFormatException, WrongFileEndingException, WrongTaxpayerStatusException {
    try {
        System.out.printf("Contains Taxpayer 4435: %b",taxpayerManager.containsTaxpayer(4435));
        taxRegistrationNumberFile = "4435_INFO.txt";
        taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
        
        Receipt receipt1 = new Receipt(6,"12/12/2012",1001.5F,"Other",new Company("Lol","Greece", "Ioannina", "Zerva", 1));
        Receipt receipt2 = new Receipt(7,"11/11/2013",1002.5F,"Basic",new Company("Oasis","Greece", "Ioannina", "Zerva", 3));
        
        taxpayerManager.getTaxpayer(4435).addReceipt(receipt1);
        taxpayerManager.getTaxpayer(4435).addReceipt(receipt2);
        // TODO: broken because private method
//        taxpayerManager.updateFiles(4435);
        taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
        
        Path file = workingDir.resolve("4435_INFO.txt");
        long count = Files.lines(file).count();
        assertEquals(true,taxpayerManager.containsTaxpayer(4435));
        assertEquals(7,taxpayerManager.getTaxpayerTotalReceiptsGathered(4435));
        assertEquals(count,75);
    } catch (WrongReceiptDateException e) {
        throw new RuntimeException(e);
    }
  }
  
  @Test
  void deleteReceiptUpdateWriter() throws IOException, WrongFileFormatException, WrongReceiptKindException, NumberFormatException, WrongFileEndingException, WrongTaxpayerStatusException {
    try {
        System.out.printf("Contains Taxpayer 4435: %b",taxpayerManager.containsTaxpayer(4435));
        taxRegistrationNumberFile = "4435_INFO.txt";
        taxpayerManager.removeReceipt(7);
        taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
        
        Path file = workingDir.resolve("4435_INFO.txt");
        long count = Files.lines(file).count();
        assertEquals(true,taxpayerManager.containsTaxpayer(4435));
        assertEquals(6,taxpayerManager.getTaxpayerTotalReceiptsGathered(4435));
        assertEquals(count,65);
    } catch (WrongReceiptDateException e) {
        throw new RuntimeException(e);
    }
  }

}
