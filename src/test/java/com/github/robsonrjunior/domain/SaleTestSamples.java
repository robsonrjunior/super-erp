package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SaleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Sale getSaleSample1() {
        Sale sale = new Sale();
        sale.setId(1L);
        sale.setSaleNumber("saleNumber1");
        sale.setNotes("notes1");
        return sale;
    }

    public static Sale getSaleSample2() {
        Sale sale = new Sale();
        sale.setId(2L);
        sale.setSaleNumber("saleNumber2");
        sale.setNotes("notes2");
        return sale;
    }

    public static Sale getSaleRandomSampleGenerator() {
        Sale sale = new Sale();
        sale.setId(longCount.incrementAndGet());
        sale.setSaleNumber(UUID.randomUUID().toString());
        sale.setNotes(UUID.randomUUID().toString());
        return sale;
    }
}
