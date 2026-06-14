package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        Product product = new Product();
        product.setId(1L);
        product.setName("name1");
        product.setSku("sku1");
        product.setUnitDecimalPlaces(1);
        return product;
    }

    public static Product getProductSample2() {
        Product product = new Product();
        product.setId(2L);
        product.setName("name2");
        product.setSku("sku2");
        product.setUnitDecimalPlaces(2);
        return product;
    }

    public static Product getProductRandomSampleGenerator() {
        Product product = new Product();
        product.setId(longCount.incrementAndGet());
        product.setName(UUID.randomUUID().toString());
        product.setSku(UUID.randomUUID().toString());
        product.setUnitDecimalPlaces(intCount.incrementAndGet());
        return product;
    }
}
