package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StockMovementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static StockMovement getStockMovementSample1() {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setReferenceNumber("referenceNumber1");
        stockMovement.setNotes("notes1");
        return stockMovement;
    }

    public static StockMovement getStockMovementSample2() {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(2L);
        stockMovement.setReferenceNumber("referenceNumber2");
        stockMovement.setNotes("notes2");
        return stockMovement;
    }

    public static StockMovement getStockMovementRandomSampleGenerator() {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(longCount.incrementAndGet());
        stockMovement.setReferenceNumber(UUID.randomUUID().toString());
        stockMovement.setNotes(UUID.randomUUID().toString());
        return stockMovement;
    }
}
