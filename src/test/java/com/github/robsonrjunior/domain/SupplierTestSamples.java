package com.github.robsonrjunior.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SupplierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Supplier getSupplierSample1() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setLegalName("legalName1");
        supplier.setTradeName("tradeName1");
        supplier.setTaxId("taxId1");
        supplier.setEmail("email1");
        supplier.setPhone("phone1");
        return supplier;
    }

    public static Supplier getSupplierSample2() {
        Supplier supplier = new Supplier();
        supplier.setId(2L);
        supplier.setLegalName("legalName2");
        supplier.setTradeName("tradeName2");
        supplier.setTaxId("taxId2");
        supplier.setEmail("email2");
        supplier.setPhone("phone2");
        return supplier;
    }

    public static Supplier getSupplierRandomSampleGenerator() {
        Supplier supplier = new Supplier();
        supplier.setId(longCount.incrementAndGet());
        supplier.setLegalName(UUID.randomUUID().toString());
        supplier.setTradeName(UUID.randomUUID().toString());
        supplier.setTaxId(UUID.randomUUID().toString());
        supplier.setEmail(UUID.randomUUID().toString());
        supplier.setPhone(UUID.randomUUID().toString());
        return supplier;
    }
}
