package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RawMaterialTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RawMaterial getRawMaterialSample1() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("name1");
        rawMaterial.setSku("sku1");
        rawMaterial.setUnitDecimalPlaces(1);
        return rawMaterial;
    }

    public static RawMaterial getRawMaterialSample2() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(2L);
        rawMaterial.setName("name2");
        rawMaterial.setSku("sku2");
        rawMaterial.setUnitDecimalPlaces(2);
        return rawMaterial;
    }

    public static RawMaterial getRawMaterialRandomSampleGenerator() {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(longCount.incrementAndGet());
        rawMaterial.setName(UUID.randomUUID().toString());
        rawMaterial.setSku(UUID.randomUUID().toString());
        rawMaterial.setUnitDecimalPlaces(intCount.incrementAndGet());
        return rawMaterial;
    }
}
