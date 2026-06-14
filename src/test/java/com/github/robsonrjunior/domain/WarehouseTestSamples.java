package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WarehouseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Warehouse getWarehouseSample1() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setName("name1");
        warehouse.setCode("code1");
        return warehouse;
    }

    public static Warehouse getWarehouseSample2() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(2L);
        warehouse.setName("name2");
        warehouse.setCode("code2");
        return warehouse;
    }

    public static Warehouse getWarehouseRandomSampleGenerator() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(longCount.incrementAndGet());
        warehouse.setName(UUID.randomUUID().toString());
        warehouse.setCode(UUID.randomUUID().toString());
        return warehouse;
    }
}
