package com.incometaxcalculator.management;


import com.incometaxcalculator.data.management.Company;
import com.incometaxcalculator.data.management.Receipt;
import com.incometaxcalculator.data.management.SingleTaxpayer;
import com.incometaxcalculator.exceptions.WrongReceiptDateException;
import com.incometaxcalculator.exceptions.WrongReceiptKindException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxpayerTest {

    @Test
    void addReceiptTest() {
        SingleTaxpayer singletaxpayer1 = new SingleTaxpayer("Avgoustinos Zhgos",123456798, 10000.5F);
        try {
            Receipt receipt1 = new Receipt(1,"12/12/2012",1000.5F,"Basic",new Company("Lol2","Greece", "Ioannina", "Zerva", 1));

            float startingReceipts = 0;
            int startingTotalReceiptsGathered = 0;
            HashMap<Integer, Receipt> receiptHashBeforeAdd;
            receiptHashBeforeAdd = singletaxpayer1.getReceiptHashMap();

            singletaxpayer1.addReceipt(receipt1);

            startingReceipts += 1000.5;
            startingTotalReceiptsGathered += 1;
            HashMap<Integer, Receipt> receiptHashAfterAdd;
            receiptHashAfterAdd = singletaxpayer1.getReceiptHashMap();

            assertEquals(startingReceipts,singletaxpayer1.getAmountOfReceiptKind((short) 1));
            assertEquals(startingTotalReceiptsGathered,singletaxpayer1.getTotalReceiptsGathered());
            assertEquals (receiptHashBeforeAdd.get(1),receiptHashAfterAdd.get(1));
        } catch (WrongReceiptDateException e) {
            throw new RuntimeException(e);
        } catch (WrongReceiptKindException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removeReceipt() {
            SingleTaxpayer singletaxpayer1 = new SingleTaxpayer("Avgoustinos Zhgos",123456798, 10000.5F);
            try {
                Receipt receipt1 = new Receipt(1,"12/12/2012",1000.5F,"Basic",new Company("Lol2","Greece", "Ioannina", "Zerva", 1));

                float startingReceipts = 0;
                int startingTotalReceiptsGathered = 0;

                singletaxpayer1.addReceipt(receipt1);

                startingReceipts += singletaxpayer1.getAmountOfReceiptKind((short) 1);
                startingTotalReceiptsGathered += singletaxpayer1.getTotalReceiptsGathered();
                HashMap<Integer, Receipt> receiptHashAfterAdd;
                receiptHashAfterAdd = singletaxpayer1.getReceiptHashMap();

                singletaxpayer1.removeReceipt(receipt1.getId());

                HashMap<Integer, Receipt> receiptHashAfterRemove;
                receiptHashAfterRemove = singletaxpayer1.getReceiptHashMap();

                startingReceipts -= 1000.5;
                startingTotalReceiptsGathered -= 1;
                receiptHashAfterAdd.remove(1);

                assertEquals(startingReceipts,singletaxpayer1.getAmountOfReceiptKind((short) 1));
                assertEquals(startingTotalReceiptsGathered,singletaxpayer1.getTotalReceiptsGathered());
                assertEquals(receiptHashAfterAdd.get(1),receiptHashAfterRemove.get(1));
            } catch (WrongReceiptDateException e) {
                throw new RuntimeException(e);
            } catch (WrongReceiptKindException e) {
                throw new RuntimeException(e);
            }
    }

    @Test
    void getVariationTaxOnReceipts() {
        SingleTaxpayer singleTaxpayer = new SingleTaxpayer("Avgoustinos Zigos", 123456780, 5000);
        try {
            Receipt receipt1 = new Receipt(1,"12/12/2012",1001.5F,"Other",new Company("Lol2","Greece", "Ioannina", "Zerva", 1));
            Receipt receipt2 = new Receipt(2,"11/11/2013",1002.5F,"Basic",new Company("Lol2","Greece", "Ioannina", "Zerva", 1));
            float variationTaxOnReceipts = (float) (0.15 * 0.0535 * 5000);

            singleTaxpayer.addReceipt(receipt1);
            singleTaxpayer.addReceipt(receipt2);

            assertEquals(variationTaxOnReceipts, singleTaxpayer.getVariationTaxOnReceipts());

        } catch (WrongReceiptDateException e) {
            throw new RuntimeException(e);
        } catch (WrongReceiptKindException e) {
            throw new RuntimeException(e);
        }

    }

}