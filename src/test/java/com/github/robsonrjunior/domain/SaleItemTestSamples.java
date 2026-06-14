package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SaleItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static SaleItem getSaleItemSample1() {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(1L);
        return saleItem;
    }

    public static SaleItem getSaleItemSample2() {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(2L);
        return saleItem;
    }

    public static SaleItem getSaleItemRandomSampleGenerator() {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(longCount.incrementAndGet());
        return saleItem;
    }
}
